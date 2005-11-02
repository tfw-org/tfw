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

/**
 * A state change rule for always notifing sinks when state is published.
 */
public class AlwaysChangeRule implements StateChangeRule
{
    /** An instance of the rule. */
    public static final AlwaysChangeRule RULE = new AlwaysChangeRule();

    /**
     * Hide constructor to avoid un-needed object creation. This rule is
     * stateless and therefore only one instance is needed.
     */
    private AlwaysChangeRule()
    {
    }

    /**
     * Returns an instance of the rule.
     * 
     * @return an instance of the rule.
     */
    public static AlwaysChangeRule getInstance()
    {
        return RULE;
    }

    /**
     * Returns <code>true</code> reqardless of the specified values.
     * 
     * @param currentState
     *            the current event channel state.
     * @param newState
     *            the new state value for the event channel.
     * @return <code>true</code> reqardless of the specified values.
     */
    public boolean isChange(Object currentState, Object newState)
    {
        return true;
    }
}
