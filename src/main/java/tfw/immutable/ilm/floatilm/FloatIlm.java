package tfw.immutable.ilm.floatilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface FloatIlm extends ImmutableLongMatrix
{
    public float[][] toArray()
    	throws DataInvalidException;
    public float[][] toArray(long rowStart, long columnStart,
    	int width, int height) throws DataInvalidException;
    public void toArray(float[][] array, int rowOffset, int columnOffset,
    	long rowStart, long columnStart, int width, int height)
    	throws DataInvalidException;
}