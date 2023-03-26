package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.objectilm.ObjectIlm;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class ObjectIlmECD extends ObjectECD
{
	public ObjectIlmECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(ObjectIlm.class));
	}
}