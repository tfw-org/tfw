package tfw.immutable.ila.demo;

import java.io.File;
import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromFile;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;
import tfw.immutable.ila.byteila.ByteIlaSwap;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromLongIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromIntIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromByteIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromByteIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;
import tfw.immutable.ila.objectila.AbstractObjectIla;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaIterator;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromByteIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public class OctalDump {
    public static final int BUFFER_SIZE = 1000;

    public static void main(String[] args) throws IOException {
        if (args.length != 3 || args[1].length() != 2) {
            System.err.println("Usage: OctalDump <-t type> <file>");
            System.err.println("       type = <d,f,o,x><1,2,4,8>");
            System.exit(-1);
        }

        ByteIla bytes = ByteIlaFromFile.create(new File(args[2]));
        ObjectIla<String> strings = null;
        char type = args[1].charAt(0);
        char size = args[1].charAt(1);

        switch (type) {
            case 'd':
            case 'o':
            case 'x':
                switch (size) {
                    case '1': {
                        strings = StringObjectIlaFromByteIla.create(bytes, type, BUFFER_SIZE);
                        break;
                    }
                    case '2': {
                        ByteIla byteIla = ByteIlaSwap.create(bytes, 2, BUFFER_SIZE);
                        ShortIla shortIla = ShortIlaFromByteIla.create(byteIla, BUFFER_SIZE);
                        strings = StringObjectIlaFromShortIla.create(shortIla, type, BUFFER_SIZE);
                        break;
                    }
                    case '4': {
                        ByteIla byteIla = ByteIlaSwap.create(bytes, 4, BUFFER_SIZE);
                        IntIla intIla = IntIlaFromByteIla.create(byteIla, BUFFER_SIZE);
                        strings = StringObjectIlaFromIntIla.create(intIla, type, BUFFER_SIZE);
                        break;
                    }
                    case '8': {
                        ByteIla byteIla = ByteIlaSwap.create(bytes, 8, BUFFER_SIZE);
                        LongIla longIla = LongIlaFromByteIla.create(byteIla, BUFFER_SIZE);
                        strings = StringObjectIlaFromLongIla.create(longIla, type, BUFFER_SIZE);
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("size=" + size + " for 'd', 'o', or 'x' not allowed!");
                }
                break;
            case 'f':
                switch (size) {
                    case '4': {
                        ByteIla byteIla = ByteIlaSwap.create(bytes, 4, BUFFER_SIZE);
                        IntIla intIla = IntIlaFromByteIla.create(byteIla, BUFFER_SIZE);
                        FloatIla floatIla = FloatIlaFromIntIla.create(intIla, BUFFER_SIZE);
                        strings = StringObjectIlaFromFloatIla.create(floatIla, BUFFER_SIZE);
                        break;
                    }
                    case '8': {
                        ByteIla byteIla = ByteIlaSwap.create(bytes, 8, BUFFER_SIZE);
                        LongIla longIla = LongIlaFromByteIla.create(byteIla, BUFFER_SIZE);
                        DoubleIla doubleIla = DoubleIlaFromLongIla.create(longIla, BUFFER_SIZE);
                        strings = StringObjectIlaFromDoubleIla.create(doubleIla, BUFFER_SIZE);
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("size=" + size + " for 'f' not allowed!");
                }
                break;
            default:
                throw new IllegalArgumentException("type=" + type + " not allowed!");
        }

        for (ObjectIlaIterator<String> oii = new ObjectIlaIterator<>(strings, new String[BUFFER_SIZE]);
                oii.hasNext(); ) {
            System.out.println(oii.next());
        }

        System.out.println("strings = \n" + strings);
    }

    private static class StringObjectIlaFromByteIla {
        private StringObjectIlaFromByteIla() {}

        public static ObjectIla<String> create(final ByteIla byteIla, final char type, final int bufferSize) {
            Argument.assertNotNull(byteIla, "byteIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            return new MyObjectIla(byteIla, type, bufferSize);
        }

        private static class MyObjectIla extends AbstractObjectIla<String> {
            private final ByteIla byteIla;
            private final char type;
            private final int bufferSize;

            MyObjectIla(final ByteIla byteIla, final char type, final int bufferSize) {
                super(byteIla.length() / 16 + (byteIla.length() % 16 == 0 ? 0 : 1));

                this.byteIla = byteIla;
                this.type = type;
                this.bufferSize = bufferSize;
            }

            protected void toArrayImpl(String[] array, int offset, long start, int length) throws IOException {
                ByteIlaIterator bii = new ByteIlaIterator(
                        ByteIlaSegment.create(byteIla, start, byteIla.length() - start), new byte[bufferSize]);

                for (int i = 0; i < length; i++) {
                    StringBuffer sb = new StringBuffer();

                    sb.append(Long.toOctalString(start + i * 16));
                    for (int j = 0; j < 16 && bii.hasNext(); j++) {
                        sb.append(" ");
                        switch (type) {
                            case 'd':
                                sb.append(Byte.toString(bii.next()));
                                break;
                            case 'o':
                                sb.append(Integer.toOctalString(bii.next() & 0xFF));
                                break;
                            case 'x':
                                sb.append(Integer.toHexString(bii.next() & 0xFF));
                                break;
                            default:
                                throw new IllegalArgumentException("size=1 for '" + type + "' not allowed!");
                        }
                    }
                    sb.append("\n");

                    array[offset + i] = sb.toString();
                }
            }
        }
    }

    private static class StringObjectIlaFromShortIla {
        private StringObjectIlaFromShortIla() {}

        public static ObjectIla<String> create(final ShortIla shortIla, final char type, final int bufferSize) {
            Argument.assertNotNull(shortIla, "shortIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            return new MyObjectIla(shortIla, type, bufferSize);
        }

        private static class MyObjectIla extends AbstractObjectIla<String> {
            private final ShortIla shortIla;
            private final char type;
            private final int bufferSize;

            MyObjectIla(final ShortIla shortIla, final char type, final int bufferSize) {
                super(shortIla.length() / 8 + (shortIla.length() % 8 == 0 ? 0 : 1));

                this.shortIla = shortIla;
                this.type = type;
                this.bufferSize = bufferSize;
            }

            protected void toArrayImpl(String[] array, int offset, long start, int length) throws IOException {
                ShortIlaIterator sii = new ShortIlaIterator(
                        ShortIlaSegment.create(shortIla, start * 2, shortIla.length() - start * 2),
                        new short[bufferSize]);

                for (int i = 0; i < length; i++) {
                    StringBuffer sb = new StringBuffer();

                    sb.append(Long.toOctalString(start + i * 16));
                    for (int j = 0; j < 8 && sii.hasNext(); j++) {
                        sb.append(" ");
                        switch (type) {
                            case 'd':
                                sb.append(Short.toString(sii.next()));
                                break;
                            case 'o':
                                sb.append(Integer.toOctalString(sii.next() & 0xFFFF));
                                break;
                            case 'x':
                                sb.append(Integer.toHexString(sii.next() & 0xFFFF));
                                break;
                            default:
                                throw new IllegalArgumentException("size=2 for '" + type + "' not allowed!");
                        }
                    }
                    sb.append("\n");

                    array[offset + i] = sb.toString();
                }
            }
        }
    }

    private static class StringObjectIlaFromIntIla {
        private StringObjectIlaFromIntIla() {}

        public static ObjectIla<String> create(final IntIla intIla, final char type, final int bufferSize) {
            Argument.assertNotNull(intIla, "intIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            return new MyObjectIla(intIla, type, bufferSize);
        }

        private static class MyObjectIla extends AbstractObjectIla<String> {
            private final IntIla intIla;
            private final char type;
            private final int bufferSize;

            MyObjectIla(final IntIla intIla, final char type, final int bufferSize) {
                super(intIla.length() / 4 + (intIla.length() % 4 == 0 ? 0 : 1));

                this.intIla = intIla;
                this.type = type;
                this.bufferSize = bufferSize;
            }

            protected void toArrayImpl(String[] array, int offset, long start, int length) throws IOException {
                IntIlaIterator iii = new IntIlaIterator(
                        IntIlaSegment.create(intIla, start * 4, intIla.length() - start * 4), new int[bufferSize]);

                for (int i = 0; i < length; i++) {
                    StringBuffer sb = new StringBuffer();

                    sb.append(Long.toOctalString(start + i * 16));
                    for (int j = 0; j < 4 && iii.hasNext(); j++) {
                        sb.append(" ");
                        switch (type) {
                            case 'd':
                                sb.append(Integer.toString(iii.next()));
                                break;
                            case 'o':
                                sb.append(Integer.toOctalString(iii.next()));
                                break;
                            case 'x':
                                sb.append(Integer.toHexString(iii.next()));
                                break;
                        }
                    }
                    sb.append("\n");

                    array[offset + i] = sb.toString();
                }
            }
        }
    }

    private static class StringObjectIlaFromLongIla {
        private StringObjectIlaFromLongIla() {}

        public static ObjectIla<String> create(final LongIla longIla, final char type, final int bufferSize) {
            Argument.assertNotNull(longIla, "longIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            return new MyObjectIla(longIla, type, bufferSize);
        }

        private static class MyObjectIla extends AbstractObjectIla<String> {
            private final LongIla longIla;
            private final char type;
            private final int bufferSize;

            MyObjectIla(final LongIla longIla, final char type, final int bufferSize) {
                super(longIla.length() / 2 + (longIla.length() % 2 == 0 ? 0 : 1));

                this.longIla = longIla;
                this.type = type;
                this.bufferSize = bufferSize;
            }

            protected void toArrayImpl(String[] array, int offset, long start, int length) throws IOException {
                LongIlaIterator lii = new LongIlaIterator(
                        LongIlaSegment.create(longIla, start * 8, longIla.length() - start * 8), new long[bufferSize]);

                for (int i = 0; i < length; i++) {
                    StringBuffer sb = new StringBuffer();

                    sb.append(Long.toOctalString(start + i * 16));
                    for (int j = 0; j < 2 && lii.hasNext(); j++) {
                        sb.append(" ");
                        switch (type) {
                            case 'd':
                                sb.append(Long.toString(lii.next()));
                                break;
                            case 'o':
                                sb.append(Long.toOctalString(lii.next()));
                                break;
                            case 'x':
                                sb.append(Long.toHexString(lii.next()));
                                break;
                        }
                    }
                    sb.append("\n");

                    array[offset + i] = sb.toString();
                }
            }
        }
    }

    private static class StringObjectIlaFromFloatIla {
        private StringObjectIlaFromFloatIla() {}

        public static ObjectIla<String> create(final FloatIla floatIla, final int bufferSize) {
            Argument.assertNotNull(floatIla, "floatIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            return new MyObjectIla(floatIla, bufferSize);
        }

        private static class MyObjectIla extends AbstractObjectIla<String> {
            private final FloatIla floatIla;
            private final int bufferSize;

            MyObjectIla(final FloatIla floatIla, final int bufferSize) {
                super(floatIla.length() / 4 + (floatIla.length() % 4 == 0 ? 0 : 1));

                this.floatIla = floatIla;
                this.bufferSize = bufferSize;
            }

            protected void toArrayImpl(String[] array, int offset, long start, int length) throws IOException {
                FloatIlaIterator fii = new FloatIlaIterator(
                        FloatIlaSegment.create(floatIla, start * 4, floatIla.length() - start * 4),
                        new float[bufferSize]);

                for (int i = 0; i < length; i++) {
                    StringBuffer sb = new StringBuffer();

                    sb.append(Long.toOctalString(start + i * 16));
                    for (int j = 0; j < 4 && fii.hasNext(); j++) {
                        sb.append(" ");
                        sb.append(Float.toString(fii.next()));
                    }
                    sb.append("\n");

                    array[offset + i] = sb.toString();
                }
            }
        }
    }

    private static class StringObjectIlaFromDoubleIla {
        private StringObjectIlaFromDoubleIla() {}

        public static ObjectIla<String> create(final DoubleIla doubleIla, final int bufferSize) {
            Argument.assertNotNull(doubleIla, "doubleIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            return new MyObjectIla(doubleIla, bufferSize);
        }

        private static class MyObjectIla extends AbstractObjectIla<String> {
            private final DoubleIla doubleIla;
            private final int bufferSize;

            MyObjectIla(final DoubleIla doubleIla, final int bufferSize) {
                super(doubleIla.length() / 2 + (doubleIla.length() % 2 == 0 ? 0 : 1));

                this.doubleIla = doubleIla;
                this.bufferSize = bufferSize;
            }

            protected void toArrayImpl(String[] array, int offset, long start, int length) throws IOException {
                DoubleIlaIterator dii = new DoubleIlaIterator(
                        DoubleIlaSegment.create(doubleIla, start * 4, doubleIla.length() - start * 4),
                        new double[bufferSize]);

                for (int i = 0; i < length; i++) {
                    StringBuffer sb = new StringBuffer();

                    sb.append(Long.toOctalString(start + i * 16));
                    for (int j = 0; j < 2 && dii.hasNext(); j++) {
                        sb.append(" ");
                        sb.append(Double.toString(dii.next()));
                    }
                    sb.append("\n");

                    array[offset + i] = sb.toString();
                }
            }
        }
    }
}
