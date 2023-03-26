package tfw.tsm.ecd.ila;

import tfw.immutable.ila.floatila.FloatIla;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class FloatIlaECD extends ObjectECD
{
	public FloatIlaECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(FloatIla.class));
	}
}
