package tfw.tsm.ecd.io;

import java.io.File;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class FileECD extends ObjectECD
{
	public FileECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(File.class));
	}
}
