package tfw.swing.demo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import tfw.component.TriggeredEventChannelCopy;
import tfw.swing.JButtonBB;
import tfw.swing.JFrameBB;
import tfw.swing.JPanelBB;
import tfw.swing.JTextFieldBB;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Commit;
import tfw.tsm.Converter;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.Synchronizer;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.StringRollbackECD;

public class AdditionDemo {
    private AdditionDemo() {}

    public static void main(String[] args) {
        final BooleanECD CALCULATE_ENABLED_ECD = new BooleanECD("calculateEnabled");
        final StatelessTriggerECD CALCULATE_TRIGGER_ECD = new StatelessTriggerECD("calculateTrigger");
        final StringRollbackECD ERROR_TEXT_ECD = new StringRollbackECD("errorText");
        final IntegerECD SUM_ECD = new IntegerECD("sum");
        final BooleanECD SUM_ENABLED_ECD = new BooleanECD("sumEnabled");
        final StringECD SUM_TEXT_ECD = new StringECD("sumText");
        final IntegerECD VALUE_ONE_ECD = new IntegerECD("valueOne");
        final BooleanECD VALUE_ONE_ENABLED_ECD = new BooleanECD("valueOneEnabled");
        final StringECD VALUE_ONE_TEXT_ECD = new StringECD("valueOneText");
        final StringECD VALUE_ONE_TEXT_ADJ_ECD = new StringECD("valueOneTextAdj");
        final IntegerECD VALUE_TWO_ECD = new IntegerECD("valueTwo");
        final BooleanECD VALUE_TWO_ENABLED_ECD = new BooleanECD("valueTwoEnabled");
        final StringECD VALUE_TWO_TEXT_ECD = new StringECD("valueTwoText");
        final StringECD VALUE_TWO_TEXT_ADJ_ECD = new StringECD("valueTwoTextAdj");

        RootFactory rootFactory = new RootFactory();
        rootFactory.addEventChannel(CALCULATE_ENABLED_ECD, Boolean.TRUE);
        rootFactory.addEventChannel(CALCULATE_TRIGGER_ECD);
        rootFactory.addEventChannel(ERROR_TEXT_ECD);
        rootFactory.addEventChannel(SUM_ECD);
        rootFactory.addEventChannel(SUM_ENABLED_ECD, Boolean.FALSE);
        rootFactory.addEventChannel(SUM_TEXT_ECD, "");
        rootFactory.addEventChannel(VALUE_ONE_ECD);
        rootFactory.addEventChannel(VALUE_ONE_ENABLED_ECD, Boolean.TRUE);
        rootFactory.addEventChannel(VALUE_ONE_TEXT_ECD, "");
        rootFactory.addEventChannel(VALUE_ONE_TEXT_ADJ_ECD, "");
        rootFactory.addEventChannel(VALUE_TWO_ECD);
        rootFactory.addEventChannel(VALUE_TWO_ENABLED_ECD, Boolean.TRUE);
        rootFactory.addEventChannel(VALUE_TWO_TEXT_ECD, "");
        rootFactory.addEventChannel(VALUE_TWO_TEXT_ADJ_ECD, "");
        rootFactory.setLogging(true);
        Root root = rootFactory.create("AdditionDemo", new BasicTransactionQueue());

        JFrameBB frame = new JFrameBB(root);

        JLabel valueOneL = new JLabel("Value One:");
        valueOneL.setHorizontalAlignment(JLabel.RIGHT);
        JLabel valueTwoL = new JLabel("Value Two:");
        valueTwoL.setHorizontalAlignment(JLabel.RIGHT);
        JLabel sumL = new JLabel("Sum:");
        sumL.setHorizontalAlignment(JLabel.RIGHT);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(3, 1));
        labelPanel.add(valueOneL);
        labelPanel.add(valueTwoL);
        labelPanel.add(sumL);

        JTextFieldBB valueOneTF = new JTextFieldBB("valueOne", VALUE_ONE_TEXT_ADJ_ECD, VALUE_ONE_ENABLED_ECD);
        valueOneTF.setColumns(10);
        JTextFieldBB valueTwoTF = new JTextFieldBB("valueTwo", VALUE_TWO_TEXT_ADJ_ECD, VALUE_TWO_ENABLED_ECD);
        valueTwoTF.setColumns(10);
        JTextFieldBB sumTF = new JTextFieldBB("sum", SUM_TEXT_ECD, SUM_ENABLED_ECD);
        sumTF.setColumns(10);

        JPanelBB textFieldPanel = new JPanelBB("textField");
        textFieldPanel.setLayout(new GridLayout(3, 1));
        textFieldPanel.addToBoth(valueOneTF);
        textFieldPanel.addToBoth(valueTwoTF);
        textFieldPanel.addToBoth(sumTF);

