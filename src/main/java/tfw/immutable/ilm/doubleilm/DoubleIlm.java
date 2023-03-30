package tfw.immutable.ilm.doubleilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface DoubleIlm extends ImmutableLongMatrix
{
    public double[][] toArray()
    	throws DataInvalidException;
    public double[][] toArray(long rowStart, long columnStart,
    	int width, int height) throws DataInvalidException;
    public void toArray(double[][] array, int rowOffset, int columnOffset,
    	long rowStart, long columnStart, int width, int height)
    	throws DataInvalidException;
}