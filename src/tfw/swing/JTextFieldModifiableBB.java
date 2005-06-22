/*
 * The Framework Project Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; witout even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package tfw.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tfw.check.Argument;
import tfw.tsm.Branch;
import tfw.tsm.Commit;
import tfw.tsm.Converter;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;

public class JTextFieldModifiableBB extends JTextFieldBB {

	private final Color defaultDisabledBackground;

	private final Color defaultEnabledBackground;

	private final Color modifiedBackground = new Color(153, 153, 204);

	public JTextFieldModifiableBB(String name, StringECD textECD,
			StringECD textAdjECD, BooleanECD enabledECD,
			StatelessTriggerECD applyECD) {
		this(new Branch(name), textECD, textAdjECD, enabledECD, applyECD);
	}

	public JTextFieldModifiableBB(Branch branch, StringECD textECD,
			StringECD textAdjECD, BooleanECD enabledECD,
			StatelessTriggerECD applyECD) {
		super(branch, textAdjECD, enabledECD);
		Argument.assertNotNull(textAdjECD, "textAdjECD");

		Initiator applyInitiator = new Initiator("ApplyTriggerInitiator["
				+ branch.getName() + "]", applyECD);
		branch.add(applyInitiator);
		branch.add(new ForegroundBackgroundHandler(branch.getName(), textECD,
				textAdjECD, enabledECD));
		branch.add(new TextToTextAdjustingConverter(branch.getName(), textECD,
				textAdjECD));
		setEnabled(true);
		defaultEnabledBackground = getBackground();
		setEnabled(false);
		defaultDisabledBackground = getBackground();

		addActionListener(new ApplyTriggerActionListener(applyInitiator,
				applyECD));
	}

	/**
	 * Sets the foreground and background state of the text field. Sets the
	 * value of the text field if the text event channel is set during the state
	 * change cycle.
	 */
	private final class ForegroundBackgroundHandler extends Commit {
		private final StringECD textName;

		private final StringECD textAdjName;

		private final BooleanECD enableName;

		public ForegroundBackgroundHandler(String name, StringECD textSink,
				StringECD textAdjSink, BooleanECD enableSink) {
			super("ForegroundBackgroundHandler[" + name + "]",
					new EventChannelDescription[] { textSink, textAdjSink,
							enableSink });
			this.textName = textSink;
			this.textAdjName = textAdjSink;
			this.enableName = enableSink;
		}

		protected void commit() {
			String text = (String) get(textName);
			String textAdj = (String) get(textAdjName);

			if (!text.equals(textAdj)) {
				setBackground(modifiedBackground);
			} else if (((Boolean) get(enableName)).booleanValue()) {
				setBackground(defaultEnabledBackground);
				setForeground(Color.black);
			} else {
				setBackground(defaultDisabledBackground);
				setForeground(Color.black);
			}
		}
	}

	/**
	 * This converter reacts to changes in the text event channel. It sends the
	 * value out on the text adjusting event channel.
	 */
	private final class TextToTextAdjustingConverter extends Converter {
		private final StringECD textAdjECD;

		private final StringECD textECD;

		public TextToTextAdjustingConverter(String name, StringECD textECD,
				StringECD textAdjECD) {
			super("TextToTextAdjustingConverter[" + name + "]",
					new EventChannelDescription[] { textECD },
					new EventChannelDescription[] { textAdjECD });
			this.textAdjECD = textAdjECD;
			this.textECD = textECD;
		}

		public void convert() {
			set(textAdjECD, get(textECD));
		}
	}

	private class ApplyTriggerActionListener implements ActionListener {
		private final Initiator initiator;

		private final StatelessTriggerECD applyECD;

		public ApplyTriggerActionListener(Initiator initiator,
				StatelessTriggerECD applyECD) {
			this.applyECD = applyECD;
			this.initiator = initiator;
		}

		public void actionPerformed(ActionEvent event) {
			initiator.trigger(applyECD);
		}
	}
}
