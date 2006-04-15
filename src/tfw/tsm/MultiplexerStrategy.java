/*
 * Created on Feb 12, 2006
 *
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
 * A strategy for multiplexing and demultiplexing state.
 */
public interface MultiplexerStrategy
{
    /**
     * Provides an accessor given a multi-state object.
     * 
     * @param multiState
     *            The multi-state object to be accessed.
     * @return An accessor initialized with the specified state.
     */
    public MultiStateAccessor toMultiStateAccessor(Object multiState);
    
    /**
     * Provides a factory for build a multi-state object.
     * @param multiState The initial state. This value may be <code>null</code>.
     * @return A factory for creating a multi-state object.
     */
    public MultiStateFactory toMultiStateFactory(Object multiState);

    
    public Object getDefaultSlotState();
    
    /**
     * An interface for accessing a multiplexed state object.
     */
    public interface MultiStateAccessor
    {
        /**
         * Gets the state associated with the specified key.
         * 
         * @param key
         *            The key to use in find an associated state value.
         * @return The value associated with the key or <code>null</code> if
         *         no state is associated with the specified key.
         */
        public Object getState(Object key);

    }
    
    /**
     * An interface for creating a multi-state object.
     */
    public interface MultiStateFactory{
        /**
         * Sets the state associated with the given key.
         * @param key The key to which the state is associated.
         * @param state The new state value.
         */
        public void setState(Object key, Object state);

        /**
         * Returns an object which represents the multiplexed state.
         * @return The multi-state object.
         */
        public Object toMultiState();
    }
}
