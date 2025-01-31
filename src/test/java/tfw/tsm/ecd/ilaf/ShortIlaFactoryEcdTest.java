package tfw.tsm.ecd.ilaf;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactoryFill;
import tfw.tsm.Initiator;
import tfw.tsm.Root;

final class ShortIlaFactoryEcdTest {
    @Test
    void constraintTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);
        final ShortIlaFactory shortIlaFactory = ShortIlaFactoryFill.create((short) 0, 10);
        final ShortIlaFactoryEcd shortIlaFactoryEcd = new ShortIlaFactoryEcd("ShortIlaFactory");
        final Root root = Root.builder()
                .setName("Root")
                .addEventChannel(shortIlaFactoryEcd, null)
                .build();
        final Initiator initiator = Initiator.builder()
                .setName("Initiator")
                .addEventChannelDescription(shortIlaFactoryEcd)
                .build();

        root.add(initiator);

        initiator.set(shortIlaFactoryEcd, shortIlaFactory);
        assertThatThrownBy(() -> initiator.set(shortIlaFactoryEcd, bitIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "The value, of type 'tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill$BitIlaFactoryImpl', is not assignable to type 'tfw.immutable.ilaf.shortilaf.ShortIlaFactory'.");
    }
}
