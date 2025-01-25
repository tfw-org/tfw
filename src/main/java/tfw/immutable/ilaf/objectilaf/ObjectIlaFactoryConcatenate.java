package tfw.immutable.ilaf.objectilaf;

import tfw.check.Argument;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaConcatenate;

public class ObjectIlaFactoryConcatenate {
    private ObjectIlaFactoryConcatenate() {}

    public static <T> ObjectIlaFactory<T> create(ObjectIlaFactory<T> leftFactory, ObjectIlaFactory<T> rightFactory) {
        return new ObjectIlaFactoryImpl<>(leftFactory, rightFactory);
    }

    private static class ObjectIlaFactoryImpl<T> implements ObjectIlaFactory<T> {
        private final ObjectIlaFactory<T> leftFactory;
        private final ObjectIlaFactory<T> rightFactory;

        public ObjectIlaFactoryImpl(final ObjectIlaFactory<T> leftFactory, ObjectIlaFactory<T> rightFactory) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
        }

        @Override
        public ObjectIla<T> create() {
            return ObjectIlaConcatenate.create(leftFactory.create(), rightFactory.create());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
