package tfw.tsm.ecd.ilaf;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;
import tfw.immutable.ilaf.charilaf.CharIlaFactoryFill;
import tfw.tsm.Initiator;
import tfw.tsm.Root;

final class CharIlaFactoryEcdTest {
    @Test
    void constraintTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);
        final CharIlaFactory charIlaFactory = CharIlaFactoryFill.create((char) 0, 10);
        final CharIlaFactoryEcd charIlaFactoryEcd = new CharIlaFactoryEcd("CharIlaFactory");
        final Root root = Root.builder()
                .setName("Root")
                .addEventChannel(charIlaFactoryEcd, null)
                .build();
        final Initiator initiator = Initiator.builder()
                .setName("Initiator")
                .addEventChannelDescription(charIlaFactoryEcd)
                .build();

        root.add(initiator);

        initiator.set(charIlaFactoryEcd, charIlaFactory);
        assertThatThrownBy(() -> initiator.set(charIlaFactoryEcd, bitIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "The value, of type 'tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill$BitIlaFactoryImpl', is not assignable to type 'tfw.immutable.ilaf.charilaf.CharIlaFactory'.");
    }
}
