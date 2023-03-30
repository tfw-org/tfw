package tfw.immutable.ilm.byteilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface ByteIlm extends ImmutableLongMatrix
{
    public byte[][] toArray()
    	throws DataInvalidException;
    public byte[][] toArray(long rowStart, long columnStart,
    	int width, int height) throws DataInvalidException;
    public void toArray(byte[][] array, int rowOffset, int columnOffset,
    	long rowStart, long columnStart, int width, int height)
    	throws DataInvalidException;
}