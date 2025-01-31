package tfw.tsm.ecd.ilaf;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill;
import tfw.immutable.ilaf.booleanilaf.BooleanIlaFactory;
import tfw.immutable.ilaf.booleanilaf.BooleanIlaFactoryFill;
import tfw.tsm.Initiator;
import tfw.tsm.Root;

final class BooleanIlaFactoryEcdTest {
    @Test
    void constraintTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);
        final BooleanIlaFactory booleanIlaFactory = BooleanIlaFactoryFill.create(false, 10);
        final BooleanIlaFactoryEcd booleanIlaFactoryEcd = new BooleanIlaFactoryEcd("BooleanIlaFactory");
        final Root root = Root.builder()
                .setName("Root")
                .addEventChannel(booleanIlaFactoryEcd, null)
                .build();
        final Initiator initiator = Initiator.builder()
                .setName("Initiator")
                .addEventChannelDescription(booleanIlaFactoryEcd)
                .build();

        root.add(initiator);

        initiator.set(booleanIlaFactoryEcd, booleanIlaFactory);
        assertThatThrownBy(() -> initiator.set(booleanIlaFactoryEcd, bitIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "The value, of type 'tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill$BitIlaFactoryImpl', is not assignable to type 'tfw.immutable.ilaf.booleanilaf.BooleanIlaFactory'.");
    }
}
