package tfw.visualizer;

import java.awt.Font;

import tfw.awt.ecd.FontECD;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class ChangeFontSizeConverter extends TriggeredConverter
{
	private final FontECD fontECD;
	private final float scale;
	
	public ChangeFontSizeConverter(String name, StatelessTriggerECD triggerECD,
		FontECD fontECD, float scale)
	{
		super("IncreaseFontConverter["+name+"]",
			triggerECD,
			new ObjectECD[] {fontECD},
			new ObjectECD[] {fontECD});
		
		this.fontECD = fontECD;
		this.scale = scale;
	}
	
	protected void convert()
	{
		Font font = (Font)get(fontECD);
		float newSize = font.getSize2D() + scale;
		
		if (newSize > 0.0f)
		{
			set(fontECD, font.deriveFont(newSize));
		}
	}
}