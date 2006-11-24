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
package tfw.awt.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import tfw.awt.ecd.ColorECD;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;

public class SetBackgroundCommit extends Commit
{
	private final ColorECD colorECD;
	private final Component component;
	
	public SetBackgroundCommit(String name, ColorECD colorECD,
		Component component, Initiator[] initiators)
	{
		super("SetBackgroundCommit["+name+"]",
			new ObjectECD[] {colorECD},
			null,
			initiators);
		
		this.colorECD = colorECD;
		this.component = component;
	}
	
	protected void commit()
	{
		final Color color = (Color)get(colorECD);
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				component.setBackground(color);
			}
		});
	}
}