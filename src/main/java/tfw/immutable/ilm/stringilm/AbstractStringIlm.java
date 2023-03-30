package tfw.immutable.ilm.stringilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ilm.AbstractIlm;
import tfw.immutable.ilm.ImmutableLongMatrix;

public abstract class AbstractStringIlm extends AbstractIlm
	implements StringIlm
{
    protected abstract void toArrayImpl(String[][] array,
    	int xOffset, int yOffset, long xStart, long yStart,
    	int xLength, int yLength) throws DataInvalidException;

    protected AbstractStringIlm(long width, long height)
    {
    	super(width, height);
    }

    public static Object getImmutableInfo(ImmutableLongMatrix ilm)
    {
    	if (ilm instanceof ImmutableProxy)
    	{
    		return(((ImmutableProxy)ilm).getParameters());
    	}
    	else
    	{
    		return(ilm.toString());
    	}
    }

    public final String[][] toArray()
    	throws DataInvalidException
    {
    	Argument.assertNotGreaterThan(width(), Integer.MAX_VALUE,
    		"width()", "native array size");
    	Argument.assertNotGreaterThan(height(), Integer.MAX_VALUE,
    		"height()", "native array size");
    	
    	return toArray(0, 0, (int)width(), (int)height());
    }

    public final String[][] toArray(long rowStart, long columnStart,
    	int width, int height) throws DataInvalidException
    {
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");
    	
    	String[][] result = new String[height][width];
    	
    	toArray(result, 0, 0, rowStart, columnStart, width, height);
    	
    	return result;
    }

    public final void toArray(String[][] array, int rowOffset,
    	int columnOffset, long rowStart, long columnStart,
    	int width, int height) throws DataInvalidException
    {
    	Argument.assertNotNull(array, "array");
    	
    	if (width == 0 || height == 0 || array.length == 0)
    	{
    		return;
    	}
    	
    	boundsCheck(array[0].length, array.length, rowOffset, columnOffset,
    		rowStart, columnStart, width, height);
    	toArrayImpl(array, rowOffset, columnOffset, rowStart, columnStart,
    		width, height);
    }
}