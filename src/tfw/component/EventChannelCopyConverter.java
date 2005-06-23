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
 * witout even the implied warranty of
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
import tfw.tsm.ecd.EventChannelDescription;

public class EventChannelCopyConverter extends Converter
{
	private final EventChannelDescription inputECD;
	private final EventChannelDescription outputECD;

	public EventChannelCopyConverter(String name,
		EventChannelDescription inputECD, EventChannelDescription outputECD)
	{
		super("TriggeredEventChannelCopy[" + name + "]",
			new EventChannelDescription[] {inputECD},
			null,
			new EventChannelDescription[] {outputECD});
		
		if (outputECD.getConstraint().isCompatable(
			inputECD.getConstraint()) == false)
		{
			throw new IllegalArgumentException(
				"outputECD.getConstraint().isCompatable("+
				"inputECD.getConstraint()) == false not allowed");
		}

		this.inputECD = inputECD;
		this.outputECD = outputECD;
	}

	protected void convert()
	{
		set(outputECD, get(inputECD));
	}
}
