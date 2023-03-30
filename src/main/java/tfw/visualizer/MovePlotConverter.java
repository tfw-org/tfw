package tfw.visualizer;

import tfw.tsm.Converter;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.IntegerECD;

public class MovePlotConverter extends Converter
{
	private final BooleanECD selectedECD;
	private final IntegerECD xECD;
	private final IntegerECD yECD;
	private final BooleanECD button1ECD;
	private final BooleanECD button2ECD;
	private final BooleanECD button3ECD;
	private final IntegerECD xOffsetECD;
	private final IntegerECD yOffsetECD;
	
	public MovePlotConverter(BooleanECD selectedECD, IntegerECD xECD,
		IntegerECD yECD, BooleanECD button1ECD, BooleanECD button2ECD,
		BooleanECD button3ECD, IntegerECD xOffsetECD, IntegerECD yOffsetECD)
	{
		super("MovePlotConverter",
			new ObjectECD[] {selectedECD, xECD, yECD,
				button1ECD, button2ECD, button3ECD},
			new ObjectECD[] {xOffsetECD, yOffsetECD},
			new ObjectECD[] {xOffsetECD, yOffsetECD});
		
		this.selectedECD = selectedECD;
		this.xECD = xECD;
		this.yECD = yECD;
		this.button1ECD = button1ECD;
		this.button2ECD = button2ECD;
		this.button3ECD = button3ECD;
		this.xOffsetECD = xOffsetECD;
		this.yOffsetECD = yOffsetECD;
	}
	
	protected void convert()
	{
		if (((Boolean)get(selectedECD)).booleanValue())
		{
			boolean button1 = ((Boolean)get(button1ECD)).booleanValue();
			boolean button2 = ((Boolean)get(button2ECD)).booleanValue();
			boolean button3 = ((Boolean)get(button3ECD)).booleanValue();
	
			if (button1 && !button2 && !button3)
			{
				int x = ((Integer)get(xECD)).intValue();
				int y = ((Integer)get(yECD)).intValue();
				int previousX = ((Integer)getPreviousCycleState(xECD)).intValue();
				int previousY = ((Integer)getPreviousCycleState(yECD)).intValue();
				int xOffset = ((Integer)get(xOffsetECD)).intValue();
				int yOffset = ((Integer)get(yOffsetECD)).intValue();
				
				set(xOffsetECD, new Integer(xOffset+(x - previousX)));
				set(yOffsetECD, new Integer(yOffset+(y - previousY)));
			}
		}
	}
}