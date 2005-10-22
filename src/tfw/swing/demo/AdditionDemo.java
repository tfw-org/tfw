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
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import tfw.awt.event.ActionInitiator;
import tfw.swing.JButtonBB;
import tfw.swing.JFrameBB;
import tfw.swing.JLabelBB;
import tfw.swing.JPanelBB;
import tfw.swing.JTextFieldBB;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Converter;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.Synchronizer;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.StringRollbackECD;

public class AdditionDemo
{
	private AdditionDemo() {}
	
	public static void main(String[] args)
	{
		final BooleanECD CALCULATE_ENABLED_ECD =
			new BooleanECD("calculateEnabled");
		final StatelessTriggerECD CALCULATE_TRIGGER_ECD =
			new StatelessTriggerECD("calculateTrigger");
		final StringRollbackECD ERROR_TEXT_ECD =
			new StringRollbackECD("errorText");
		final IntegerECD SUM_ECD =
			new IntegerECD("sum");
		final BooleanECD SUM_ENABLED_ECD =
			new BooleanECD("sumEnabled");
		final StringECD SUM_LABEL_ECD =
			new StringECD("sumLabel");
		final StringECD SUM_TEXT_ECD =
			new StringECD("sumText");
		final IntegerECD VALUE_ONE_ECD =
			new IntegerECD("valueOne");
		final BooleanECD VALUE_ONE_ENABLED_ECD =
			new BooleanECD("valueOneEnabled");
		final StringECD VALUE_ONE_LABEL_ECD =
			new StringECD("valueOneLabel");
		final StringECD VALUE_ONE_TEXT_ECD =
			new StringECD("valueOneText");
		final IntegerECD VALUE_TWO_ECD =
			new IntegerECD("valueTwo");
		final BooleanECD VALUE_TWO_ENABLED_ECD =
			new BooleanECD("valueTwoEnabled");
		final StringECD VALUE_TWO_LABEL_ECD =
			new StringECD("valueTwoLabel");
		final StringECD VALUE_TWO_TEXT_ECD =
			new StringECD("valueTwoText");
		
		RootFactory rootFactory = new RootFactory();
		rootFactory.addEventChannel(CALCULATE_ENABLED_ECD, Boolean.TRUE);
		rootFactory.addEventChannel(CALCULATE_TRIGGER_ECD);
		rootFactory.addEventChannel(ERROR_TEXT_ECD);
		rootFactory.addEventChannel(SUM_ECD);
		rootFactory.addEventChannel(SUM_ENABLED_ECD, Boolean.FALSE);
		rootFactory.addEventChannel(SUM_LABEL_ECD, "Sum:");
		rootFactory.addEventChannel(SUM_TEXT_ECD, "");
		rootFactory.addEventChannel(VALUE_ONE_ECD);
		rootFactory.addEventChannel(VALUE_ONE_ENABLED_ECD, Boolean.TRUE);
		rootFactory.addEventChannel(VALUE_ONE_LABEL_ECD, "Value One:");
		rootFactory.addEventChannel(VALUE_ONE_TEXT_ECD, "");
		rootFactory.addEventChannel(VALUE_TWO_ECD);
		rootFactory.addEventChannel(VALUE_TWO_ENABLED_ECD, Boolean.TRUE);
		rootFactory.addEventChannel(VALUE_TWO_LABEL_ECD, "Value Two:");
		rootFactory.addEventChannel(VALUE_TWO_TEXT_ECD, "");
		Root root = rootFactory.create("AdditionDemo",
			new BasicTransactionQueue());
		
		JFrameBB frame = new JFrameBB(root);
		
		JLabelBB valueOneL = new JLabelBB("valueOne", VALUE_ONE_LABEL_ECD);
		valueOneL.setHorizontalAlignment(JLabelBB.RIGHT);
		JLabelBB valueTwoL = new JLabelBB("valueTwo", VALUE_TWO_LABEL_ECD);
		valueTwoL.setHorizontalAlignment(JLabelBB.RIGHT);
		JLabelBB sumL = new JLabelBB("sum", SUM_LABEL_ECD);
		sumL.setHorizontalAlignment(JLabelBB.RIGHT);
		
		JPanelBB labelPanel = new JPanelBB("label");
		labelPanel.setLayout(new GridLayout(3, 1));
		labelPanel.addToBoth(valueOneL);
		labelPanel.addToBoth(valueTwoL);
		labelPanel.addToBoth(sumL);
		
		JTextFieldBB valueOneTF = new JTextFieldBB("valueOne",
			VALUE_ONE_TEXT_ECD, VALUE_ONE_ENABLED_ECD);
		valueOneTF.addActionListenerToBoth(
			new ActionInitiator("valueOne", CALCULATE_TRIGGER_ECD));
		valueOneTF.setColumns(10);
		JTextFieldBB valueTwoTF = new JTextFieldBB("valueTwo",
			VALUE_TWO_TEXT_ECD, VALUE_TWO_ENABLED_ECD);
		valueTwoTF.addActionListenerToBoth(
			new ActionInitiator("valueTwo", CALCULATE_TRIGGER_ECD));
		valueTwoTF.setColumns(10);
		JTextFieldBB sumTF = new JTextFieldBB("sum",
			SUM_TEXT_ECD, SUM_ENABLED_ECD);
		sumTF.setColumns(10);
		
		JPanelBB textFieldPanel = new JPanelBB("textField");
		textFieldPanel.setLayout(new GridLayout(3, 1));
		textFieldPanel.addToBoth(valueOneTF);
		textFieldPanel.addToBoth(valueTwoTF);
		textFieldPanel.addToBoth(sumTF);
		
		JPanelBB centerPanel = new JPanelBB("center");
		centerPanel.setLayout(new BorderLayout());
		centerPanel.addToBoth(labelPanel, BorderLayout.WEST);
		centerPanel.addToBoth(textFieldPanel, BorderLayout.EAST);
		
		JButtonBB calculateB = new JButtonBB("calculate",
			CALCULATE_ENABLED_ECD, CALCULATE_TRIGGER_ECD);
		calculateB.setText("Calculate");
		
		JPanelBB southPanel = new JPanelBB("south");
		southPanel.addToBoth(calculateB);
		
		JPanelBB contentPane = new JPanelBB("contentPane");
		contentPane.setLayout(new BorderLayout());
		contentPane.addToBoth(centerPanel, BorderLayout.CENTER);
		contentPane.addToBoth(southPanel, BorderLayout.SOUTH);
		
		root.add(new IntegerSynchronizer(
			"sum", SUM_ECD, SUM_TEXT_ECD, ERROR_TEXT_ECD));
		root.add(new IntegerSynchronizer(
			"valueOne", VALUE_ONE_ECD, VALUE_ONE_TEXT_ECD, ERROR_TEXT_ECD));
		root.add(new IntegerSynchronizer(
			"valueTwo", VALUE_TWO_ECD, VALUE_TWO_TEXT_ECD, ERROR_TEXT_ECD));
		root.add(new AdditionConverter(CALCULATE_TRIGGER_ECD,
			VALUE_ONE_ECD, VALUE_TWO_ECD, SUM_ECD));
		root.add(new ErrorMessageConverter(frame, ERROR_TEXT_ECD));
		
		frame.setContentPaneForBoth(contentPane);
		frame.setTitle("AdditionDemo");
		frame.setDefaultCloseOperation(JFrameBB.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	private static class IntegerSynchronizer extends Synchronizer
	{
		private final String name;
		private final IntegerECD integerECD;
		private final StringECD textECD;
		private final StringRollbackECD errorECD;
		
		public IntegerSynchronizer(String name, IntegerECD integerECD,
			StringECD textECD, StringRollbackECD errorECD)
		{
			super("IntegerSynchronizer["+name+"]",
				new EventChannelDescription[] {integerECD},
				new EventChannelDescription[] {textECD},
				null,
				new EventChannelDescription[] {errorECD});
			
			this.name = name;
			this.integerECD = integerECD;
			this.textECD = textECD;
			this.errorECD = errorECD;
		}
		
		protected void convertAToB()
		{
			int i = ((Integer)get(integerECD)).intValue();
			
			if (i == -Integer.MAX_VALUE - 1)
			{
				set(textECD, "");
			}
			else
			{
				set(textECD, Integer.toString(i));
			}
		}
		
		protected void convertBToA()
		{
			String s = (String)get(textECD);
			
			if (s.length() == 0)
			{
				set(integerECD, new Integer(-Integer.MAX_VALUE - 1));
			}
			else
			{
				try
				{
					set(integerECD, new Integer(Integer.parseInt(s)));
				}
				catch(NumberFormatException nfe)
				{
					this.rollback(errorECD,
						"Bad Value for "+name+" ("+s+")");
				}
			}
		}
	}
	
	private static class AdditionConverter extends TriggeredConverter
	{
		private final IntegerECD valueOneECD;
		private final IntegerECD valueTwoECD;
		private final IntegerECD sumECD;
		
		public AdditionConverter(StatelessTriggerECD triggerECD,
			IntegerECD valueOneECD, IntegerECD valueTwoECD, IntegerECD sumECD)
		{
			super("AdditionConverter",
				triggerECD,
				new EventChannelDescription[] {valueOneECD, valueTwoECD},
				new EventChannelDescription[] {sumECD});
			
			this.valueOneECD = valueOneECD;
			this.valueTwoECD = valueTwoECD;
			this.sumECD = sumECD;
		}
		
		protected void convert()
		{
			int v1 = ((Integer)get(valueOneECD)).intValue();
			int v2 = ((Integer)get(valueTwoECD)).intValue();
			
			set(sumECD, new Integer(v1 + v2));
		}
	}
	
	private static class ErrorMessageConverter extends Converter
	{
		private final Component component;
		private final StringRollbackECD errorMessageECD;
		
		public ErrorMessageConverter(Component component,
			StringRollbackECD errorMessageECD)
		{
			super("ErrorMessageConverter",
				new EventChannelDescription[] {errorMessageECD},
				null,
				null);
			
			this.component = component;
			this.errorMessageECD = errorMessageECD;
		}
		
		protected void convert()
		{
			JOptionPane.showMessageDialog(component, get(errorMessageECD),
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}