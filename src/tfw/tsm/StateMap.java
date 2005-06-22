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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import tfw.tsm.ecd.EventChannelDescription;

/**
 * A map of {@link EventChannelDescription} to values.
 */
public class StateMap {
	private final Map state;
	
	/**
	 * Creates an empty map.
	 */
	public StateMap(){
		this.state = new HashMap();
	}

	public StateMap(int size){
		this.state = new HashMap(size);
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		this.state.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(EventChannelDescription key) {
		return this.state.containsKey(key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		return this.state.containsValue(value);
	}


	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Object get(EventChannelDescription key) {
		return this.state.get(key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return this.state.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	public Set keySet() {
		return this.state.keySet();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(EventChannelDescription key, Object value) {
		return this.state.put(key, value);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public Object remove(EventChannelDescription key) {
		return this.state.remove(key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	public int size() {
		return this.state.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	public Collection values() {
		return this.state.values();
	}

	public String toString(){
		return state.toString();
	}
}
