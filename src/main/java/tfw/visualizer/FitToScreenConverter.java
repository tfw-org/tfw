package tfw.visualizer;

import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class FitToScreenConverter extends TriggeredConverter
{
	private final IntegerECD widthECD;
	private final IntegerECD heightECD;
	private final IntegerECD xECD;
	private final IntegerECD yECD;
	private final IntegerECD graphWidthECD;
	private final IntegerECD graphHeightECD;
	
	public FitToScreenConverter(StatelessTriggerECD triggerECD,
		IntegerECD widthECD, IntegerECD heightECD, IntegerECD xECD,
		IntegerECD yECD, IntegerECD graphWidthECD, IntegerECD graphHeightECD)
	{
		super("FitToScreenConverter",
			triggerECD,
			new ObjectECD[] {widthECD, heightECD},
			new ObjectECD[] {xECD, yECD, graphWidthECD,
				graphHeightECD});
		
		this.widthECD = widthECD;
		this.heightECD = heightECD;
		this.xECD = xECD;
		this.yECD = yECD;
		this.graphWidthECD = graphWidthECD;
		this.graphHeightECD = graphHeightECD;
	}
	
	protected void convert()
	{
		set(xECD, new Integer(0));
		set(yECD, new Integer(0));
		set(graphWidthECD, get(widthECD));
		set(graphHeightECD, get(heightECD));
	}
}