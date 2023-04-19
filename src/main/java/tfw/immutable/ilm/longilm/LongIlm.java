package tfw.immutable.ilm.longilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface LongIlm extends ImmutableLongMatrix
{
    public long[] toArray()
    	throws DataInvalidException;
    public long[] toArray(long rowStart, long columnStart,
    	int rowCount, int colCount) throws DataInvalidException;
    public void toArray(long[] array, int offset,
    	long rowStart, long columnStart, int rowCount, int colCount)
    	throws DataInvalidException;
    public void toArray(long[] array, int offset, int rowStride, int colStride,
        	long rowStart, long columnStart, int rowCount, int colCount)
        	throws DataInvalidException;
}