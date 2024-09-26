package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.StringECD;

/**
 *
 */
class StateChangeCycleDelayTest {
    private static int globalOrder = 0;
    StringECD ecI = new StringECD("ecI");
    StringECD ecA = new StringECD("ecA");
    StringECD ecB = new StringECD("ecB");
    StringECD ecC = new StringECD("ecC");
    StringECD ecD = new StringECD("ecD");

    @Test
    void testTwoStage() {
        RootFactory rf = new RootFactory();
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Initiator initiator = new Initiator("Test", ecI);
        TestConverter converterA = new TestConverter("ConverterA", new StringECD[] {ecI}, ecA);
        TestConverter converterB = new TestConverter("ConverterB", new StringECD[] {ecI, ecA}, ecB);
        rf.addEventChannel(ecI);
        rf.addEventChannel(ecA, "A initial");
        rf.addEventChannel(ecB, "B initial");

        Root root = rf.create("Test", queue);
        root.add(converterA);
        root.add(converterB);
        root.add(initiator);
        queue.waitTilEmpty();
        converterA.reset();
        converterB.reset();

        initiator.set(ecI, "Start");
        queue.waitTilEmpty();

        assertEquals(1, converterA.getCount(), "Converter A was called the wrong number of times");
        assertEquals(1, converterA.getOrder(), "Converter A was called in the wrong order");
        assertEquals(1, converterB.getCount(), "Converter B was called the wrong number of times");
        assertEquals(2, converterB.getOrder(), "Converter B was called in the wrong order");
    }

    @Test
    void testIndirectDependency() {
        RootFactory rf = new RootFactory();
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Initiator initiator = new Initiator("Test", ecI);
        TestConverter converterA = new TestConverter("ConverterA", new StringECD[] {ecI}, ecA);
        TestConverter converterB = new TestConverter("ConverterB", new StringECD[] {ecA}, ecB);
        TestConverter converterC = new TestConverter("ConverterC", new StringECD[] {ecI, ecB}, ecC);
        rf.addEventChannel(ecI);
        rf.addEventChannel(ecA, "A initial");
        rf.addEventChannel(ecB, "B initial");
        rf.addEventChannel(ecC, "C initial");

        // rf.setLogging(true);
        Root root = rf.create("Test", queue);
        root.add(converterA);
        root.add(converterB);
        root.add(converterC);
        root.add(initiator);
        queue.waitTilEmpty();
        converterA.reset();
        converterB.reset();
        converterC.reset();

        initiator.set(ecI, "Start");
        queue.waitTilEmpty();

        assertEquals(1, converterA.getCount(), "Converter A was called the wrong number of times");
        assertEquals(1, converterA.getOrder(), "Converter A was called in the wrong order");
        assertEquals(1, converterB.getCount(), "Converter B was called the wrong number of times");
        assertEquals(2, converterB.getOrder(), "Converter B was called in the wrong order");
        assertEquals(1, converterC.getCount(), "Converter C was called the wrong number of times");
        assertEquals(3, converterC.getOrder(), "Converter C was called in the wrong order");
    }

    @Test
    void testDirectIndirectDependency() {
        RootFactory rf = new RootFactory();
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Initiator initiator = new Initiator("Test", ecI);
        TestConverter converterA = new TestConverter("ConverterA", new StringECD[] {ecI}, ecA);
        TestConverter converterB = new TestConverter("ConverterB", new StringECD[] {ecI, ecA}, ecB);
        TestConverter converterC = new TestConverter("ConverterC", new StringECD[] {ecB}, ecC);
        TestConverter converterD = new TestConverter("ConverterD", new StringECD[] {ecI, ecC}, ecD);
        rf.addEventChannel(ecI);
        rf.addEventChannel(ecA, "A initial");
        rf.addEventChannel(ecB, "B initial");
        rf.addEventChannel(ecC, "C initial");
        rf.addEventChannel(ecD, "D initial");

        // rf.setLogging(true);
        Root root = rf.create("Test", queue);
        root.add(converterA);
        root.add(converterB);
        root.add(converterC);
        root.add(converterD);
        root.add(initiator);
        queue.waitTilEmpty();
        converterA.reset();
        converterB.reset();
        converterC.reset();
        converterD.reset();

        initiator.set(ecI, "Start");
        queue.waitTilEmpty();

        assertEquals(1, converterA.getCount(), "Converter A was called the wrong number of times");
        assertEquals(1, converterA.getOrder(), "Converter A was called in the wrong order");
        assertEquals(1, converterB.getCount(), "Converter B was called the wrong number of times");
        assertEquals(2, converterB.getOrder(), "Converter B was called in the wrong order");
        assertEquals(1, converterC.getCount(), "Converter C was called the wrong number of times");
        assertEquals(3, converterC.getOrder(), "Converter C was called in the wrong order");
        assertEquals(1, converterD.getCount(), "Converter D was called the wrong number of times");
        assertEquals(4, converterD.getOrder(), "Converter D was called in the wrong order");
    }

