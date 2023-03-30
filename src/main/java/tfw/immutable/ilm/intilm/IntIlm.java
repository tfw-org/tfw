package tfw.immutable.ilm.intilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface IntIlm extends ImmutableLongMatrix
{
    public int[][] toArray()
    	throws DataInvalidException;
    public int[][] toArray(long rowStart, long columnStart,
    	int width, int height) throws DataInvalidException;
    public void toArray(int[][] array, int rowOffset, int columnOffset,
    	long rowStart, long columnStart, int width, int height)
    	throws DataInvalidException;
}