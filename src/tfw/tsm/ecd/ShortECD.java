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
package tfw.tsm.ecd;

import tfw.value.ClassValueConstraint;
import tfw.value.ShortCodec;
import tfw.value.ShortConstraint;

/**
 * A <code>java.lang.Short</code> event channel descritpion
 */
public class ShortECD extends EventChannelDescription {
	
	/**
	 * Creates an event channel description with the specified attribute.
	 * @param name the name of the event channel.
	 * @param min the minimum value allowed.
	 * @param max the maximum value allowed.
	 */
	public ShortECD(String name, short min, short max)
	{
		super(name, new ShortConstraint(min, max), ShortCodec.INSTANCE);
	}
	
	/**
	 *  Creates an short event channel description with the specified name.
	 * @param name The name of the event channel.
	 */
	public ShortECD(String name){
		super(name, ClassValueConstraint.SHORT, ShortCodec.INSTANCE);
	}
}
