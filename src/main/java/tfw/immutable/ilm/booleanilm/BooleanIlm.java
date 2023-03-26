package tfw.immutable.ilm.booleanilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface BooleanIlm extends ImmutableLongMatrix
{
    public boolean[][] toArray()
    	throws DataInvalidException;
    public boolean[][] toArray(long rowStart, long columnStart,
    	int width, int height) throws DataInvalidException;
    public void toArray(boolean[][] array, int rowOffset, int columnOffset,
    	long rowStart, long columnStart, int width, int height)
    	throws DataInvalidException;
}