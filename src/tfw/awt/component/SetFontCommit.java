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

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import tfw.awt.ecd.FontECD;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;

public class SetFontCommit extends Commit
{
	private final FontECD fontECD;
	private final Component component;
	
	public SetFontCommit(String name, FontECD fontECD,
		Component component, Initiator[] initiators)
	{
		super("SetFontCommit["+name+"]",
			new ObjectECD[] {fontECD},
			null,
			initiators);
		
		this.fontECD = fontECD;
		this.component = component;
	}
	
	protected void commit()
	{
		final Font font = (Font)get(fontECD);
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				component.setFont(font);
			}
		});
	}
}