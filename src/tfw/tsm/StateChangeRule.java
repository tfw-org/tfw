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

/**
 * Defines the rule for what constitutes a state change on an event channel.
 * Notification of a state change will only occur if
 * {@link #isChange(Object, Object)}returns <code>true</code>. A state
 * change rule can be set when an event channel is terminated at the root or branch
 * level. See {@link RootFactory} and {@link BranchFactory}.
 */
public interface StateChangeRule
{
    /**
     * Returns true if the new state represents a change from the current state
     * 
     * @param currentState
     *            the current state of the event channel. A <code>null</code>
     *            value is allowed as the initial state.
     * @param newState
     *            the new state for the event channel. May not be
     *            <code>null</code>.
     * @return <code>true</code> if <code>newState</code> represents a
     *         change relative to the <code>currentState</code>.
     * @throws IllegalArgumentException
     *             if <code>newSate == null</code>.
     */
    public boolean isChange(Object currentState, Object newState);
}
