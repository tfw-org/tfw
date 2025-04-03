package tfw.tsm.ecd.ilaf;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;
import tfw.immutable.ilaf.intilaf.IntIlaFactoryFill;
import tfw.tsm.Initiator;
import tfw.tsm.Root;

final class IntIlaFactoryEcdTest {
    @Test
    void constraintTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);
        final IntIlaFactory intIlaFactory = IntIlaFactoryFill.create(0, 10);
        final IntIlaFactoryEcd intIlaFactoryEcd = new IntIlaFactoryEcd("IntIlaFactory");
        final Root root = Root.builder()
                .setName("Root")
                .addEventChannel(intIlaFactoryEcd, null)
                .build();
        final Initiator initiator = Initiator.builder()
                .setName("Initiator")
                .addEventChannelDescription(intIlaFactoryEcd)
                .build();

        root.add(initiator);

        initiator.set(intIlaFactoryEcd, intIlaFactory);
        assertThatThrownBy(() -> initiator.set(intIlaFactoryEcd, bitIlaFactory))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
