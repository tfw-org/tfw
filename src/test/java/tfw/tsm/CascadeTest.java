package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

final class CascadeTest {
    private int value = 0;
    private int convertABValue = -1;
    private int convertBCValue = -1;
    private int validateAValue = -1;
    private int validateBValue = -1;
    private int validateCValue = -1;
    private int commitValue = -1;

    private StringECD portA = new StringECD("a");
    private StringECD portB = new StringECD("b");
    private StringECD portC = new StringECD("c");
    private Initiator initiator = new Initiator("Initiator", new ObjectECD[] {portA});

    private Validator aValidator = new Validator("A Validator", new ObjectECD[] {portA}, null) {
        @Override
        protected void validateState() {
            validateAValue = value++;
        }
    };

    private Converter converterAB = new Converter("converterAB", new ObjectECD[] {portA}, new ObjectECD[] {portB}) {
        @Override
        protected void convert() {
            convertABValue = value++;
            set(portB, "");
        }
    };

    private Validator bValidator = new Validator("B Validator", new ObjectECD[] {portB}, null) {
        @Override
        protected void validateState() {
            validateBValue = value++;
        }
    };

    private Converter converterBC = new Converter("converterBC", new ObjectECD[] {portB}, new ObjectECD[] {portC}) {
        @Override
        protected void convert() {
            set(portC, "");
            convertBCValue = value++;
        }
    };

    private Validator cValidator = new Validator("C Validator", new ObjectECD[] {portC}, null) {
        @Override
        protected void validateState() {
            validateCValue = value++;
        }
    };

    private Commit commit = new Commit("Commit", new ObjectECD[] {portC}) {
        @Override
        protected void commit() {
            commitValue = value;
        }
    };

    @Test
    void converterTest() {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(portA);
        rf.addEventChannel(portB);
        rf.addEventChannel(portC);
        BasicTransactionQueue queue = new BasicTransactionQueue();

        Branch branch = rf.create("Test branch", queue);
        branch.add(initiator);
        branch.add(aValidator);
        branch.add(converterAB);
        branch.add(bValidator);
        branch.add(converterBC);
        branch.add(cValidator);
        branch.add(commit);

        initiator.set(portA, "Hello");
        queue.waitTilEmpty();
        assertThat(validateAValue).isEqualTo(0);
        assertThat(convertABValue).isEqualTo(1);
        assertThat(validateBValue).isEqualTo(2);
        assertThat(convertBCValue).isEqualTo(3);
        assertThat(validateCValue).isEqualTo(4);
        assertThat(commitValue).isEqualTo(5);
    }
}
