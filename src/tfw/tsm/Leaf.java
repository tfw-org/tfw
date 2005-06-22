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


/**
 * The base class for all leaf components. DO NOT TRY TO EXTEND THIS CLASS. 
 * This class is made public for the purpose of providing a common super-class 
 * for all of leaf components in the framework.
 */
public class Leaf extends TreeComponent
{	
	/**
	 * Constructs a leaf component with the specified name.
	 * @param name the name of the component.
	 */
	Leaf(String name, Sink[] sinks, Source[] sources)
	{
		super(name, sinks, sources, null);
	}	
}
