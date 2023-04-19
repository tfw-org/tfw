package tfw.immutable.ilm.intilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.AbstractIlm;

public abstract class AbstractIntIlm extends AbstractIlm
	implements IntIlm
{
    protected abstract void toArrayImpl(int[] array, int offset,
    	int rowStride, int colStride, long rowStart, long colStart,
    	int rowCount, int colCount) throws DataInvalidException;

    protected AbstractIntIlm(long width, long height)
    {
    	super(width, height);
    }

    public final int[] toArray()
    	throws DataInvalidException
    {
    	Argument.assertNotGreaterThan(width(), Integer.MAX_VALUE,
    		"width()", "native array size");
    	Argument.assertNotGreaterThan(height(), Integer.MAX_VALUE,
    		"height()", "native array size");
    	
    	return toArray(0, 0, (int)height(), (int)width());
    }

    public final int[] toArray(long rowStart, long columnStart,
    	int rowCount, int colCount) throws DataInvalidException
    {
    	Argument.assertNotLessThan(rowCount, 0, "rowCount");
    	Argument.assertNotLessThan(colCount, 0, "colCount");
    	
    	int[] result = new int[rowCount * colCount];
    	
    	toArray(result, 0, rowStart, columnStart, rowCount, colCount);
    	
    	return result;
    }
    
    public final void toArray(int[] array, int offset, long rowStart,
    	long colStart, int rowCount, int colCount) throws DataInvalidException {
    	toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
    }

    public final void toArray(int[] array, int offset, int rowStride,
    	int colStride, long rowStart, long columnStart,
    	int rowCount, int colCount) throws DataInvalidException
    {
    	Argument.assertNotNull(array, "array");
    	
    	if (width == 0 || height == 0 || array.length == 0)
    	{
    		return;
    	}
    	
    	boundsCheck(array.length, offset, rowStride, colStride,
    		rowStart, columnStart, rowCount, colCount);
    	toArrayImpl(array, offset, rowStride, colStride, rowStart, columnStart,
    		rowCount, colCount);
    }
}