        JPanelBB centerPanel = new JPanelBB("center");
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(labelPanel, BorderLayout.WEST);
        centerPanel.addToBoth(textFieldPanel, BorderLayout.EAST);

        JButtonBB calculateB = new JButtonBB("calculate", CALCULATE_ENABLED_ECD, CALCULATE_TRIGGER_ECD);
        calculateB.setText("Calculate");

        JPanelBB southPanel = new JPanelBB("south");
        southPanel.addToBoth(calculateB);

        JPanelBB contentPane = new JPanelBB("contentPane");
        contentPane.setLayout(new BorderLayout());
        contentPane.addToBoth(centerPanel, BorderLayout.CENTER);
        contentPane.addToBoth(southPanel, BorderLayout.SOUTH);

        root.add(new TriggeredEventChannelCopy(
                "ValueOne", CALCULATE_TRIGGER_ECD, VALUE_ONE_TEXT_ADJ_ECD, VALUE_ONE_TEXT_ECD));
        root.add(new TriggeredEventChannelCopy(
                "ValueTwo", CALCULATE_TRIGGER_ECD, VALUE_TWO_TEXT_ADJ_ECD, VALUE_TWO_TEXT_ECD));
        root.add(new IntegerSynchronizer("sum", SUM_ECD, SUM_TEXT_ECD, ERROR_TEXT_ECD));
        root.add(new IntegerSynchronizer("valueOne", VALUE_ONE_ECD, VALUE_ONE_TEXT_ECD, ERROR_TEXT_ECD));
        root.add(new IntegerSynchronizer("valueTwo", VALUE_TWO_ECD, VALUE_TWO_TEXT_ECD, ERROR_TEXT_ECD));
        root.add(new AdditionConverter(VALUE_ONE_ECD, VALUE_TWO_ECD, SUM_ECD));
        root.add(new ErrorMessageCommit(frame, ERROR_TEXT_ECD));

        frame.setContentPaneForBoth(contentPane);
        frame.setTitle("AdditionDemo");
        frame.setDefaultCloseOperation(JFrameBB.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static class IntegerSynchronizer extends Synchronizer {
        private final String name;
        private final IntegerECD integerECD;
        private final StringECD textECD;
        private final StringRollbackECD errorECD;

        public IntegerSynchronizer(String name, IntegerECD integerECD, StringECD textECD, StringRollbackECD errorECD) {
            super(
                    "IntegerSynchronizer[" + name + "]",
                    new ObjectECD[] {integerECD},
                    new ObjectECD[] {textECD},
                    null,
                    new ObjectECD[] {errorECD});

            this.name = name;
            this.integerECD = integerECD;
            this.textECD = textECD;
            this.errorECD = errorECD;
        }

        @Override
        protected void convertAToB() {
            int i = ((Integer) get(integerECD)).intValue();

            if (i == -Integer.MAX_VALUE - 1) {
                set(textECD, "");
            } else {
                set(textECD, Integer.toString(i));
            }
        }

        @Override
        protected void convertBToA() {
            String s = (String) get(textECD);

            if (s.length() == 0) {
                set(integerECD, Integer.MIN_VALUE);
            } else {
                try {
                    set(integerECD, Integer.parseInt(s));
                } catch (NumberFormatException nfe) {
                    rollback(errorECD, "Bad Value for " + name + " (" + s + ")");
                }
            }
        }
    }

    private static class AdditionConverter extends Converter {
        private final IntegerECD valueOneECD;
        private final IntegerECD valueTwoECD;
        private final IntegerECD sumECD;

        public AdditionConverter(IntegerECD valueOneECD, IntegerECD valueTwoECD, IntegerECD sumECD) {
            super("AdditionConverter", new ObjectECD[] {valueOneECD, valueTwoECD}, null, new ObjectECD[] {sumECD});

            this.valueOneECD = valueOneECD;
            this.valueTwoECD = valueTwoECD;
            this.sumECD = sumECD;
        }

        @Override
        protected void convert() {
            int v1 = ((Integer) get(valueOneECD)).intValue();
            int v2 = ((Integer) get(valueTwoECD)).intValue();

            set(sumECD, v1 + v2);
        }
    }

    private static class ErrorMessageCommit extends Commit {
        private final Component component;
        private final StringRollbackECD errorMessageECD;

        public ErrorMessageCommit(Component component, StringRollbackECD errorMessageECD) {
            super("ErrorMessageConverter", new ObjectECD[] {errorMessageECD});

            this.component = component;
            this.errorMessageECD = errorMessageECD;
        }

        @Override
        protected void commit() {
            JOptionPane.showMessageDialog(component, get(errorMessageECD), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
