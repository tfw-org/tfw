package tfw.immutable.ilm.charilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.charilm.CharIlm;
import tfw.immutable.ilm.charilm.CharIlmFromArray;

public class CharIlmFromArrayTest extends TestCase
{
	public void testCharIlmFromArray()
		throws DataInvalidException
	{
		final int WIDTH = 20;
		final int HEIGHT = 25;
		final Random random = new Random();
		final char[][] array = new char[HEIGHT][WIDTH];
		
		for (int i=0 ; i < HEIGHT ; i++)
		{
			for (int j=0 ; j < WIDTH ; j++)
			{
				array[i][j] = (char)random.nextInt();
			}
		}
		
		final CharIlm ilm = CharIlmFromArray.create(array);
		
		try
		{
			CharIlmFromArray.create(null);
			fail("array == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		if (ilm.width() != WIDTH)
		{
			fail("ilm.width() (="+ilm.width()+
				") != array.width() (="+WIDTH+")");
		}
		if (ilm.height() != HEIGHT)
		{
			fail("ilm.height() (="+ilm.height()+
				") != array.height() (="+HEIGHT+")");
		}
				
		// Check to see if the zero argument toArray() methods return
		// the same result.
		
		char[][] a = ilm.toArray();
		
		for (int r=0 ; r < HEIGHT ; r++)
		{
			for (int c=0 ; c < WIDTH ; c++)
			{
				if (array[r][c] != a[r][c])
				{
					fail("zero argument: array["+r+"]["+c+"] != ilm.toArray()["+r+"]["+c+"]");
				}
			}
		}
		
		// Check to see if the four argument toArray() methods return
		// the same result.

		for (int sr=0 ; sr < HEIGHT ; sr++)
		{
			for (int sc=0 ; sc < WIDTH ; sc++)
			{
				int remainingHeight = HEIGHT - sr;
				int remainingWidth = WIDTH - sc;
				
				for (int h=0 ; h < remainingHeight ; h++)
				{
					for (int w=0 ; w < remainingWidth ; w++)
					{
						a = ilm.toArray(sr, sc, w, h);
						
						for (int r=0 ; r < h ; r++)
						{
							for (int c=0 ; c < w ; c++)
							{
								if (array[sr+r][sc+c] != a[r][c])
								{
									fail("four argument: startingRow="+sr+" startingColumn="+sc+
										"\nwidth="+w+" height="+h+
										"\narray["+(sr+r)+"]["+(sc+c)+"] != ilm.toArray()["+r+"]["+c+"]");
								}
							}
						}
					}
				}
			}
		}

		// Check to see if the seven argument toArray() methods return
		// the same result.
		
/*
		for (int s=0 ; s < LENGTH ; s++)
		{
			for (int l=0 ; l < LENGTH - s ; l++)
			{
				for (int o=0 ; o < LENGTH - l ; o++)
				{
					char[] a1 = new char[LENGTH];
					char[] a2 = new char[LENGTH];

					System.arraycopy(array, s, a1, o, l);
					ila.toArray(a2, o, s, l);
					
					if (!Arrays.equals(a1, a2))
					{
						fail("array(array, offset="+o+
								", start="+s+", length="+l+
								") != ila2.toArray(array, offset="+o+
								", start="+s+", length="+l+")");
					}
				}
			}
		}
*/
	}
}