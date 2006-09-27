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
package tfw.swing.slider;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import tfw.tsm.EventChannelStateBuffer;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.EventChannelDescriptionUtil;
import tfw.tsm.ecd.IntegerECD;

public class SliderChangeInitiator extends Initiator implements ChangeListener
{
	private final IntegerECD valueECD;
	private final IntegerECD valueAdjECD;
	private final JSlider slider;
	
	public SliderChangeInitiator(String name, IntegerECD valueECD,
		IntegerECD valueAdjECD, JSlider slider)
	{
		super("SliderChangeInitiator["+name+"]",
			EventChannelDescriptionUtil.create(
				new EventChannelDescription[] {valueECD, valueAdjECD}));
		
		this.valueECD = valueECD;
		this.valueAdjECD = valueAdjECD;
		this.slider = slider;
	}
	
	public void stateChanged(ChangeEvent changeEvent)
	{
		EventChannelStateBuffer ecsb = new EventChannelStateBuffer();
		
		if (slider.getValueIsAdjusting())
		{
			if (valueAdjECD != null)
			{
				ecsb.put(valueAdjECD, new Integer(slider.getValue()));
			}
		}
		else
		{
			if (valueECD != null)
			{
				ecsb.put(valueECD, new Integer(slider.getValue()));
			}
		}
		
		set(ecsb.toArray());
	}
}