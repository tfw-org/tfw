package tfw.immutable.iis.chariis;

import tfw.immutable.ila.charila.CharIla;

public final class CharIisFactoryFromCharIla {
    public static CharIisFactory create(final CharIla ila) {
        return new CharIisFactoryImpl(ila);
    }

    private static class CharIisFactoryImpl implements CharIisFactory {
        private final CharIla ila;

        public CharIisFactoryImpl(final CharIla ila) {
            this.ila = ila;
        }

        @Override
        public CharIis create() {
            return CharIisFromCharIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
