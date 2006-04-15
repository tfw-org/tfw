/*
 * Created on Feb 13, 2006
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    /* (non-Javadoc)
     * @see tfw.tsm.MultiplexerStrategy#toMultiStateAccessor(java.lang.Object)
     */
    public MultiStateAccessor toMultiStateAccessor(Object multiState)
    {
        Argument.assertNotNull(multiState, "multiState");
        return new MyMultiStateAccessor((ObjectIla) multiState);
    }

    /* (non-Javadoc)
     * @see tfw.tsm.MultiplexerStrategy#toMultiStateFactory(java.lang.Object)
     */
    public MultiStateFactory toMultiStateFactory(Object multiState)
    {
        return new MyMultiStateFactory((ObjectIla) multiState);
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

        /*
         * (non-Javadoc)
         * 
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

    private class MyMultiStateFactory implements MultiStateFactory
    {

        List list = new ArrayList();

        MyMultiStateFactory(ObjectIla ila)
        {
            if (ila != null)
            {
                try
                {
                    list.addAll(Arrays.asList(ila.toArray()));
                }
                catch (DataInvalidException e)
                {
                    throw new RuntimeException(
                            "Exception occurred accessing multiplexed state:"
                                    + e.getMessage(), e);
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see tfw.tsm.MultiplexerStrategy.MultiStateFactory#setState(java.lang.Object,
         *      java.lang.Object)
         */
        public void setState(Object key, Object state)
        {
            int index = ((Integer) key).intValue();
            while (list.size() - 1 < index)
            {
                list.add(null);
            }
            list.set(index, state);
        }

        /*
         * (non-Javadoc)
         * 
         * @see tfw.tsm.MultiplexerStrategy.MultiStateFactory#toMultiState()
         */
        public Object toMultiState()
        {
            return ObjectIlaFromArray.create(this.list.toArray());
        }
    }

}
