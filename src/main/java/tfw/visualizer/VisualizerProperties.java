package tfw.visualizer;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import tfw.awt.ecd.ColorECD;
import tfw.demo.ColorButtonNB;
import tfw.swing.JPanelBB;
import tfw.tsm.ecd.BooleanECD;

public class VisualizerProperties extends JPanelBB
{
	public static final ColorECD BACKGROUND_COLOR_ECD =
		new ColorECD("backgroundColor");
	public static final BooleanECD BACKGROUND_COLOR_ENABLED_ECD =
		new BooleanECD("backgroundColorEnable");
	public static final ColorECD BRANCH_COLOR_ECD =
		new ColorECD("branchColor");
	public static final BooleanECD BRANCH_COLOR_ENABLED_ECD =
		new BooleanECD("branchColorEnable");
	public static final ColorECD COMMIT_COLOR_ECD =
		new ColorECD("commitColor");
	public static final BooleanECD COMMIT_COLOR_ENABLED_ECD =
		new BooleanECD("commitColorEnable");
	public static final ColorECD CONVERTER_COLOR_ECD =
		new ColorECD("converterColor");
	public static final BooleanECD CONVERTER_COLOR_ENABLED_ECD =
		new BooleanECD("converterColorEnable");
	public static final ColorECD EVENTCHANNEL_COLOR_ECD =
		new ColorECD("eventChannelColor");
	public static final BooleanECD EVENTCHANNEL_COLOR_ENABLED_ECD =
		new BooleanECD("eventChannelColorEnable");
	public static final ColorECD INITIATOR_COLOR_ECD =
		new ColorECD("initiatorColor");
	public static final BooleanECD INITIATOR_COLOR_ENABLED_ECD =
		new BooleanECD("initiatorColorEnable");
	public static final ColorECD MULTIPLEXEDBRANCH_COLOR_ECD =
		new ColorECD("multiplexedbranchColor");
	public static final BooleanECD MULTIPLEXEDBRANCH_COLOR_ENABLED_ECD =
		new BooleanECD("multiplexedbranchColorEnable");
	public static final ColorECD ROOT_COLOR_ECD =
		new ColorECD("rootColor");
	public static final BooleanECD ROOT_COLOR_ENABLED_ECD =
		new BooleanECD("rootColorEnable");
	public static final ColorECD SYNCHRONIZER_COLOR_ECD =
		new ColorECD("synchronizerColor");
	public static final BooleanECD SYNCHRONIZER_COLOR_ENABLED_ECD =
		new BooleanECD("synchronizerColorEnable");
	public static final ColorECD TRIGGEREDCOMMIT_COLOR_ECD =
		new ColorECD("triggeredcommitColor");
	public static final BooleanECD TRIGGEREDCOMMIT_COLOR_ENABLED_ECD =
		new BooleanECD("triggeredcommitColorEnable");
	public static final ColorECD TRIGGEREDCONVERTER_COLOR_ECD =
		new ColorECD("triggeredconverterColor");
	public static final BooleanECD TRIGGEREDCONVERTER_COLOR_ENABLED_ECD =
		new BooleanECD("triggeredconverterColorEnable");
	public static final ColorECD VALIDATOR_COLOR_ECD =
		new ColorECD("validatorColor");
	public static final BooleanECD VALIDATOR_COLOR_ENABLED_ECD =
		new BooleanECD("validatorColorEnable");
	
