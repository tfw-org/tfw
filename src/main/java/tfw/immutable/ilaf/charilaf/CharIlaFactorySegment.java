package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaSegment;

public class CharIlaFactorySegment {
    private CharIlaFactorySegment() {}

    public static CharIlaFactory create(CharIlaFactory factory, final long start, final long length) {
        return new CharIlaFactoryImpl(factory, start, length);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final CharIlaFactory factory;
        private final long start;
        private final long length;

        public CharIlaFactoryImpl(final CharIlaFactory factory, final long start, final long length) {
            Argument.assertNotNull(factory, "factory");

            this.factory = factory;
            this.start = start;
            this.length = length;
        }

        @Override
        public CharIla create() {
            return CharIlaSegment.create(factory.create(), start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
