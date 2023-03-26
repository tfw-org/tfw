package tfw.awt.ecd;

import java.awt.Image;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class ImageECD extends ObjectECD
{
	public ImageECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(Image.class));
	}
}