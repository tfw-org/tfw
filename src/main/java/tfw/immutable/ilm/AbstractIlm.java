package tfw.immutable.ilm;

import tfw.check.Argument;

public abstract class AbstractIlm implements ImmutableLongMatrix
{
    protected final long width;
    protected final long height;
    
    protected AbstractIlm(long width, long height)
    {
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");
    	
    	if (width == 0 && height != 0)
    		throw new IllegalArgumentException(
    			"width == 0 && height != 0 not allowed!");
    	if (height == 0 && width != 0)
    		throw new IllegalArgumentException(
    			"height == 0 && width != 0 not allowed!");
    	
    	this.width = width;
    	this.height = height;
    }

    public final long width()
    {
    	return(width);
    }
    
    public final long height()
    {
    	return(height);
    }

    protected final void boundsCheck(int arrayWidth, int arrayHeight,
    	int rowOffset, int columnOffset, long rowStart, long columnStart,
		int width, int height)
    {
    	Argument.assertNotLessThan(arrayWidth, 0, "arrayWidth");
    	Argument.assertNotLessThan(arrayHeight, 0, "arrayHeight");
    	Argument.assertNotLessThan(rowOffset, 0, "rowOffset");
    	Argument.assertNotLessThan(columnOffset, 0, "columnOffset");
    	Argument.assertNotLessThan(rowStart, 0, "rowStart");
    	Argument.assertNotLessThan(columnStart, 0, "columnStart");
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");
    	Argument.assertNotGreaterThan(columnOffset + width, arrayWidth,
    		"columnOffset + width", "arrayWidth");
    	Argument.assertNotGreaterThan(rowOffset + height, arrayHeight,
    		"rowOffset + height", "arrayHeight");
    	Argument.assertNotGreaterThan(rowStart + height, this.height,
    		"rowStart + height", "Ila.height");
    	Argument.assertNotGreaterThan(columnStart + width, this.width,
    		"columnStart + width", "Ila.width");
    }
}