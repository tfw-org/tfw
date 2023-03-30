package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.intilm.IntIlm;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class IntIlmECD extends ObjectECD
{
	public IntIlmECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(IntIlm.class));
	}
}