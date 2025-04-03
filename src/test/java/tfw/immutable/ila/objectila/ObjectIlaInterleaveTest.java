package tfw.immutable.ila.objectila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Array;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ObjectIlaInterleaveTest {
    @SuppressWarnings("unchecked")
    @Test
    void argumentsTest() {
        final ObjectIla<Object> ila1 = ObjectIlaFromArray.create(new Object[10]);
        final ObjectIla<Object> ila2 = ObjectIlaFromArray.create(new Object[20]);
        final ObjectIla<Object>[] ilas1 = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, 0);
        final ObjectIla<Object>[] ilas2 = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, 2);
        final ObjectIla<Object>[] ilas3 = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, 2);
        final ObjectIla<Object>[] ilas4 = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, 2);
        final ObjectIla<Object>[] ilas5 = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, 2);
        final ObjectIla<Object>[] ilas6 = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, 2);
        final Object[] buffer = new Object[10];

        ilas3[0] = null;
        ilas3[1] = ila1;
        ilas4[0] = ila1;
        ilas4[1] = null;
        ilas5[0] = ila1;
        ilas5[1] = ila1;
        ilas6[0] = ila1;
        ilas6[1] = ila2;

        assertThatThrownBy(() -> ObjectIlaInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaInterleave.create(ilas5, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaInterleave.create(ilas1, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas.length (=0) < 1 not allowed!");
        assertThatThrownBy(() -> ObjectIlaInterleave.create(ilas2, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[0] == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaInterleave.create(ilas3, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[0] == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaInterleave.create(ilas4, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[1] == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaInterleave.create(ilas6, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[0].length() (=20) != ilas[1].length() (=10) not allowed!");
    }

    @SuppressWarnings("unchecked")
    @Test
    void allTest() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final Object[][] target = new Object[jj][length];
            final Object[] array = new Object[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = new Object();
            }
            ObjectIla<Object>[] ilas = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, jj);
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = ObjectIlaFromArray.create(target[ii]);
            }
            ObjectIla<Object> targetIla = ObjectIlaFromArray.create(array);
            ObjectIla<Object> actualIla = ObjectIlaInterleave.create(ilas, new Object[1000]);

            ObjectIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
