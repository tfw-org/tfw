package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.StringECD;

final class StateChangeCycleDelayTest {
    private static int globalOrder = 0;
    StringECD ecI = new StringECD("ecI");
    StringECD ecA = new StringECD("ecA");
    StringECD ecB = new StringECD("ecB");
    StringECD ecC = new StringECD("ecC");
    StringECD ecD = new StringECD("ecD");

    @Test
    void twoStageTest() {
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

        assertThat(converterA.getCount()).isEqualTo(1);
        assertThat(converterA.getOrder()).isEqualTo(1);
        assertThat(converterB.getCount()).isEqualTo(1);
        assertThat(converterB.getOrder()).isEqualTo(2);
    }

    @Test
    void indirectDependencyTest() {
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

        assertThat(converterA.getCount()).isEqualTo(1);
        assertThat(converterA.getOrder()).isEqualTo(1);
        assertThat(converterB.getCount()).isEqualTo(1);
        assertThat(converterB.getOrder()).isEqualTo(2);
        assertThat(converterC.getCount()).isEqualTo(1);
        assertThat(converterC.getOrder()).isEqualTo(3);
    }

    @Test
    void directIndirectDependencyTest() {
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

        assertThat(converterA.getCount()).isEqualTo(1);
        assertThat(converterA.getOrder()).isEqualTo(1);
        assertThat(converterB.getCount()).isEqualTo(1);
        assertThat(converterB.getOrder()).isEqualTo(2);
        assertThat(converterC.getCount()).isEqualTo(1);
        assertThat(converterC.getOrder()).isEqualTo(3);
        assertThat(converterD.getCount()).isEqualTo(1);
        assertThat(converterD.getOrder()).isEqualTo(4);
    }

    @Test
    void circularDependencyTest() {
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

        assertThat(handler.exception).isNull();
    }

    @Test
    void threeWayDependencyTest() {
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

        assertThat(converterA.getCount()).isEqualTo(1);
        assertThat(converterA.getOrder()).isEqualTo(1);
        assertThat(converterB.getCount()).isEqualTo(1);
        assertThat(converterB.getOrder()).isEqualTo(2);
        assertThat(converterC.getCount()).isEqualTo(1);
        assertThat(converterC.getOrder()).isEqualTo(3);
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
