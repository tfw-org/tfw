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
package tfw.awt.event;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tfw.tsm.Initiator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.EventChannelDescriptionUtil;
import tfw.tsm.ecd.StatelessTriggerECD;

public class WindowInitiator extends Initiator implements WindowListener
{
	private final StatelessTriggerECD windowActivatedTriggerECD;
	private final StatelessTriggerECD windowClosedTriggerECD;
	private final StatelessTriggerECD windowClosingTriggerECD;
	private final StatelessTriggerECD windowDeactivatedTriggerECD;
	private final StatelessTriggerECD windowDeiconifiedTriggerECD;
	private final StatelessTriggerECD windowIconifiedTriggerECD;
	private final StatelessTriggerECD windowOpenedTriggerECD;
	
	public WindowInitiator(String name,
		StatelessTriggerECD windowActivatedTriggerECD,
		StatelessTriggerECD windowClosedTriggerECD,
		StatelessTriggerECD windowClosingTriggerECD,
		StatelessTriggerECD windowDeactivatedTriggerECD,
		StatelessTriggerECD windowDeiconifiedTriggerECD,
		StatelessTriggerECD windowIconifiedTriggerECD,
		StatelessTriggerECD windowOpenedTriggerECD)
	{
		super("WindowInitiator["+name+"]",
			EventChannelDescriptionUtil.create(new EventChannelDescription[] {
				windowActivatedTriggerECD, windowClosedTriggerECD,
				windowClosingTriggerECD, windowDeactivatedTriggerECD,
				windowDeiconifiedTriggerECD, windowIconifiedTriggerECD,
				windowOpenedTriggerECD}));
					
		this.windowActivatedTriggerECD = windowActivatedTriggerECD;
		this.windowClosedTriggerECD = windowClosedTriggerECD;
		this.windowClosingTriggerECD = windowClosingTriggerECD;
		this.windowDeactivatedTriggerECD = windowDeactivatedTriggerECD;
		this.windowDeiconifiedTriggerECD = windowDeiconifiedTriggerECD;
		this.windowIconifiedTriggerECD = windowIconifiedTriggerECD;
		this.windowOpenedTriggerECD = windowOpenedTriggerECD;
	}
	
	public final void windowActivated(WindowEvent e)
	{
		if (windowActivatedTriggerECD != null)
		{
			trigger(windowActivatedTriggerECD);
		}
	}
	
	public final void windowClosed(WindowEvent e)
	{
		if (windowClosedTriggerECD != null)
		{
			trigger(windowClosedTriggerECD);
		}
	}
	
	public final void windowClosing(WindowEvent e)
	{
		if (windowClosingTriggerECD != null)
		{
			trigger(windowClosingTriggerECD);
		}
	}
	
	public final void windowDeactivated(WindowEvent e)
	{
		if (windowDeactivatedTriggerECD != null)
		{
			trigger(windowDeactivatedTriggerECD);
		}
	}
	
	public final void windowDeiconified(WindowEvent e)
	{
		if (windowDeiconifiedTriggerECD != null)
		{
			trigger(windowDeiconifiedTriggerECD);
		}
	}
	
	public final void windowIconified(WindowEvent e)
	{
		if (windowIconifiedTriggerECD != null)
		{
			trigger(windowIconifiedTriggerECD);
		}
	}
	
	public final void windowOpened(WindowEvent e)
	{
		if (windowOpenedTriggerECD != null)
		{
			trigger(windowOpenedTriggerECD);
		}
	}
}
