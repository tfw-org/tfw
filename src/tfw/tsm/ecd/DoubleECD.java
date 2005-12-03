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
import tfw.value.DoubleCodec;
import tfw.value.DoubleConstraint;

/**
 * A <code>java.lang.Double</code> event channel descritpion.
 */
public class DoubleECD extends ObjectECD {
	
	/**
	 * Creates an event channel description with the specified attribute.
	 * @param name the name of the event channel.
	 * @param min the minimum value allowed.
	 * @param max the maximum value allowed.
     * @param minInclusive if true <code>min</code> is a valid value,
     * otherwise it is not valid.
     * @param maxInclusive if true <code>max</code> is a valid value,
     * otherwise it is not valid.
	 */
	public DoubleECD(String name, double min, double max, boolean minInclusive, boolean maxInclusive)
	{
		super(name, new DoubleConstraint(min, max, minInclusive, maxInclusive), DoubleCodec.INSTANCE);
	}
	
	/**
	 * Creates an event channel description with the specified name.
	 * @param name the name of the event channel.
	 */
	public DoubleECD(String name){
		super(name, ClassValueConstraint.DOUBLE, DoubleCodec.INSTANCE);
	}

}
