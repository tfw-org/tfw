/*
 * The Framework Project
 * Copyright (C) 2006 Anonymous
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
package tfw.swing;

import javax.swing.JSlider;
import tfw.awt.component.EnabledCommit;
import tfw.swing.slider.SetModelCommit;
import tfw.swing.slider.SliderChangeInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.IntegerECD;

public class JSliderBB extends JSlider implements BranchBox
{
	private final Branch branch;
	
	
	public JSliderBB(String name, IntegerECD valueECD, IntegerECD valueAdjECD,
		IntegerECD extentECD, IntegerECD minimumECD, IntegerECD maximumECD,
		BooleanECD enabledECD)
	{
		this(new Branch("JSliderBB["+name+"]"), valueECD, valueAdjECD,
			extentECD, minimumECD, maximumECD, enabledECD);
	}
	
	public JSliderBB(Branch branch, IntegerECD valueECD, IntegerECD valueAdjECD,
		IntegerECD extentECD, IntegerECD minimumECD, IntegerECD maximumECD,
		BooleanECD enabledECD)
	{
		this.branch = branch;
		
		SliderChangeInitiator sliderChangeInitiator = new SliderChangeInitiator(
			branch.getName(), valueECD, valueAdjECD, this);
		branch.add(sliderChangeInitiator);
		addChangeListener(sliderChangeInitiator);
		
		SetModelCommit setModelCommit = new SetModelCommit(branch.getName(),
			valueECD, extentECD, minimumECD, maximumECD,
			new Initiator[] {sliderChangeInitiator}, this);
		branch.add(setModelCommit);
		
		EnabledCommit setEnabledCommit = new EnabledCommit(
			branch.getName(), enabledECD, this, null);
		branch.add(setEnabledCommit);
		
		setEnabled(false);
	}
	
	public final Branch getBranch()
	{
		return(branch);
	}
}