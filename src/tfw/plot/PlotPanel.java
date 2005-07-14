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
package tfw.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import tfw.awt.graphic.Graphic;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.MultiplexedBranch;
import tfw.tsm.MultiplexedBranchFactory;
import tfw.tsm.TreeComponent;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class PlotPanel extends JPanel implements BranchBox
{
	private static final ObjectIla EMPTY_OBJECT_ILA =
		ObjectIlaFromArray.create(new Object[0]);
	private static final ObjectIlaECD GRAPHIC_ECD =
		new ObjectIlaECD("graphic");
	private static final tfw.tsm.ecd.ObjectIlaECD MULTI_GRAPHIC_ECD =
		new tfw.tsm.ecd.ObjectIlaECD("multiGraphic");
	private static final StatelessTriggerECD GENERATE_GRAPHIC_TRIGGER_ECD =
		new StatelessTriggerECD("generateGraphicTrigger");
	
	private final Branch branch;
	private final MultiplexedBranch multiplexedBranch;
	private final Initiator initiator;
	
	private ObjectIla multiGraphic = null;
	
	public PlotPanel(String name)
	{
		this(new Branch("PlotPanel["+name+"]"));
	}
	public PlotPanel(Branch branch)
	{
		this.branch = branch;

		MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
		mbf.addMultiplexer(GRAPHIC_ECD, MULTI_GRAPHIC_ECD);
		multiplexedBranch = mbf.create("PlotPanelPainters");
		branch.add(multiplexedBranch);
		
		initiator = new Initiator("PlotPanelInitiator",
			new EventChannelDescription[] {GENERATE_GRAPHIC_TRIGGER_ECD,
				MULTI_GRAPHIC_ECD});
		branch.add(initiator);
		
		PlotPanelCommit plotPanelCommit = new PlotPanelCommit(this);
		branch.add(plotPanelCommit);
	}
	
	private final class PlotPanelCommit extends Commit
	{
		private final PlotPanel plotPanel;
		
		public PlotPanelCommit(PlotPanel plotPanel)
		{
			super("PlotPanelCommit",
				new EventChannelDescription[] {MULTI_GRAPHIC_ECD},
				null,
				null);
			
			this.plotPanel = plotPanel;
		}
		
		protected void commit()
		{
System.out.println("Inside PlotPanelCommit");
			synchronized(plotPanel)
			{
				multiGraphic = (ObjectIla)get(MULTI_GRAPHIC_ECD);
System.out.println("NotifyingAll");
				plotPanel.notifyAll();
			}
		}
	}
	
	public final void paint(Graphics graphics)
	{
System.out.println("Graphics class="+graphics.getClass());
		synchronized(this)
		{
			initiator.trigger(GENERATE_GRAPHIC_TRIGGER_ECD);
			try
			{
System.out.println("Before wait");
				wait();
System.out.println("After wait");
				
				Object[] mg = null;
				
				try
				{
					mg = multiGraphic.toArray();
				}
				catch(DataInvalidException die)
				{
					return;
				}
				
				for (int i=0 ; i < mg.length ; i++)
				{
System.out.println("  mg["+i+"]="+mg[i]);
					Graphic g = (Graphic)mg[i];
					g.paint((Graphics2D)graphics);
				}
			}
			catch(InterruptedException ie) {}
		}
	}
	
	public final void addComponentListenerToBoth(ComponentListener listener)
	{
		addComponentListener(listener);
		branch.add((TreeComponent)listener);
	}
	
	public final void removeComponentListenerFromBoth(ComponentListener listener)
	{
		removeComponentListener(listener);
		branch.remove((TreeComponent)listener);
	}
	
	public final void addGraphicProducer(TreeComponent treeComponent,
		int multiplexIndex)
	{
		multiplexedBranch.add(treeComponent, multiplexIndex);
	}
	
	public final void removeGraphicProducer(TreeComponent treeComponent)
	{
		multiplexedBranch.remove(treeComponent);
	}
	
	public final Branch getBranch()
	{
		return(branch);
	}
}