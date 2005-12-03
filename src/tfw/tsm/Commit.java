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
package tfw.tsm;

import tfw.tsm.ecd.ObjectECD;

/**
 * The base class for component which process state in the commit phase of
 * a transaction. Note that no state can be changed during the commit phase.
 */
public abstract class Commit extends BaseCommit {

	/**
	 * Creates a commit component with the specified values.
	 * @param name the name of the commit component.
	 * @param triggerSinks the set of event channels which this
	 * commit component will process.
	 */
	public Commit(String name, ObjectECD[] triggerSinks) {
		this(name, triggerSinks, null, null);
	}

	/**
	 * Creates a commit component with the specified values.
	 *
	 * @param name the name of the component.
	 *
	 * @param triggerSinks the set of event channel sinks which cause the
	 * commit components {@link #commit()} method to be called when their
	 * state is changed.
	 *
	 * @param nonTriggerSinks the set of event channel sinks whose state the
	 * commit component needs, but whose state changes do not cause the
	 * component to call it's {@link #commit()} method.
	 *
	 * @param initiators the set of {@link Initiator}s whose state changes
	 * this commit component will ignore. For example if an initiator sources
	 * events on event channel 'A' and 'A' is one of the event channels
	 * specified in the <code>triggerSinks</code> list, the {@link #commit()}
	 * method will not be called as a result of the initiator changing 'A's
	 * state.
	 *
	 * @throws IllegalArgumentException if <code>triggerSinks == null</code>.
	 * @throws IllegalArgumentException if <code>triggerSinks.length == 0</code>.
	 * @throws IllegalArgumentException if <code>triggerSinks[i] == null</code>.
	 */
	public Commit(
		String name,
		ObjectECD[] triggerSinks,
		ObjectECD[] nonTriggerSinks,
		Initiator[] initiators) {
		super(name, triggerSinks, nonTriggerSinks, initiators);
	}
}
