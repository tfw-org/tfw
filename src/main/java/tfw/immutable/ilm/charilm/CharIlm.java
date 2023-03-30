package tfw.immutable.ilm.charilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface CharIlm extends ImmutableLongMatrix
{
    public char[][] toArray()
    	throws DataInvalidException;
    public char[][] toArray(long rowStart, long columnStart,
    	int width, int height) throws DataInvalidException;
    public void toArray(char[][] array, int rowOffset, int columnOffset,
    	long rowStart, long columnStart, int width, int height)
    	throws DataInvalidException;
}