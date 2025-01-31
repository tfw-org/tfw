package tfw.tsm.ecd.ilaf;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactoryFill;
import tfw.tsm.Initiator;
import tfw.tsm.Root;

final class BitIlaFactoryEcdTest {
    @Test
    void constraintTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);
        final ByteIlaFactory byteIlaFactory = ByteIlaFactoryFill.create((byte) 0, 10);
        final BitIlaFactoryEcd bitIlaFactoryEcd = new BitIlaFactoryEcd("BitIlaFactory");
        final Root root = Root.builder()
                .setName("Root")
                .addEventChannel(bitIlaFactoryEcd, null)
                .build();
        final Initiator initiator = Initiator.builder()
                .setName("Initiator")
                .addEventChannelDescription(bitIlaFactoryEcd)
                .build();

        root.add(initiator);

        initiator.set(bitIlaFactoryEcd, bitIlaFactory);
        assertThatThrownBy(() -> initiator.set(bitIlaFactoryEcd, byteIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "The value, of type 'tfw.immutable.ilaf.byteilaf.ByteIlaFactoryFill$ByteIlaFactoryImpl', is not assignable to type 'tfw.immutable.ilaf.bitilaf.BitIlaFactory'.");
    }
}
