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
package tfw.value;

import java.util.HashMap;
import java.util.Map;

/**
 * A map of values. 
 */
public class ValueMap {
	/** The map of values. */
	private final Map map;
	
	/**
	 * Creates an map preloaded with the specified values..
	 */
	public ValueMap(Value[]	values){
		this.map = new HashMap(values.length);
		for(int i = 0; i < values.length; i++){
			map.put(values[i].getVD().getName(), values[i]);
		}
	}
	
	/**
	 * Returns the value with the specified name.
	 * 
	 * @param name the name of the value.
	 * 
	 * @return the event channel state for the specified event channel.
	 */
	public Value getValue(String name){
		return (Value)this.map.get(name);
	}
}
