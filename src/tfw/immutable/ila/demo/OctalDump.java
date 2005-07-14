/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * witout even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.immutable.ila.demo;

import java.io.File;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromFile;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;
import tfw.immutable.ila.byteila.ByteIlaSwap;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromLongIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromIntIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromByteIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromByteIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;
import tfw.immutable.ila.objectila.AbstractObjectIla;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaIterator;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromByteIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public class OctalDump
{
	public static void main(String[] args)
		throws DataInvalidException
	{
		if (args.length != 3 || args[1].length() != 2)
		{
			System.err.println("Usage: OctalDump <-t type> <file>");
			System.err.println("       type = <d,f,o,x><1,2,4,8>");
			System.exit(-1);
		}
		
		ByteIla bytes = ByteIlaFromFile.create(new File(args[2]));
		ObjectIla strings = null;
		char type = args[1].charAt(0);
		char size = args[1].charAt(1);
		
		switch(type)
		{
			case 'd':
			case 'o':
			case 'x':
				switch(size)
				{
					case '1':
					{
						strings = StringObjectIlaFromByteIla.create(bytes, type);
						break;
					}
					case '2':
					{
						ByteIla byteIla = ByteIlaSwap.create(bytes, 2);
						ShortIla shortIla = ShortIlaFromByteIla.create(byteIla);
						strings = StringObjectIlaFromShortIla.create(shortIla, type);
						break;
					}
					case '4':
					{
						ByteIla byteIla = ByteIlaSwap.create(bytes, 4);
						IntIla intIla = IntIlaFromByteIla.create(byteIla);
						strings = StringObjectIlaFromIntIla.create(intIla, type);
						break;
					}
					case '8':
					{
						ByteIla byteIla = ByteIlaSwap.create(bytes, 8);
						LongIla longIla = LongIlaFromByteIla.create(byteIla);
						strings = StringObjectIlaFromLongIla.create(longIla, type);
						break;
					}
					default:
						throw new IllegalArgumentException(
							"size="+size+" for 'd', 'o', or 'x' not allowed!");
				}
				break;
			case 'f':
				switch(size)
				{
					case '4':
					{
						ByteIla byteIla = ByteIlaSwap.create(bytes, 4);
						IntIla intIla = IntIlaFromByteIla.create(byteIla);
						FloatIla floatIla = FloatIlaFromIntIla.create(intIla);
						strings = StringObjectIlaFromFloatIla.create(floatIla);
						break;
					}
					case '8':
					{
						ByteIla byteIla = ByteIlaSwap.create(bytes, 8);
						LongIla longIla = LongIlaFromByteIla.create(byteIla);
						DoubleIla doubleIla = DoubleIlaFromLongIla.create(longIla);
						strings = StringObjectIlaFromDoubleIla.create(doubleIla);
					}
					default:
						throw new IllegalArgumentException(
							"size="+size+" for 'f' not allowed!");
				}
				break;
			default:
				throw new IllegalArgumentException(
					"type="+type+" not allowed!");
		}

		for (ObjectIlaIterator oii=new ObjectIlaIterator(strings) ;
			oii.hasNext() ; )
		{
			System.out.println((String)oii.next());
		}
		
		System.out.println("strings = \n"+strings);
	}
	
	private static class StringObjectIlaFromByteIla
	{
		private StringObjectIlaFromByteIla() {}
		
		public static ObjectIla create(ByteIla byteIla, char type)
		{
			if (byteIla == null)
				throw new IllegalArgumentException(
					"byteIla == null not allowed!");
			
			return(new MyObjectIla(byteIla, type));
		}
		
	    private static class MyObjectIla extends AbstractObjectIla
	    {
			private final ByteIla byteIla;
			private final char type;

			MyObjectIla(ByteIla byteIla, char type)
			{
				super(byteIla.length() / 16 +
					(byteIla.length() % 16 == 0 ? 0 : 1));
				
				this.byteIla = byteIla;
				this.type = type;
			}
			
			protected void toArrayImpl(Object[] array, int offset,
					long start, int length) throws DataInvalidException
			{
				ByteIlaIterator bii = new ByteIlaIterator(
					ByteIlaSegment.create(byteIla, start,
						byteIla.length() - start));
				long end = start + length;
				
				for (int i=0 ; i < length ; i++)
				{
					StringBuffer sb = new StringBuffer();
					
					sb.append(Long.toOctalString(start + i * 16));
					for (int j=0 ; j < 16 && bii.hasNext() ; j++)
					{
						sb.append(" ");
						switch(type)
						{
							case 'd':
								sb.append(Byte.toString(bii.next()));
								break;
							case 'o':
								sb.append(Integer.toOctalString(
									bii.next() & 0xFF));
								break;
							case 'x':
								sb.append(Integer.toHexString(
									bii.next() & 0xFF));
								break;
							default:
								throw new IllegalArgumentException(
									"size=1 for '"+type+"' not allowed!");
						}
					}
					sb.append("\n");
					
					array[offset+i] = sb.toString();
				}
			}
			
			public String toString()
			{
				StringBuffer sb = new StringBuffer();
				
				sb.append("StringObjectIlaFromByteIla: length=");
				sb.append(length());
				sb.append(" type=");
				sb.append(type);
				sb.append("\n");
				sb.append(byteIla.toString());
				
				return(sb.toString());
			}
	    }
	}
	
	private static class StringObjectIlaFromShortIla
	{
		private StringObjectIlaFromShortIla() {}
		
		public static ObjectIla create(ShortIla shortIla, char type)
		{
			if (shortIla == null)
				throw new IllegalArgumentException(
					"shortIla == null not allowed!");
			
			return(new MyObjectIla(shortIla, type));
		}
		
	    private static class MyObjectIla extends AbstractObjectIla
	    {
			private final ShortIla shortIla;
			private final char type;

			MyObjectIla(ShortIla shortIla, char type)
			{
				super(shortIla.length() / 8 +
					(shortIla.length() % 8 == 0 ? 0 : 1));
				
				this.shortIla = shortIla;
				this.type = type;
			}
			
			protected void toArrayImpl(Object[] array, int offset,
					long start, int length) throws DataInvalidException
			{
				ShortIlaIterator sii = new ShortIlaIterator(
					ShortIlaSegment.create(shortIla, start * 2,
						shortIla.length() - start * 2));
				long end = start + length;
				
				for (int i=0 ; i < length ; i++)
				{
					StringBuffer sb = new StringBuffer();
					
					sb.append(Long.toOctalString(start + i * 16));
					for (int j=0 ; j < 8 && sii.hasNext() ; j++)
					{
						sb.append(" ");
						switch(type)
						{
							case 'd':
								sb.append(Short.toString(sii.next()));
								break;
							case 'o':
								sb.append(Integer.toOctalString(
									sii.next() & 0xFFFF));
								break;
							case 'x':
								sb.append(Integer.toHexString(
									sii.next() & 0xFFFF));
								break;
							default:
								throw new IllegalArgumentException(
									"size=2 for '"+type+"' not allowed!");
						}
					}
					sb.append("\n");
					
					array[offset+i] = sb.toString();
				}
			}
			
			public String toString()
			{
				StringBuffer sb = new StringBuffer();
				
				sb.append("StringObjectIlaFromShortIla: length=");
				sb.append(length());
				sb.append(" type=");
				sb.append(type);
				sb.append("\n");
				sb.append(shortIla.toString());
				
				return(sb.toString());
			}
	    }
	}
	
	private static class StringObjectIlaFromIntIla
	{
		private StringObjectIlaFromIntIla() {}
		
		public static ObjectIla create(IntIla intIla, char type)
		{
			if (intIla == null)
				throw new IllegalArgumentException(
					"intIla == null not allowed!");
			
			return(new MyObjectIla(intIla, type));
		}
		
	    private static class MyObjectIla extends AbstractObjectIla
	    {
			private final IntIla intIla;
			private final char type;

			MyObjectIla(IntIla intIla, char type)
			{
				super(intIla.length() / 4 +
					(intIla.length() % 4 == 0 ? 0 : 1));
				
				this.intIla = intIla;
				this.type = type;
			}
			
			protected void toArrayImpl(Object[] array, int offset,
					long start, int length) throws DataInvalidException
			{
				IntIlaIterator iii = new IntIlaIterator(
					IntIlaSegment.create(intIla, start * 4,
						intIla.length() - start * 4));
				long end = start + length;
				
				for (int i=0 ; i < length ; i++)
				{
					StringBuffer sb = new StringBuffer();
					
					sb.append(Long.toOctalString(start + i * 16));
					for (int j=0 ; j < 4 && iii.hasNext() ; j++)
					{
						sb.append(" ");
						switch(type)
						{
							case 'd':
								sb.append(Integer.toString(iii.next()));
								break;
							case 'o':
								sb.append(Integer.toOctalString(iii.next()));
								break;
							case 'x':
								sb.append(Integer.toHexString(iii.next()));
								break;
						}
					}
					sb.append("\n");
					
					array[offset+i] = sb.toString();
				}
			}
			
			public String toString()
			{
				StringBuffer sb = new StringBuffer();
				
				sb.append("StringObjectIlaFromIntIla: length=");
				sb.append(length());
				sb.append(" type=");
				sb.append(type);
				sb.append("\n");
				sb.append(intIla.toString());
				
				return(sb.toString());
			}
	    }
	}
	
	private static class StringObjectIlaFromLongIla
	{
		private StringObjectIlaFromLongIla() {}
		
		public static ObjectIla create(LongIla longIla, char type)
		{
			if (longIla == null)
				throw new IllegalArgumentException(
					"intIla == null not allowed!");
			
			return(new MyObjectIla(longIla, type));
		}
		
	    private static class MyObjectIla extends AbstractObjectIla
	    {
			private final LongIla longIla;
			private final char type;

			MyObjectIla(LongIla longIla, char type)
			{
				super(longIla.length() / 2 +
					(longIla.length() % 2 == 0 ? 0 : 1));
				
				this.longIla = longIla;
				this.type = type;
			}
			
			protected void toArrayImpl(Object[] array, int offset,
					long start, int length) throws DataInvalidException
			{
				LongIlaIterator lii = new LongIlaIterator(
					LongIlaSegment.create(longIla, start * 8,
						longIla.length() - start * 8));
				long end = start + length;
				
				for (int i=0 ; i < length ; i++)
				{
					StringBuffer sb = new StringBuffer();
					
					sb.append(Long.toOctalString(start + i * 16));
					for (int j=0 ; j < 2 && lii.hasNext() ; j++)
					{
						sb.append(" ");
						switch(type)
						{
							case 'd':
								sb.append(Long.toString(lii.next()));
								break;
							case 'o':
								sb.append(Long.toOctalString(lii.next()));
								break;
							case 'x':
								sb.append(Long.toHexString(lii.next()));
								break;
						}
					}
					sb.append("\n");
					
					array[offset+i] = sb.toString();
				}
			}
			
			public String toString()
			{
				StringBuffer sb = new StringBuffer();
				
				sb.append("StringObjectIlaFromLongIla: length=");
				sb.append(length());
				sb.append(" type=");
				sb.append(type);
				sb.append("\n");
				sb.append(longIla.toString());
				
				return(sb.toString());
			}
	    }
	}

	private static class StringObjectIlaFromFloatIla
	{
		private StringObjectIlaFromFloatIla() {}
		
		public static ObjectIla create(FloatIla floatIla)
		{
			if (floatIla == null)
				throw new IllegalArgumentException(
					"intIla == null not allowed!");
			
			return(new MyObjectIla(floatIla));
		}
		
	    private static class MyObjectIla extends AbstractObjectIla
	    {
			private final FloatIla floatIla;

			MyObjectIla(FloatIla floatIla)
			{
				super(floatIla.length() / 4 +
					(floatIla.length() % 4 == 0 ? 0 : 1));
				
				this.floatIla = floatIla;
			}
			
			protected void toArrayImpl(Object[] array, int offset,
					long start, int length) throws DataInvalidException
			{
				FloatIlaIterator fii = new FloatIlaIterator(
					FloatIlaSegment.create(floatIla, start * 4,
						floatIla.length() - start * 4));
				long end = start + length;
				
				for (int i=0 ; i < length ; i++)
				{
					StringBuffer sb = new StringBuffer();
					
					sb.append(Long.toOctalString(start + i * 16));
					for (int j=0 ; j < 4 && fii.hasNext() ; j++)
					{
						sb.append(" ");
						sb.append(Float.toString(fii.next()));
					}
					sb.append("\n");
					
					array[offset+i] = sb.toString();
				}
			}
			
			public String toString()
			{
				StringBuffer sb = new StringBuffer();
				
				sb.append("StringObjectIlaFromFloatIla: length=");
				sb.append(length());
				sb.append("\n");
				sb.append(floatIla.toString());
				
				return(sb.toString());
			}
	    }
	}
	private static class StringObjectIlaFromDoubleIla
	{
		private StringObjectIlaFromDoubleIla() {}
		
		public static ObjectIla create(DoubleIla doubleIla)
		{
			if (doubleIla == null)
				throw new IllegalArgumentException(
					"intIla == null not allowed!");
			
			return(new MyObjectIla(doubleIla));
		}
		
	    private static class MyObjectIla extends AbstractObjectIla
	    {
			private final DoubleIla doubleIla;

			MyObjectIla(DoubleIla doubleIla)
			{
				super(doubleIla.length() / 2 +
					(doubleIla.length() % 2 == 0 ? 0 : 1));
				
				this.doubleIla = doubleIla;
			}
			
			protected void toArrayImpl(Object[] array, int offset,
					long start, int length) throws DataInvalidException
			{
				DoubleIlaIterator dii = new DoubleIlaIterator(
					DoubleIlaSegment.create(doubleIla, start * 4,
						doubleIla.length() - start * 4));
				long end = start + length;
				
				for (int i=0 ; i < length ; i++)
				{
					StringBuffer sb = new StringBuffer();
					
					sb.append(Long.toOctalString(start + i * 16));
					for (int j=0 ; j < 2 && dii.hasNext() ; j++)
					{
						sb.append(" ");
						sb.append(Double.toString(dii.next()));
					}
					sb.append("\n");
					
					array[offset+i] = sb.toString();
				}
			}
			
			public String toString()
			{
				StringBuffer sb = new StringBuffer();
				
				sb.append("StringObjectIlaFromDoubleIla: length=");
				sb.append(length());
				sb.append("\n");
				sb.append(doubleIla.toString());
				
				return(sb.toString());
			}
	    }
	}
}