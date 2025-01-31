package tfw.tsm.ecd.ilaf;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactoryFill;
import tfw.tsm.Initiator;
import tfw.tsm.Root;

final class DoubleIlaFactoryEcdTest {
    @Test
    void constraintTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);
        final DoubleIlaFactory doubleIlaFactory = DoubleIlaFactoryFill.create(0.0, 10);
        final DoubleIlaFactoryEcd doubleIlaFactoryEcd = new DoubleIlaFactoryEcd("DoubleIlaFactory");
        final Root root = Root.builder()
                .setName("Root")
                .addEventChannel(doubleIlaFactoryEcd, null)
                .build();
        final Initiator initiator = Initiator.builder()
                .setName("Initiator")
                .addEventChannelDescription(doubleIlaFactoryEcd)
                .build();

        root.add(initiator);

        initiator.set(doubleIlaFactoryEcd, doubleIlaFactory);
        assertThatThrownBy(() -> initiator.set(doubleIlaFactoryEcd, bitIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "The value, of type 'tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill$BitIlaFactoryImpl', is not assignable to type 'tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory'.");
    }
}
