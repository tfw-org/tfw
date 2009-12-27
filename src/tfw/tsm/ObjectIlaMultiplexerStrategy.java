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

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;

/**
 * A strategy for multiplexing/de-multiplexing an {@link ObjectIla} event
 * channel.
 */
public class ObjectIlaMultiplexerStrategy implements MultiplexerStrategy
{
    /**
     * @see tfw.tsm.MultiplexerStrategy#toMultiStateAccessor(java.lang.Object)
     */
    public MultiStateAccessor toMultiStateAccessor(Object multiState)
    {
        Argument.assertNotNull(multiState, "multiState");
        return new MyMultiStateAccessor((ObjectIla) multiState);
    }

    public Object getDefaultSlotState(){
    	return null;
    }
    private class MyMultiStateAccessor implements MultiStateAccessor
    {

        private final Object[] objs;

        MyMultiStateAccessor(ObjectIla ila)
        {
            try
            {
                this.objs = ila.toArray();
            }
            catch (DataInvalidException e)
            {
                throw new RuntimeException(
                        "Exception occurred accessing multiplexed state:"
                                + e.getMessage(), e);
            }
        }

        /**
         * @see tfw.tsm.MultiplexerStrategy.MultiStateAccessor#getState(java.lang.Object)
         */
        public Object getState(Object key)
        {
            int index = ((Integer) key).intValue();
            if ((index >= 0) && (index < this.objs.length))
            {
                return objs[index];
            }
            return null;
        }
    }
    
    public Object addToMultiState(Object originalMultiState, Object[] keys,
    	Object[] values, int numberOfKeyValuePairs)
    {
    	Object[] array = null;
    	
    	try
    	{
    		array = ((ObjectIla)originalMultiState).toArray();
    	}
    	catch (DataInvalidException e)
    	{
    		throw new RuntimeException(
    			"Exception occurred accessing multiplexed state:" +
    				e.getMessage(), e);
    	}
    	
    	for (int i=0 ; i < numberOfKeyValuePairs ; i++)
    	{
    		int index = ((Integer)keys[i]).intValue();
    		
    		if (array.length -1 < index)
    		{
    			Object[] newArray = new Object[index+1];
    			System.arraycopy(array, 0, newArray, 0, array.length);
    			array = newArray;
    		}
    		
    		array[index] = values[i];
    	}
    	
    	return ObjectIlaFromArray.create(array, false);
    }
}