    @Test
    void testCircularDependency() {
        RootFactory rf = new RootFactory();
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Initiator initiator = new Initiator("Test", ecI);
        TestConverter converterA = new TestConverter("ConverterA", new StringECD[] {ecI}, ecA);
        TestConverter converterB = new TestConverter("ConverterB", new StringECD[] {ecA}, ecB);
        TestConverter converterC = new TestConverter("ConverterC", new StringECD[] {ecI, ecB}, ecA, false);
        rf.addEventChannel(ecI);
        rf.addEventChannel(ecA, "A initial");
        rf.addEventChannel(ecB, "B initial");
        rf.addEventChannel(ecC, "C initial");

        TestHandler handler = new TestHandler();
        rf.setTransactionExceptionHandler(handler);

        Root root = rf.create("Test", queue);
        root.add(converterA);
        root.add(converterB);
        root.add(converterC);
        root.add(initiator);
        queue.waitTilEmpty();
        converterA.reset();
        converterB.reset();
        converterC.reset();

        initiator.set(ecI, "Start");
        queue.waitTilEmpty();

        if (handler.exception != null) {
            fail("Unexpected exception: " + handler.exception.getMessage());
        }
    }

    @Test
    void testThreeWayDependency() {
        RootFactory rf = new RootFactory();
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Initiator initiator = new Initiator("Test", ecI);
        TestConverter converterA = new TestConverter("ConverterA", new StringECD[] {ecI}, ecA);
        TestConverter converterB = new TestConverter("ConverterB", new StringECD[] {ecI, ecA}, ecB);
        TestConverter converterC = new TestConverter("ConverterC", new StringECD[] {ecI, ecA, ecB}, ecC);
        rf.addEventChannel(ecI);
        rf.addEventChannel(ecA, "A initial");
        rf.addEventChannel(ecB, "B initial");
        rf.addEventChannel(ecC, "C initial");

        // rf.setLogging(true);
        Root root = rf.create("Test", queue);
        root.add(converterA);
        root.add(converterB);
        root.add(converterC);
        root.add(initiator);
        queue.waitTilEmpty();
        converterA.reset();
        converterB.reset();
        converterC.reset();

        initiator.set(ecI, "Start");
        queue.waitTilEmpty();

        assertEquals(1, converterA.getCount(), "Converter A was called the wrong number of times");
        assertEquals(1, converterA.getOrder(), "Converter A was called in the wrong order");
        assertEquals(1, converterB.getCount(), "Converter B was called the wrong number of times");
        assertEquals(2, converterB.getOrder(), "Converter B was called in the wrong order");
        assertEquals(1, converterC.getCount(), "Converter C was called the wrong number of times");
        assertEquals(3, converterC.getOrder(), "Converter C was called in the wrong order");
    }

    private static class TestHandler implements TransactionExceptionHandler {
        private Exception exception = null;

        @Override
        public void handle(Exception exception) {
            this.exception = exception;
        }
    }

    public static class TestConverter extends Converter {
        private int count = 0;
        private int order = -1;
        private final StringECD output;
        private final boolean fire;

        public TestConverter(String name, StringECD[] inputs, StringECD output) {
            this(name, inputs, output, true);
        }

        public TestConverter(String name, StringECD[] inputs, StringECD output, boolean fire) {
            super(name, inputs, new StringECD[] {output});
            this.output = output;
            this.fire = fire;
        }

        public int getCount() {
            return count;
        }

        public int getOrder() {
            return order;
        }

        public void reset() {
            this.count = 0;
            this.order = -1;
            globalOrder = 0;
        }

        @Override
        public void convert() {
            count++;
            order = ++globalOrder;

            // System.out.println(this.getName() + " has been called " + count +
            //    " times");
            // System.out.println(this.get());
            // System.out.println();
            if (fire) {
                set(output, "" + count);
            }
        }
    }
}
