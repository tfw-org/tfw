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
package tfw.swing;

import java.awt.Color;

import tfw.awt.event.ActionInitiator;
import tfw.component.EventChannelCopyConverter;
import tfw.tsm.Branch;
import tfw.tsm.Commit;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;

public class JTextFieldModifiableBB extends JTextFieldBB
{
    private final Color defaultDisabledBackground;

    private final Color defaultEnabledBackground;

    private final Color modifiedBackground = new Color(153, 153, 204);

    public JTextFieldModifiableBB(String name, StringECD textECD,
            StringECD textAdjECD, BooleanECD enabledECD,
            StatelessTriggerECD applyECD)
    {
        this(new Branch("JTextFieldModifiableBB[" + name + "]"), textECD,
                textAdjECD, enabledECD, applyECD);
    }

    public JTextFieldModifiableBB(Branch branch, StringECD textECD,
            StringECD textAdjECD, BooleanECD enabledECD,
            StatelessTriggerECD applyECD)
    {
        super(branch, textAdjECD, enabledECD);

        setEnabled(true);
        defaultEnabledBackground = getBackground();
        setEnabled(false);
        defaultDisabledBackground = getBackground();

        ActionInitiator actionInitiator = new ActionInitiator(
                "JTextFieldModifiableBB", applyECD);
        addActionListenerToBoth(actionInitiator);

        branch.add(new ForegroundBackgroundHandler(branch.getName(), textECD,
                textAdjECD, enabledECD));
        branch.add(new EventChannelCopyConverter(branch.getName(), textECD,
                textAdjECD));
    }

    /**
     * Sets the foreground and background state of the text field. Sets the
     * value of the text field if the text event channel is set during the state
     * change cycle.
     */
    private final class ForegroundBackgroundHandler extends Commit
    {
        private final StringECD textName;

        private final StringECD textAdjName;

        private final BooleanECD enableName;

        public ForegroundBackgroundHandler(String name, StringECD textSink,
                StringECD textAdjSink, BooleanECD enableSink)
        {
            super("ForegroundBackgroundHandler[" + name + "]", new ObjectECD[] {
                    textSink, textAdjSink, enableSink });

            this.textName = textSink;
            this.textAdjName = textAdjSink;
            this.enableName = enableSink;
        }

        protected void commit()
        {
            String text = (String) get(textName);
            String textAdj = (String) get(textAdjName);

            boolean enabled = true;
            if (this.enableName != null){
                enabled = ((Boolean) get(enableName)).booleanValue();
            } 
            
            if (!text.equals(textAdj))
            {
                setBackground(modifiedBackground);
            }
            else if (enabled)
            {
                setBackground(defaultEnabledBackground);
                setForeground(Color.black);
            }
            else
            {
                setBackground(defaultDisabledBackground);
                setForeground(Color.black);
            }
        }
    }
}