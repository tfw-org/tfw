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

import tfw.tsm.ecd.EventChannelDescription;
import tfw.value.ValueException;


/**
 *
 */
class ProcessorSource extends Source
{
    private Object state = null;

    ProcessorSource(String name, EventChannelDescription ecd)
    {
        super(name, ecd);
    }

	Object getState()
	{
		return state;
	}

    void setState(Object state) throws ValueException
    {
    	setState(state, true);
    }
    
    void setState(Object state, boolean defer) throws ValueException
    {
        // This check is potentially invalid if a rollback occurs
        //        if (this.state != null)
        //        {
        //            throw new IllegalStateException(
        //                "Attempt to overwrite state on event channel '" +
        //                this.getEventChannelName() + "'.");
        //        }
        if (getEventChannel() == null)
        {
            throw new IllegalStateException(
                "Attempt to set state using disconnected source");
        }
		getConstraint().checkValue(state);

        this.state = state;
        if (defer)
        {
        	getEventChannel().addDeferredStateChange(this);
        }
        else
        {
        	fire();
        	getTreeComponent().getTransactionManager().addChangedEventChannel(
        		getEventChannel());
        }
    }

	/**
     * @see co2.ui.fw.Source#fire()
     */
    Object fire()
    {
        Object temp = state;
        state = null;
        getEventChannel().setState(this, temp, null);
        return temp;
    }
}
