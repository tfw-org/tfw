package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.longilm.LongIlm;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class LongIlmECD extends ObjectECD
{
	public LongIlmECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(LongIlm.class));
	}
}