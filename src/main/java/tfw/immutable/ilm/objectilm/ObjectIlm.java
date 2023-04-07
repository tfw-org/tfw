package tfw.immutable.ilm.objectilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface ObjectIlm extends ImmutableLongMatrix
{
    public Object[] toArray()
    	throws DataInvalidException;
    public Object[] toArray(long rowStart, long columnStart,
    	int rowCount, int colCount) throws DataInvalidException;
    public void toArray(Object[] array, int offset,
    	long rowStart, long columnStart, int rowCount, int colCount)
    	throws DataInvalidException;
    public void toArray(Object[] array, int offset, int rowStride, int colStride,
        	long rowStart, long columnStart, int rowCount, int colCount)
        	throws DataInvalidException;
}