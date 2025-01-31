package tfw.tsm.ecd.ilaf;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactoryFill;
import tfw.tsm.Initiator;
import tfw.tsm.Root;

final class FloatIlaFactoryEcdTest {
    @Test
    void constraintTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);
        final FloatIlaFactory floatIlaFactory = FloatIlaFactoryFill.create(0.0F, 10);
        final FloatIlaFactoryEcd floatIlaFactoryEcd = new FloatIlaFactoryEcd("FloatIlaFactory");
        final Root root = Root.builder()
                .setName("Root")
                .addEventChannel(floatIlaFactoryEcd, null)
                .build();
        final Initiator initiator = Initiator.builder()
                .setName("Initiator")
                .addEventChannelDescription(floatIlaFactoryEcd)
                .build();

        root.add(initiator);

        initiator.set(floatIlaFactoryEcd, floatIlaFactory);
        assertThatThrownBy(() -> initiator.set(floatIlaFactoryEcd, bitIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "The value, of type 'tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill$BitIlaFactoryImpl', is not assignable to type 'tfw.immutable.ilaf.floatilaf.FloatIlaFactory'.");
    }
}
