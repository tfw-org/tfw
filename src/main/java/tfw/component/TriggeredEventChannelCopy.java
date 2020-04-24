/*
 * The Framework Project Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package tfw.component;

import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

/**
 * A component which copies the state of the input channel to the output channel
 * based on a trigger.
 */
public class TriggeredEventChannelCopy extends TriggeredConverter {
	private final ObjectECD inputECD;

	private final ObjectECD outputECD;

	/**
	 * Creates a component with specified attributes.
	 * 
	 * @param name
	 *            The name of this component.
	 * @param triggerECD
	 *            The description of the triggering event channel that causes
	 *            the copy to be performed.
	 * @param inputECD
	 *            The description of the input channel to be copied.
	 * @param outputECD
	 *            The description of the output channel into which the input
	 *            channels state is to be copied.
	 */
	public TriggeredEventChannelCopy(String name,
			StatelessTriggerECD triggerECD, ObjectECD inputECD,
			ObjectECD outputECD) {
		super("TriggeredEventChannelCopy[" + name + "]", triggerECD,
				new ObjectECD[] { inputECD },
				new ObjectECD[] { outputECD });
		if (outputECD.getConstraint().isCompatible(inputECD.getConstraint()) == false) {
			throw new IllegalArgumentException(
					"outputECD.getConstraint().isCompatible(inputECD.getConstraint()) == false not allowed");
		}

		this.inputECD = inputECD;
		this.outputECD = outputECD;
	}

	protected void convert() {
		set(outputECD, get(inputECD));
	}
}
