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
package tfw.swing.demo;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tfw.swing.JTextFieldBB;
import tfw.tsm.AWTTransactionQueue;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StringECD;

public class JTextFieldBBDemo
{
	private JTextFieldBBDemo() {}
	
	public static final void main(String[] args)
	{
		JFrame f = new JFrame();
		
		final StringECD textECD = new StringECD("text");
		final BooleanECD enabledECD = new BooleanECD("enabled");
		
		RootFactory rf = new RootFactory();
		rf.addEventChannel(textECD, "Initial Value");
		rf.addEventChannel(enabledECD, Boolean.FALSE);
		Root root = rf.create("JTextFieldBBTest", new AWTTransactionQueue());
		
		final Initiator initiator = new Initiator("JTextFieldBBTest",
				new EventChannelDescription[] {textECD, enabledECD});
			root.add(initiator);

		final JTextField tf = new JTextField();
		tf.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				initiator.set(textECD, tf.getText());
			}
		});
		final JCheckBox cb = new JCheckBox("Enabled");
		cb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				initiator.set(enabledECD, new Boolean(cb.isSelected()));
			}
		});
		
		JTextFieldBB tfb = new JTextFieldBB(
			"JTextFieldBBTest", textECD, enabledECD);
		root.add(tfb);
		root.add(new Commit(
			"JTextFieldBBTestCommit",
			new EventChannelDescription[] {textECD},
			null,
			null)
		{
			protected void commit()
			{
				tf.setText((String)get(textECD));
			}
		});
		
		JPanel lp = new JPanel();
		lp.setLayout(new GridLayout(3, 1));
		lp.add(new JLabel("TextField to Test:"));
		lp.add(new JLabel("value of TextField:"));
		
		JPanel tfp = new JPanel();
		tfp.setLayout(new GridLayout(3, 1));
		tfp.add(tfb);
		tfp.add(tf);
		tfp.add(cb);
		
		JPanel cp = new JPanel();
		cp.setLayout(new BorderLayout());
		cp.add(lp, BorderLayout.WEST);
		cp.add(tfp, BorderLayout.CENTER);
		
		f.setSize(300, 100);
		f.setContentPane(cp);
		f.setVisible(true);
	}
}