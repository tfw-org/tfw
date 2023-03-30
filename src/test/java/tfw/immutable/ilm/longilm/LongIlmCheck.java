package tfw.immutable.ilm.longilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.longilm.LongIlm;

public final class LongIlmCheck
{
	private LongIlmCheck() {}
	
	public static void check(LongIlm ilm1, LongIlm ilm2)
	{
		Argument.assertEquals(ilm1.width(), ilm2.width(),
			"ilm1.width()", "ilm2.width");
		Argument.assertEquals(ilm1.height(), ilm2.height(),
			"ilm1.height()", "ilm2.height()");
			
		// Check to see if the zero argument toArray() methods return
		// the same result.
		
		final int width = (int)ilm1.width();
		final int height = (int)ilm1.height();
		long[][] a1 = null;
		long[][] a2 = null;
		
		try
		{
			a1 = ilm1.toArray();
			a2 = ilm2.toArray();
		}
		catch(DataInvalidException die)
		{
			throw new IllegalArgumentException(
				die.toString());
		}
		
		for (int r=0 ; r < height ; r++)
		{
			for (int c=0 ; c < width ; c++)
			{
				if (a1[r][c] != a2[r][c])
				{
					throw new IllegalArgumentException(
						"zero argument: ilm1.toArray()["+r+"]["+c+
						"] != ilm2.toArray()["+r+"]["+c+"]");
				}
			}
		}
		
		// Check to see if the four argument toArray() methods return
		// the same result.

		for (int sr=0 ; sr < height ; sr++)
		{
			for (int sc=0 ; sc < width ; sc++)
			{
				int remainingHeight = height - sr;
				int remainingWidth = width - sc;
				
				for (int h=0 ; h < remainingHeight ; h++)
				{
					for (int w=0 ; w < remainingWidth ; w++)
					{
						try
						{
							a1 = ilm1.toArray(sr, sc, w, h);
							a2 = ilm2.toArray(sr, sc, w, h);
						}
						catch(DataInvalidException die)
						{
							throw new IllegalArgumentException(
								die.toString());
						}
						
						for (int r=0 ; r < h ; r++)
						{
							for (int c=0 ; c < w ; c++)
							{
								if (a1[r][c] != a2[r][c])
								{
									throw new IllegalArgumentException(
										"four argument: ilm1.toArray("+
										sr+", "+sc+", "+w+", "+h+")["+r+"]["+c+
										"] != ilm2.toArray("+
										sr+", "+sc+", "+w+", "+h+")["+r+"]["+c+"]");
								}
							}
						}
					}
				}
			}
		}
	}
}