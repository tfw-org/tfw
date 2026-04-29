package tfw.tsm.ecd.ilaf;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill;
import tfw.immutable.ilaf.objectilaf.ObjectIlaFactory;
import tfw.immutable.ilaf.objectilaf.ObjectIlaFactoryFill;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.DefaultCheckDependencies;
import tfw.tsm.Initiator;
import tfw.tsm.Root;

final class ObjectIlaFactoryEcdTest {
    @Test
    void constraintTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);
        final ObjectIlaFactory<Object> objectIlaFactory = ObjectIlaFactoryFill.create(false, 10);
        final ObjectIlaFactoryEcd objectIlaFactoryEcd = new ObjectIlaFactoryEcd("ObjectIlaFactory");
        final Root root = Root.builder()
                .setName("Root")
                .setTransactionQueue(new BasicTransactionQueue())
                .setCheckDependencies(new DefaultCheckDependencies())
                .addObjectECD(objectIlaFactoryEcd, null)
                .build();
        final Initiator initiator = Initiator.builder()
                .setName("Initiator")
                .addEventChannelDescription(objectIlaFactoryEcd)
                .build();

        root.add(initiator);

        initiator.set(objectIlaFactoryEcd, objectIlaFactory);
        assertThatThrownBy(() -> initiator.set(objectIlaFactoryEcd, bitIlaFactory))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