	public VisualizerProperties()
	{
		super("VisualizerProperties");
		
		JPanelBB labelPanel = new JPanelBB("Label");
		labelPanel.setLayout(new GridLayout(12, 1, 3, 3));
		labelPanel.setBorder(new EmptyBorder(4, 4, 4, 2));
		JLabel backgroundColorL = new JLabel("Background Color:");
		backgroundColorL.setHorizontalAlignment(JLabel.TRAILING);
		labelPanel.add(backgroundColorL);
		JLabel branchColorL = new JLabel("Branch Color:");
		branchColorL.setHorizontalAlignment(JLabel.TRAILING);
		labelPanel.add(branchColorL);
		JLabel commitColorL = new JLabel("Commit Color:");
		commitColorL.setHorizontalAlignment(JLabel.TRAILING);
		labelPanel.add(commitColorL);
		JLabel converterColorL = new JLabel("Converter Color:");
		converterColorL.setHorizontalAlignment(JLabel.TRAILING);
		labelPanel.add(converterColorL);
		JLabel eventChannelColorL = new JLabel("Event Channel Color:");
		eventChannelColorL.setHorizontalAlignment(JLabel.TRAILING);
		labelPanel.add(eventChannelColorL);
		JLabel initiatorColorL = new JLabel("Initiator Color:");
		initiatorColorL.setHorizontalAlignment(JLabel.TRAILING);
		labelPanel.add(initiatorColorL);
		JLabel multiplexedBranchColorL = new JLabel("MultiplexedBranch Color:");
		multiplexedBranchColorL.setHorizontalAlignment(JLabel.TRAILING);
		labelPanel.add(multiplexedBranchColorL);
		JLabel rootColorL = new JLabel("Root Color:");
		rootColorL.setHorizontalAlignment(JLabel.TRAILING);
		labelPanel.add(rootColorL);
		JLabel synchronizerColorL = new JLabel("Synchronizer Color:");
		synchronizerColorL.setHorizontalAlignment(JLabel.TRAILING);
		labelPanel.add(synchronizerColorL);
		JLabel triggeredCommitColorL = new JLabel("TriggeredCommit Color:");
		triggeredCommitColorL.setHorizontalAlignment(JLabel.TRAILING);
		labelPanel.add(triggeredCommitColorL);
		JLabel triggeredConverterColorL = new JLabel("TriggeredConverter Color:");
		triggeredConverterColorL.setHorizontalAlignment(JLabel.TRAILING);
		labelPanel.add(triggeredConverterColorL);
		JLabel validatorColorL = new JLabel("Validator Color:");
		validatorColorL.setHorizontalAlignment(JLabel.TRAILING);
		labelPanel.add(validatorColorL);
		
		JPanelBB colorButtonsPanel = new JPanelBB("ColorButtons");
		colorButtonsPanel.setLayout(new GridLayout(12, 1, 3, 3));
		colorButtonsPanel.setBorder(new EmptyBorder(4, 2, 4, 4));
		ColorButtonNB backgroundCB = new ColorButtonNB("Background", BACKGROUND_COLOR_ECD,
			BACKGROUND_COLOR_ENABLED_ECD, "Background Color", this);
		colorButtonsPanel.addToBoth(backgroundCB);
		ColorButtonNB branchCB = new ColorButtonNB("Branch", BRANCH_COLOR_ECD,
			BRANCH_COLOR_ENABLED_ECD, "Branch Color", this);
		colorButtonsPanel.addToBoth(branchCB);
		ColorButtonNB commitCB = new ColorButtonNB("Commit", COMMIT_COLOR_ECD,
			COMMIT_COLOR_ENABLED_ECD, "Commit Color", this);
		colorButtonsPanel.addToBoth(commitCB);
		ColorButtonNB converterCB = new ColorButtonNB("Converter", CONVERTER_COLOR_ECD,
			CONVERTER_COLOR_ENABLED_ECD, "Converter Color", this);
		colorButtonsPanel.addToBoth(converterCB);
		ColorButtonNB eventChannelCB = new ColorButtonNB("EventChannel", EVENTCHANNEL_COLOR_ECD,
			EVENTCHANNEL_COLOR_ENABLED_ECD, "EventChannel Color", this);
		colorButtonsPanel.addToBoth(eventChannelCB);
		ColorButtonNB initiatorCB = new ColorButtonNB("Initiator", INITIATOR_COLOR_ECD,
			INITIATOR_COLOR_ENABLED_ECD, "Initiator Color", this);
		colorButtonsPanel.addToBoth(initiatorCB);
		ColorButtonNB multiplexedBranchCB = new ColorButtonNB("MultiplexedBranch", MULTIPLEXEDBRANCH_COLOR_ECD,
			MULTIPLEXEDBRANCH_COLOR_ENABLED_ECD, "MultiplexedBranch Color", this);
		colorButtonsPanel.addToBoth(multiplexedBranchCB);
		ColorButtonNB rootCB = new ColorButtonNB("Root", ROOT_COLOR_ECD,
			ROOT_COLOR_ENABLED_ECD, "Root Color", this);
		colorButtonsPanel.addToBoth(rootCB);
		ColorButtonNB synchronizerCB = new ColorButtonNB("Synchronizer", SYNCHRONIZER_COLOR_ECD,
			SYNCHRONIZER_COLOR_ENABLED_ECD, "Synchronizer Color", this);
		colorButtonsPanel.addToBoth(synchronizerCB);
		ColorButtonNB triggeredCommitCB = new ColorButtonNB("TriggeredCommit", TRIGGEREDCOMMIT_COLOR_ECD,
			TRIGGEREDCOMMIT_COLOR_ENABLED_ECD, "TriggeredCommit Color", this);
		colorButtonsPanel.addToBoth(triggeredCommitCB);
		ColorButtonNB triggeredConverterCB = new ColorButtonNB("TriggeredConverter", TRIGGEREDCONVERTER_COLOR_ECD,
			TRIGGEREDCONVERTER_COLOR_ENABLED_ECD, "TriggeredConverter Color", this);
		colorButtonsPanel.addToBoth(triggeredConverterCB);
		ColorButtonNB validatorCB = new ColorButtonNB("Validator", VALIDATOR_COLOR_ECD,
			VALIDATOR_COLOR_ENABLED_ECD, "Validator Color", this);
		colorButtonsPanel.addToBoth(validatorCB);
		
		setLayout(new BorderLayout());
		addToBoth(labelPanel, BorderLayout.WEST);
		addToBoth(colorButtonsPanel, BorderLayout.CENTER);
	}
}