package tfw.awt.ecd;

import java.awt.image.ImageObserver;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class ImageObserverECD extends ObjectECD
{
	public ImageObserverECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(ImageObserver.class));
	}
}