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

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.RollbackECD;

/**
 * The base class for all components which perform validation in the 
 * validation phase of a transaction. Subclasses must implement the
 * {@link #validateState()} method.
 */
public abstract class Validator extends RollbackHandler
{
    /**
     * Creates a validate with the specified attributes.
     * @param name the name of this validator component.
     * @param triggeringSinks the set of sinks which trigger this validator.
     *
     */
    public Validator(String name, EventChannelDescription[] triggeringSinks,
        RollbackECD[] initiators)
    {
        this(name, triggeringSinks, null, initiators);
    }

	/**
	 * Creates a validator with the specified attriubutes.
     * @param name the name of this validator component.
     * @param triggeringSinks the set of sinks which trigger this validator.
	 * @param nonTriggeringSinks the set of sinks which do not trigger this
	 * validator, but are used in validation. Note that the event
	 * channels associated with these sinks must be non-null before the 
	 * {@link #validateState()} method will be called.
	 * @param initiators the set of sources used to initiate new transactions.
	 */
    public Validator(String name, EventChannelDescription[] triggeringSinks,
        EventChannelDescription[] nonTriggeringSinks,
        RollbackECD[] initiators)
    {
        super(name, checkSinks(triggeringSinks), nonTriggeringSinks, initiators);
		checkForStatelessTrigger(triggeringSinks, "trigeringSinks");
		checkForStatelessTrigger(nonTriggeringSinks, "nonTriggeringSinks");
    }

    private static EventChannelDescription[] checkSinks(
        EventChannelDescription[] triggeringSinks)
    {
        Argument.assertNotNull(triggeringSinks, "triggeringSinks");
        return triggeringSinks;
    }

    final void stateChange(EventChannel eventChannel)
    {
        getTransactionManager().addValidator(this);
    }

	/**
	 * Called in the validation phase of a transaction where one or more of 
	 * the triggering event channels specified at construction has its state
	 * changed.
	 */
    protected abstract void validateState();
}
