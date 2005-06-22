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

import	java.awt.event.MouseEvent;
import	java.awt.event.MouseListener;
import	java.awt.event.MouseMotionListener;
import	tfw.tsm.Initiator;
import	tfw.tsm.ecd.BooleanECD;
import	tfw.tsm.ecd.EventChannelDescription;
import	tfw.tsm.ecd.IntegerECD;

public class MouseInitiator extends Initiator
	implements MouseListener,MouseMotionListener
{
	private final IntegerECD xECD;
	private final IntegerECD yECD;
	private final BooleanECD button1PressedECD;
	private final BooleanECD button2PressedECD;
	private final BooleanECD button3PressedECD;
	private final BooleanECD insideComponentECD;
	
	public MouseInitiator(String name, IntegerECD xECD, IntegerECD yECD,
		BooleanECD button1PressedECD, BooleanECD button2PressedECD,
		BooleanECD button3PressedECD, BooleanECD insideComponentECD)
	{
		super("MouseInitiator["+name+"]",
			new EventChannelDescription[] {});
		
		this.xECD = xECD;
		this.yECD = yECD;
		this.button1PressedECD = button1PressedECD;
		this.button2PressedECD = button2PressedECD;
		this.button3PressedECD = button3PressedECD;
		this.insideComponentECD = insideComponentECD;
	}
	
	public void mouseClicked(MouseEvent e)
	{
		// ?????
	}
	
	public void mouseEntered(MouseEvent e)
	{
		if (insideComponentECD != null)
		{
			set(insideComponentECD, new Boolean(true));
		}
	}
	
	public void mouseExited(MouseEvent e)
	{	
		if (insideComponentECD != null)
		{
			set(insideComponentECD, new Boolean(false));
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		sendButtonState(e);
	}
	
	public void mouseReleased(MouseEvent e)
	{
		sendButtonState(e);
	}
	
	public void mouseDragged(MouseEvent e)
	{
		sendPosition(e);
	}
	
	public void mouseMoved(MouseEvent e)
	{
		sendPosition(e);
	}

	private void sendPosition(MouseEvent e)
	{
		if (xECD != null)
		{
			set(xECD, new Integer(e.getX()));
		}
		if (yECD != null)
		{
			set(yECD, new Integer(e.getY()));
		}
	}
	private void sendButtonState(MouseEvent e)
	{
		if (button1PressedECD != null)
		{
			set(button1PressedECD, new Boolean(
				((e.getModifiers() & MouseEvent.BUTTON1_DOWN_MASK) ==
				MouseEvent.BUTTON1_DOWN_MASK)));
		}
		if (button2PressedECD != null)
		{
			set(button2PressedECD, new Boolean(
				((e.getModifiers() & MouseEvent.BUTTON2_DOWN_MASK) ==
				MouseEvent.BUTTON2_DOWN_MASK)));
		}
		if (button3PressedECD != null)
		{
			set(button3PressedECD, new Boolean(
				((e.getModifiers() & MouseEvent.BUTTON3_DOWN_MASK) ==
				MouseEvent.BUTTON3_DOWN_MASK)));
		}
	}
}