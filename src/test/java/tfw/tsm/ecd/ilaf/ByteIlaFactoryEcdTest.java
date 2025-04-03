package tfw.tsm.ecd.ilaf;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactoryFill;
import tfw.tsm.Initiator;
import tfw.tsm.Root;

final class ByteIlaFactoryEcdTest {
    @Test
    void constraintTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);
        final ByteIlaFactory byteIlaFactory = ByteIlaFactoryFill.create((byte) 0, 10);
        final ByteIlaFactoryEcd byteIlaFactoryEcd = new ByteIlaFactoryEcd("ByteIlaFactory");
        final Root root = Root.builder()
                .setName("Root")
                .addEventChannel(byteIlaFactoryEcd, null)
                .build();
        final Initiator initiator = Initiator.builder()
                .setName("Initiator")
                .addEventChannelDescription(byteIlaFactoryEcd)
                .build();

        root.add(initiator);

        initiator.set(byteIlaFactoryEcd, byteIlaFactory);
        assertThatThrownBy(() -> initiator.set(byteIlaFactoryEcd, bitIlaFactory))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
