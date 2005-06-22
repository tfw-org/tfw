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
package tfw.awt.event;

import	java.awt.event.ComponentEvent;
import	java.awt.event.ComponentListener;
import	tfw.tsm.Initiator;
import	tfw.tsm.EventChannelStateBuffer;
import	tfw.tsm.ecd.BooleanECD;
import	tfw.tsm.ecd.EventChannelDescription;
import	tfw.tsm.ecd.EventChannelDescriptionUtil;
import	tfw.tsm.ecd.IntegerECD;

public class ComponentInitiator extends Initiator implements ComponentListener
{
	private final BooleanECD visibleECD;
	private final IntegerECD xECD;
	private final IntegerECD yECD;
	private final IntegerECD widthECD;
	private final IntegerECD heightECD;
	
	public ComponentInitiator(String name, BooleanECD visibleECD,
		IntegerECD xECD, IntegerECD yECD, IntegerECD widthECD,
		IntegerECD heightECD)
	{
		super("ComponentInitiator["+name+"]",
			EventChannelDescriptionUtil.create(new EventChannelDescription[] {
				visibleECD, xECD, yECD, widthECD, heightECD}));
		
		this.visibleECD = visibleECD;
		this.xECD = xECD;
		this.yECD = yECD;
		this.widthECD = widthECD;
		this.heightECD = heightECD;
	}
	
	public final void componentHidden(ComponentEvent e)
	{
		if (visibleECD != null)
		{
			set(visibleECD, Boolean.FALSE);
		}
	}
	
	public final void componentMoved(ComponentEvent e)
	{
		if (xECD == null)
		{
			if (yECD == null)
			{
				// Do Nothing...
			}
			else
			{
				set(yECD, new Integer(e.getComponent().getLocation().y));
			}
		}
		else
		{
			if (yECD == null)
			{
				set(xECD, new Integer(e.getComponent().getLocation().x));
			}
			else
			{
				EventChannelStateBuffer ecsb = new EventChannelStateBuffer();
				
				ecsb.put(xECD, new Integer(e.getComponent().getLocation().x));
				ecsb.put(yECD, new Integer(e.getComponent().getLocation().y));

				set(ecsb.toArray());
			}
		}
	}
	
	public final void componentResized(ComponentEvent e)
	{
		if (widthECD == null)
		{
			if (heightECD == null)
			{
				// Do Nothing...
			}
			else
			{
				set(heightECD, new Integer(e.getComponent().getSize().height));
			}
		}
		else
		{
			if (heightECD == null)
			{
				set(widthECD, new Integer(e.getComponent().getSize().width));
			}
			else
			{
				EventChannelStateBuffer ecsb = new EventChannelStateBuffer();
				
				ecsb.put(widthECD, new Integer(e.getComponent().getSize().width));
				ecsb.put(heightECD, new Integer(e.getComponent().getSize().height));
				
				set(ecsb.toArray());
			}
		}
	}
	
	public final void componentShown(ComponentEvent e)
	{
		if (visibleECD != null)
		{
			set(visibleECD, Boolean.TRUE);
		}
	}
}
