package tfw.demo;

import tfw.value.IntegerConstraint;


/**
 * The constraint for RGB values.
 */
public class RGBConstraint 
{
	/** The constraint for RGB values. */
    public static final IntegerConstraint CONSTRAINT = new IntegerConstraint(0, 255);
}
