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

public final class ValidatorProxy implements Proxy
{
	private final Validator validator;
	
	public ValidatorProxy(Validator validator)
	{
		Argument.assertNotNull(validator, "validator");
		
		this.validator = validator;
	}
	
	public String getName()
	{
		return(validator.getName());
	}
	
	public SinkProxy[] getSinkProxies()
	{
		Object[] sinks = (Object[])validator.sinks.values().toArray();
		SinkProxy[] sp = new SinkProxy[sinks.length];
		
		for (int i=0 ; i < sinks.length ; i++)
		{
			sp[i] = new SinkProxy((Sink)sinks[i]);
		}
		return(sp);
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof ValidatorProxy)
		{
			ValidatorProxy ip = (ValidatorProxy)obj;
			
			return(validator.equals(ip.validator));
		}
		
		return(false);
	}
	
	public int hashCode()
	{
		return(validator.hashCode());
	}
}