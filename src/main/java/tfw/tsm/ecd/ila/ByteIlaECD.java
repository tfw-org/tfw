package tfw.tsm.ecd.ila;

import tfw.immutable.ila.byteila.ByteIla;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class ByteIlaECD extends ObjectECD
{
	public ByteIlaECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(ByteIla.class));
	}
}
