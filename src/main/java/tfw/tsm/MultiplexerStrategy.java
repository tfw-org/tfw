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
    
    public Object addToMultiState(Object originalMultiState, Object[] keys,
    	Object[] values, int numberOfKeyValues);
}
