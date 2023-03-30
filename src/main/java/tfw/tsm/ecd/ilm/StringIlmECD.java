package tfw.tsm.ecd.ilm;

import tfw.immutable.ilm.stringilm.StringIlm;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class StringIlmECD extends ObjectECD
{
	public StringIlmECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(StringIlm.class));
	}
}