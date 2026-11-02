package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;

final class RootTest {
    @Test
    void isRootedTest() {
        final Root root = Root.builder()
                .setName("test")
                .setTransactionQueue(new BasicTransactionQueue())
                .setCheckDependencies(new DefaultCheckDependencies())
                .build();

        assertThat(root.isRooted()).isTrue();
    }

    @Test
    void fullRootTest() {
        final CheckDependencies checkDependencies = new DefaultCheckDependencies();
        final TransactionQueue transactionQueue = new BasicTransactionQueue();
        final TransactionExceptionHandler transactionExceptionHandler = new TestTransactionExceptionHandler();
        final Root root = Root.builder()
                .setName("test")
                .setTransactionQueue(transactionQueue)
                .setCheckDependencies(new DefaultCheckDependencies())
                .setTransactionExceptionHandler(transactionExceptionHandler)
                .setCheckDependencies(checkDependencies)
                .build();

        assertThat(root.getName()).isEqualTo("test");
    }

    @Test
    void testInvalidArguments() {
        final CheckDependencies checkDependencies = new DefaultCheckDependencies();
        final BasicTransactionQueue transactionQueue = new BasicTransactionQueue();
        final TransactionExceptionHandler transactionExceptionHandler = new TestTransactionExceptionHandler();
        final ObjectECD objectECD = new ObjectECD("test");

        final Root root = Root.builder()
                .setName("test")
                .setTransactionQueue(transactionQueue)
                .setCheckDependencies(new DefaultCheckDependencies())
                .setTransactionExceptionHandler(transactionExceptionHandler)
                .setCheckDependencies(checkDependencies)
                .addEventChannel(objectECD, DotEqualsRule.RULE, new Object(), null)
                .build();
        final List<ComponentConfig<?>> componentConfigList = null;

        assertThatThrownBy(() -> root.add(componentConfigList))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("componentConfigs (=null) == null not allowed!");
        assertThatThrownBy(() -> root.remove(componentConfigList))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("componentConfigs (=null) == null not allowed!");
    }

    @Test
    void testAddComponentEnumList() throws Exception {
        final CheckDependencies checkDependencies = new DefaultCheckDependencies();
        final BasicTransactionQueue transactionQueue = new BasicTransactionQueue();
        final TransactionExceptionHandler transactionExceptionHandler = new TestTransactionExceptionHandler();
        final ObjectECD objectECD = new ObjectECD("test");
        final Initiator initiator = Initiator.builder()
                .setName("testInitiator")
                .addEventChannelDescription(objectECD)
                .build();
        final List<ComponentConfig<?>> componentConfigList = new ArrayList<>();

        componentConfigList.add(new DefaultComponentConfig<>(initiator));
        assertThat(componentConfigList).hasSize(1);

        final Root root = Root.builder()
                .setName("test")
                .setTransactionQueue(transactionQueue)
                .setCheckDependencies(new DefaultCheckDependencies())
                .setTransactionExceptionHandler(transactionExceptionHandler)
                .setCheckDependencies(checkDependencies)
                .addEventChannel(objectECD, DotEqualsRule.RULE, new Object(), null)
                .build();
        final Branch addRoot = root.add(componentConfigList);

        transactionQueue.waitTilEmpty();
        assertThat(addRoot).isEqualTo(root);
        assertThat(new RootProxy(root).getChildProxies()).hasSize(1);

        final Branch removeRoot = root.remove(componentConfigList);

        transactionQueue.waitTilEmpty();
        assertThat(removeRoot).isEqualTo(root);
        assertThat(new RootProxy(root).getChildProxies()).isEmpty();
    }
}
