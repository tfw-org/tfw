/*
 * The Framework Project
 * Copyright (C) 2006 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */package tfw.component;

import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class TriggeredEventChannelSet extends TriggeredConverter
{
	private final ObjectECD[] outputECDs;
	private final Object[] constants;
	
	public TriggeredEventChannelSet(String name, StatelessTriggerECD triggerECD,
		ObjectECD outputECD, Object constant)
	{
		this(name, triggerECD, new ObjectECD[] {outputECD},
			new Object[] {constant});
	}
	public TriggeredEventChannelSet(String name, StatelessTriggerECD triggerECD,
		ObjectECD[] outputECDs, Object[] constants)
	{
		super("TriggeredEventChannelSet[" + name +"]",
			triggerECD,
			null,
			outputECDs);

		this.outputECDs = new ObjectECD[outputECDs.length];
		System.arraycopy(outputECDs, 0, this.outputECDs, 0, outputECDs.length);
		this.constants = new Object[constants.length];
		System.arraycopy(constants, 0, this.constants, 0, constants.length);
	}

	protected void convert()
	{
		for (int i=0 ; i < outputECDs.length ; i++)
		{
			set(outputECDs[i], constants[i]);
		}
	}
}