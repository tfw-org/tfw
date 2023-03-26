package tfw.tsm.ecd.ila;

import tfw.immutable.ila.stringila.StringIla;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class StringIlaECD extends ObjectECD
{
	public StringIlaECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(StringIla.class));
	}
}
