package tfw.tsm;

import java.lang.reflect.InvocationTargetException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

public class CascadeTest extends TestCase {
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
        protected void validateState() {
            validateAValue = value++;
        }
    };

    private Converter converterAB = new Converter("converterAB", new ObjectECD[] {portA}, new ObjectECD[] {portB}) {
        protected void convert() {
            convertABValue = value++;
            set(portB, "");
        }
    };

    private Validator bValidator = new Validator("B Validator", new ObjectECD[] {portB}, null) {
        protected void validateState() {
            validateBValue = value++;
        }
    };

    private Converter converterBC = new Converter("converterBC", new ObjectECD[] {portB}, new ObjectECD[] {portC}) {
        protected void convert() {
            set(portC, "");
            convertBCValue = value++;
        }
    };

    private Validator cValidator = new Validator("C Validator", new ObjectECD[] {portC}, null) {
        protected void validateState() {
            validateCValue = value++;
        }
    };

    private Commit commit = new Commit("Commit", new ObjectECD[] {portC}) {
        protected void commit() {
            commitValue = value;
        }
    };

    public void setUp() {}

    public void testConverter() throws InterruptedException, InvocationTargetException {
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

        // Visualize.print(branch);
        initiator.set(portA, "Hello");
        queue.waitTilEmpty();
        assertEquals("validateA", 0, validateAValue);
        assertEquals("convertAB", 1, convertABValue);
        assertEquals("validateB", 2, validateBValue);
        assertEquals("convertBC", 3, convertBCValue);
        assertEquals("validateC", 4, validateCValue);
        assertEquals("commit", 5, commitValue);
    }

    public static Test suite() {
        return new TestSuite(CascadeTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
