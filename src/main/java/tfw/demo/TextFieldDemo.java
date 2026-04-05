package tfw.demo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import tfw.component.TriggeredEventChannelCopy;
import tfw.swing.JButtonBB;
import tfw.swing.JPanelBB;
import tfw.swing.JTextFieldModifiableBB;
import tfw.tsm.AWTTransactionQueue;
import tfw.tsm.Root;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

public class TextFieldDemo extends JPanelBB {
    private static final long serialVersionUID = 1L;

    public TextFieldDemo() {
        super(Root.builder()
                .setName("TextFieldDemo")
                .setTransactionQueue(new AWTTransactionQueue())
                .addEventChannels(TextFieldDemoEnum.APPLY_ENABLE)
                .build());
        setLayout(new BorderLayout());
        addToBoth(createColorPanel(), BorderLayout.EAST);
    }

    private JPanelBB createColorPanel() {
        JPanelBB labelPanel = new JPanelBB("LabelPanel");
        labelPanel.setLayout(new GridLayout(3, 1));
        labelPanel.add(new JLabel("Red: ", JLabel.RIGHT));
        labelPanel.add(new JLabel("Green: ", JLabel.RIGHT));
        labelPanel.add(new JLabel("Blue: ", JLabel.RIGHT));

        JPanelBB textFieldPanel = new JPanelBB("TextFieldPanel");
        textFieldPanel.setLayout(new GridLayout(3, 1));
        textFieldPanel.addToBoth(createTextField(
                "RedTextField",
                TextFieldDemoEnum.RED_STRING.ecd,
                TextFieldDemoEnum.RED_STRING_ADJ.ecd,
                TextFieldDemoEnum.RED_INTEGER.ecd));
        textFieldPanel.addToBoth(createTextField(
                "GreenTextField",
                TextFieldDemoEnum.GREEN_STRING.ecd,
                TextFieldDemoEnum.GREEN_STRING_ADJ.ecd,
                TextFieldDemoEnum.GREEN_INTEGER.ecd));
        textFieldPanel.addToBoth(createTextField(
                "BlueTextField",
                TextFieldDemoEnum.BLUE_STRING.ecd,
                TextFieldDemoEnum.BLUE_STRING_ADJ.ecd,
                TextFieldDemoEnum.BLUE_INTEGER.ecd));

        JPanelBB colorButtonPanel = new JPanelBB("ColorButtonPanel");
        colorButtonPanel.setLayout(new FlowLayout());
        colorButtonPanel.addToBoth(new ColorButtonNB(
                "TextFieldDemo",
                TextFieldDemoEnum.COLOR_NAME.ecd,
                TextFieldDemoEnum.COLOR_BUTTON_ENABLE_NAME.ecd,
                "Color Chooser",
                colorButtonPanel));

        JPanelBB northPanel = new JPanelBB("NorthPanel");
        northPanel.setLayout(new BorderLayout());
        northPanel.addToBoth(labelPanel, BorderLayout.WEST);
        northPanel.addToBoth(textFieldPanel, BorderLayout.CENTER);
        northPanel.addToBoth(colorButtonPanel, BorderLayout.SOUTH);

        ObjectECD[] colorText = new ObjectECD[] {
            TextFieldDemoEnum.RED_STRING.ecd, TextFieldDemoEnum.BLUE_STRING.ecd, TextFieldDemoEnum.GREEN_STRING.ecd
        };
        ObjectECD[] colorTextAdj = new ObjectECD[] {
            TextFieldDemoEnum.RED_STRING_ADJ.ecd,
            TextFieldDemoEnum.BLUE_STRING_ADJ.ecd,
            TextFieldDemoEnum.GREEN_STRING_ADJ.ecd
        };
        JButtonBB applyButton = JButtonBB.builder()
                .setName("Apply")
                .setEnabledInput(TextFieldDemoEnum.APPLY_ENABLE.ecd)
                .setActionOutput(TextFieldDemoEnum.APPLY_TRIGGER.ecd)
                .build();
        applyButton.setText("Apply");
        applyButton.getBranch().add(new ButtonEnableHandler("Apply", colorText, colorTextAdj, applyButton));

        JButtonBB resetButton = JButtonBB.builder()
                .setName("Reset")
                .setEnabledInput(TextFieldDemoEnum.RESET_ENABLE.ecd)
                .setActionOutput(TextFieldDemoEnum.RESET_TRIGGER.ecd)
                .build();
        resetButton.setText("Reset");
        resetButton.getBranch().add(new ButtonEnableHandler("ResetButton", colorText, colorTextAdj, resetButton));

        JPanelBB buttonPanel = new JPanelBB("ButtonPanel");
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.addToBoth(applyButton);
        buttonPanel.addToBoth(resetButton);

        JPanelBB colorPanel = new JPanelBB("ColorPanel");
        colorPanel.setLayout(new BorderLayout());
        colorPanel.addToBoth(northPanel, BorderLayout.NORTH);
        colorPanel.addToBoth(buttonPanel, BorderLayout.SOUTH);
        colorPanel.getBranch().add(new ErrorDialog(colorPanel, TextFieldDemoEnum.ERROR_NAME.ecd));
        colorPanel
                .getBranch()
                .add(new IntegerColorConverter(
                        "ColorDemo",
                        TextFieldDemoEnum.RED_INTEGER.ecd,
                        TextFieldDemoEnum.GREEN_INTEGER.ecd,
                        TextFieldDemoEnum.BLUE_INTEGER.ecd,
                        TextFieldDemoEnum.COLOR_NAME.ecd));

        return colorPanel;
    }

    private JTextFieldModifiableBB createTextField(String name, StringECD text, StringECD textAdj, IntegerECD integer) {
        JTextFieldModifiableBB textField = new JTextFieldModifiableBB(
                name,
                text,
                textAdj,
                TextFieldDemoEnum.COLOR_BUTTON_ENABLE_NAME.ecd,
                TextFieldDemoEnum.APPLY_TRIGGER.ecd);
        textField
                .getBranch()
                .add(new TriggeredEventChannelCopy(
                        "Apply[" + name + "]", TextFieldDemoEnum.APPLY_TRIGGER.ecd, textAdj, text));
        textField
                .getBranch()
                .add(new TriggeredEventChannelCopy(
                        "Reset[" + name + "]", TextFieldDemoEnum.RESET_TRIGGER.ecd, text, textAdj));
        textField.getBranch().add(new IntegerStringConverter(name, text, integer, TextFieldDemoEnum.ERROR_NAME.ecd));

        return textField;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TextFieldDemo");
        TextFieldDemo tfd = new TextFieldDemo();
        frame.getContentPane().add(tfd, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
