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
package tfw.visualizer.prefuse;

import java.awt.Window;
import javax.swing.JDialog;
import tfw.swing.JFrameBB;
import tfw.tsm.TreeComponent;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.ecd.StatelessTriggerECD;

public class BalloonGraphDisplayCommit extends TriggeredCommit
{
	private final String name;
	private final TreeComponent treeComponent;
	private final Window window;
	
	public BalloonGraphDisplayCommit(String name, StatelessTriggerECD triggerECD,
		TreeComponent treeComponent, Window window)
	{
		super("BalloonGraphDisplayCommit["+name+"]",
			triggerECD,
			null,
			null);
		
		if (!(window instanceof JDialog) && !(window instanceof JFrameBB))
			throw new IllegalArgumentException(
				"window != JDialog || JFrame not allowed!");
		
		this.name = name;
		this.treeComponent = treeComponent;
		this.window = window;
	}
	
	protected void commit()
	{
		BalloonGraphDisplay bgd = new BalloonGraphDisplay(name, treeComponent);
		
		if (window instanceof JDialog)
		{
			((JDialog)window).setContentPane(bgd);
		}
		else
		{
			JFrameBB frame = (JFrameBB)window;
			
			bgd.setSize(frame.getContentPane().getSize());
			bgd.setLocation(frame.getContentPane().getLocation());
			frame.setContentPaneForBoth(bgd);
		}
	}
}