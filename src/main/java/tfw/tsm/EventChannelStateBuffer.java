package tfw.tsm;

import java.util.HashMap;
import java.util.Map;

import tfw.tsm.ecd.ObjectECD;
import tfw.value.ValueException;


/**
 * A utility class for creating an array of {@link EventChannelState}.
 */
public class EventChannelStateBuffer
{
    private Map<String, EventChannelState> state =
    	new HashMap<String, EventChannelState>();

	/**
	 * Adds an {@link EventChannelState} based on the specified event 
	 * channel description and state.
	 * @param ecd The event channel description
	 * @param state The event channel state.
	 * @throws ValueException If the specified state is incompatible with 
	 * the specified event channel description.
	 */
    public void put(ObjectECD ecd, Object state)
        throws ValueException
    {
    	EventChannelState ecs = new EventChannelState(ecd, state);
        this.state.put(ecd.getEventChannelName(), ecs);
    }

	/**
	 * Returns the contents of the buffer.
	 * @return the contents of the buffer.
	 */
    public EventChannelState[] toArray()
    {
        return state.values().toArray(new EventChannelState[state.size()]);
    }
    
    /**
     * Clears the contents of the buffer.
     */
    public void clear(){
    	state.clear();
    }
    
    /**
     * Returns the number of item in the buffer.
     * @return the number of item in the buffer.
     */
    public int size(){
    	return state.size();
    }
}
