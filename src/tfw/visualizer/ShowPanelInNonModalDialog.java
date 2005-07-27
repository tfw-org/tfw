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
package tfw.visualizer;

import java.awt.EventQueue;
import java.awt.Frame;

import tfw.awt.event.WindowInitiator;
import tfw.swing.JDialogBB;
import tfw.swing.JPanelBB;
import tfw.tsm.Branch;
import tfw.tsm.BranchFactory;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.StatelessTriggerECD;

public class ShowPanelInNonModalDialog extends Branch
{
	private static final StatelessTriggerECD WINDOW_CLOSING_ECD =
		new StatelessTriggerECD("SPINMD_windowClosingTrigger");
	
	private JDialogBB dialog = null;
	
	public ShowPanelInNonModalDialog(StatelessTriggerECD triggerECD,
		Frame owner, String title, Class jPanelBBClass)
	{
		super("ShowPanelInNonModalDialog");
		
		add(new MyConverter(triggerECD, owner, title, jPanelBBClass));
	}
	
	private class MyCommit extends TriggeredCommit
	{
		public MyCommit(StatelessTriggerECD triggerECD)
		{
			super("ShowPanelInMocalDialogCommit",
				triggerECD,
				null,
				null);
		}
		
		protected void commit()
		{
System.out.println("Putting remove runnable on AWT queue");
			EventQueue.invokeLater(new Runnable()
			{
				public void run()
				{
System.out.println("dialog == "+dialog);
					if (dialog != null)
					{
System.out.println("Removed and disposed");

						remove(dialog.getBranch());
						dialog.dispose();
						
						dialog = null;
					}
				}
			});
		}
	}
	
	private class MyConverter extends TriggeredConverter
	{
		private final Frame owner;
		private final String title;
		private final Class jPanelBBClass;
		
		public MyConverter(StatelessTriggerECD triggerECD, Frame owner,
			String title, Class jPanelBBClass)
		{
			super("ShowPanelInNonModalDialogConverter",
				triggerECD,
				null,
				null);
			
			this.owner = owner;
			this.title = title;
			this.jPanelBBClass = jPanelBBClass;
		}
		
		protected void convert()
		{
			EventQueue.invokeLater(new Runnable()
			{
				public void run()
				{
					if (dialog == null)
					{
System.out.println("Creating dialog");
						JPanelBB contentPane;
						try
						{
							contentPane = (JPanelBB)jPanelBBClass.newInstance();
						}
						catch (Exception e)
						{
							throw new RuntimeException(
								"Cannot create JPanelBB class", e);
						}

						BranchFactory branchFactory = new BranchFactory();
						branchFactory.addTerminator(WINDOW_CLOSING_ECD);
						Branch branch = branchFactory.create(
							"JDialogBB["+contentPane.getBranch().getName()+"]");
						
						dialog = new JDialogBB(branch, owner, title, false);
						
						dialog.addWindowListenerToBoth(new WindowInitiator(
							"ShowPanelInNonModalDialogWI", null, null,
							WINDOW_CLOSING_ECD, null, null, null, null));
						dialog.getBranch().add(new MyCommit(WINDOW_CLOSING_ECD));
						dialog.setContentPaneForBoth(contentPane);
						
						add(dialog.getBranch());
						
						dialog.pack();
						dialog.setLocationRelativeTo(owner);
						dialog.setVisible(true);
					}
				}
			});
		}
	}
}