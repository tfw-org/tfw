/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
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
 */
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