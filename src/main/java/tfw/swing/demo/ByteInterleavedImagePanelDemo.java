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
package tfw.swing.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.IndexColorModel;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tfw.awt.ecd.ColorModelECD;
import tfw.immutable.ilm.byteilm.ByteIlmFill;
import tfw.swing.ByteInterleavedImagePanel;
import tfw.tsm.AWTTransactionQueue;
import tfw.tsm.EventChannelStateBuffer;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ilm.ByteIlmECD;

public class ByteInterleavedImagePanelDemo
{
	public ByteInterleavedImagePanelDemo() {}
	
	public static final void main(String[] args)
	{
		JFrame f = new JFrame();

		final IntegerECD xECD = new IntegerECD("x");
		final IntegerECD yECD = new IntegerECD("y");
		final ByteIlmECD ilmECD = new ByteIlmECD("byteIlm");
		final ColorModelECD cmECD = new ColorModelECD("colorModel");
		
		final Color initialColor = Color.red;
		RootFactory rf = new RootFactory();
		rf.addEventChannel(xECD);
		rf.addEventChannel(yECD);
		rf.addEventChannel(ilmECD);
		rf.addEventChannel(cmECD, new IndexColorModel(8, 2,
				new byte[] {(byte)0, (byte)initialColor.getRed()},
				new byte[] {(byte)0, (byte)initialColor.getGreen()},
				new byte[] {(byte)0, (byte)initialColor.getBlue()}, 0));
		Root root = rf.create("ByteInterleavedImagePanelTest",
			new AWTTransactionQueue());
		
		final Initiator initiator = new Initiator(
			"ByteInterleavedImagePanelTest",
			new ObjectECD[] {xECD, yECD, ilmECD, cmECD});
		root.add(initiator);
		
		final JColorChooser cc = new JColorChooser(initialColor);
		final JDialog d = JColorChooser.createDialog(f, "Pick Color", true, cc,
		new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Color color = cc.getColor();
				initiator.set(cmECD, new IndexColorModel(8, 2,
						new byte[] {(byte)0, (byte)color.getRed()},
						new byte[] {(byte)0, (byte)color.getGreen()},
						new byte[] {(byte)0, (byte)color.getBlue()}, 0));
			}
		}, null);
		
		ByteInterleavedImagePanel biip = new ByteInterleavedImagePanel(
			"Test", xECD, yECD, ilmECD, cmECD);
		root.add(biip);
			
		JButton b = new JButton("Change Color");
		b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				d.setVisible(true);
			}
		});
		
		JPanel lp = new JPanel();
		lp.setLayout(new GridLayout(5, 1));
		lp.add(new JLabel("X:"));
		lp.add(new JLabel("Y:"));
		lp.add(new JLabel("Image Width:"));
		lp.add(new JLabel("Image Height:"));
		
		final JTextField xTF = new JTextField("0");
		final JTextField yTF = new JTextField("0");
		final JTextField wTF = new JTextField("200");
		final JTextField hTF = new JTextField("200");
		
		ActionListener al = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int w = Integer.parseInt(wTF.getText());
					int h = Integer.parseInt(hTF.getText());
					
					EventChannelStateBuffer ecsb =
						new EventChannelStateBuffer();
					ecsb.put(xECD, new Integer(xTF.getText()));
					ecsb.put(yECD, new Integer(yTF.getText()));
					ecsb.put(ilmECD, ByteIlmFill.create((byte)1, w, h));
					
					initiator.set(ecsb.toArray());
				}
				catch(NumberFormatException nfe)
				{
					nfe.printStackTrace();
				}
			}
		};
		
		xTF.addActionListener(al);
		yTF.addActionListener(al);
		wTF.addActionListener(al);
		hTF.addActionListener(al);
		
		JPanel tfp = new JPanel();
		tfp.setLayout(new GridLayout(5, 1));
		tfp.add(xTF);
		tfp.add(yTF);
		tfp.add(wTF);
		tfp.add(hTF);
		tfp.add(b);
		
		JPanel sp = new JPanel();
		sp.setLayout(new BorderLayout());
		sp.add(lp, BorderLayout.WEST);
		sp.add(tfp, BorderLayout.CENTER);
		
		JPanel cp = new JPanel();
		cp.setLayout(new BorderLayout());
		cp.add(biip, BorderLayout.CENTER);
		cp.add(sp, BorderLayout.SOUTH);
		
		f.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		f.setSize(500, 500);
		f.setContentPane(cp);
		f.setVisible(true);
		
		al.actionPerformed(null);
	}
}