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
        if (eventChannel == null)
        {
            throw new IllegalStateException(
                "Attempt to set state using disconnected source");
        }
		ecd.getConstraint().checkValue(state);

        this.state = state;
        if (defer)
        {
        	eventChannel.addDeferredStateChange(this);
        }
        else
        {
        	fire();
        	getTreeComponent().getTransactionManager().addChangedEventChannel(
        		eventChannel);
        }
    }

	/**
     * @see co2.ui.fw.Source#fire()
     */
    Object fire()
    {
        Object temp = state;
        state = null;
        eventChannel.setState(this, temp, null);
        return temp;
    }
}
