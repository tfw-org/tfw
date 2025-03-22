package tfw.tsm.ecd.ilaf;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;
import tfw.immutable.ilaf.longilaf.LongIlaFactoryFill;
import tfw.tsm.Initiator;
import tfw.tsm.Root;

final class LongIlaFactoryEcdTest {
    @Test
    void constraintTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);
        final LongIlaFactory longIlaFactory = LongIlaFactoryFill.create(0L, 10);
        final LongIlaFactoryEcd longIlaFactoryEcd = new LongIlaFactoryEcd("LongIlaFactory");
        final Root root = Root.builder()
                .setName("Root")
                .addEventChannel(longIlaFactoryEcd, null)
                .build();
        final Initiator initiator = Initiator.builder()
                .setName("Initiator")
                .addEventChannelDescription(longIlaFactoryEcd)
                .build();

        root.add(initiator);

        initiator.set(longIlaFactoryEcd, longIlaFactory);
        assertThatThrownBy(() -> initiator.set(longIlaFactoryEcd, bitIlaFactory))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
