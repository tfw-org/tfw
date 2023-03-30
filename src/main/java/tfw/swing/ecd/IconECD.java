package tfw.swing.ecd;

import javax.swing.Icon;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class IconECD extends ObjectECD
{
	public IconECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(Icon.class));
	}
}
