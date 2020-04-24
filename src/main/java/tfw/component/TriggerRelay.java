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
package tfw.component;

import tfw.tsm.Converter;
import tfw.tsm.ecd.StatelessTriggerECD;

/**
 * A component which which given a trigger event on one event channel causes a
 * trigger event on another event channel.
 */
public class TriggerRelay extends Converter
{
    private final StatelessTriggerECD outputTriggerECD;

    /**
     * Creates a trigger relay component.
     * 
     * @param name
     *            The name for this component.
     * @param inputTriggerECD
     *            The trigger which when it fires causes the
     *            <tt>outputTriggerECD</tt> to be fired.
     * @param outputTriggerECD
     *            The trigger to be fired when the <tt>inputTriggerECD</tt>
     *            fires.
     */
    public TriggerRelay(String name, StatelessTriggerECD inputTriggerECD,
            StatelessTriggerECD outputTriggerECD)
    {
        super(name, new StatelessTriggerECD[] { inputTriggerECD },
                new StatelessTriggerECD[] { outputTriggerECD });
        this.outputTriggerECD = outputTriggerECD;
    }

    protected void convert()
    {
        trigger(outputTriggerECD);
    }
}
