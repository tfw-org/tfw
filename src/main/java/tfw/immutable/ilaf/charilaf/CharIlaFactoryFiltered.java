package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFiltered;
import tfw.immutable.ila.charila.CharIlaFiltered.CharFilter;

public class CharIlaFactoryFiltered {
    private CharIlaFactoryFiltered() {}

    public static CharIlaFactory create(CharIlaFactory ilaFactory, CharFilter filter, char[] buffer) {
        return new CharIlaFactoryImpl(ilaFactory, filter, buffer);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final CharIlaFactory ilaFactory;
        private final CharFilter filter;
        private final char[] buffer;

        public CharIlaFactoryImpl(final CharIlaFactory ilaFactory, CharFilter filter, char[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        public CharIla create() {
            return CharIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
