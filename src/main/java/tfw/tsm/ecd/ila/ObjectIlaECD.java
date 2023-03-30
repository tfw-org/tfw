package tfw.tsm.ecd.ila;

import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class ObjectIlaECD extends ObjectECD
{
	public ObjectIlaECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(ObjectIla.class));
	}
}
