package tfw.immutable.ilm.objectilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface ObjectIlm extends ImmutableLongMatrix
{
    public Object[][] toArray()
    	throws DataInvalidException;
    public Object[][] toArray(long rowStart, long columnStart,
    	int width, int height) throws DataInvalidException;
    public void toArray(Object[][] array, int rowOffset, int columnOffset,
    	long rowStart, long columnStart, int width, int height)
    	throws DataInvalidException;
}