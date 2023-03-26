package tfw.awt.ecd;

import java.awt.FontMetrics;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class FontMetricsECD extends ObjectECD
{
	public FontMetricsECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(FontMetrics.class));
	}
}
