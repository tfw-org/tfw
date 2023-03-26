package tfw.component;

import tfw.tsm.Converter;
import tfw.tsm.ecd.ObjectECD;

public class EventChannelCopyConverter extends Converter
{
	private final ObjectECD[] inputECDs;
	private final ObjectECD[] outputECDs;

	public EventChannelCopyConverter(String name,
		ObjectECD inputECD, ObjectECD outputECD)
	{
		this(name, new ObjectECD[] {inputECD}, new ObjectECD[] {outputECD});
	}
	
	public EventChannelCopyConverter(String name,
		ObjectECD[] inputECDs, ObjectECD[] outputECDs)
	{
		super("EventChannelCopyConverter[" + name + "]",
			inputECDs,
			null,
			outputECDs);
		
		for (int i=0 ; i < inputECDs.length ; i++)
		{
			if (outputECDs[i].getConstraint().isCompatible(
					inputECDs[i].getConstraint()) == false)
				{
					throw new IllegalArgumentException(
						"outputECD.getConstraint().isCompatible("+
						"inputECD.getConstraint()) == false not allowed");
				}
		}

		this.inputECDs = inputECDs;
		this.outputECDs = outputECDs;
	}

	protected void convert()
	{
		for (int i=0 ; i < inputECDs.length ; i++)
		{
			set(outputECDs[i], get(inputECDs[i]));
		}
	}
}