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
package tfw.swing.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import tfw.swing.JLabelBB;
import tfw.tsm.AWTTransactionQueue;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;

public class JLabelBBTest
{
	private static final StringECD TEXT_ECD =
		new StringECD("text");
	
	private static long buttonPresses = 0;
	
	private JLabelBBTest() {}
	
	public static final void main(String[] args)
	{
		final Initiator initiator = new Initiator("JButtonBBTest",
			new EventChannelDescription[] {TEXT_ECD});
		
		JLabelBB l = new JLabelBB("JButtonBBTest", TEXT_ECD);
		l.setText("Set Label Value");
		final JTextField tf = new JTextField(16);
		JButton b = new JButton("Set Label");
		
		ActionListener al = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				initiator.set(TEXT_ECD, tf.getText());
			}
		};
		tf.addActionListener(al);
		b.addActionListener(al);
		
		JPanel c = new JPanel();
		c.setLayout(new BorderLayout());
		c.add(tf, BorderLayout.CENTER);
		c.add(b, BorderLayout.EAST);
		
		final JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(l, BorderLayout.NORTH);
		p.add(c, BorderLayout.CENTER);
		
		RootFactory rf = new RootFactory();
		rf.addTerminator(TEXT_ECD);
		Root r = rf.create("JButtonBBTest", new AWTTransactionQueue());
		r.add(initiator);
		r.add(l);
		
		final JFrame f = new JFrame();
		f.getContentPane().add(p, BorderLayout.CENTER);
		f.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		f.pack();
		f.setVisible(true);
		
		initiator.set(TEXT_ECD, "Initial Label Value");
	}
}