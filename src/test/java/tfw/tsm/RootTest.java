package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class RootTest {
    @Test
    void isRootedTest() {
        final Root root = Root.builder().setName("test").build();

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
                .setTransactionExceptionHandler(transactionExceptionHandler)
                .setCheckDependencies(checkDependencies)
                .build();

        assertThat(root.getName()).isEqualTo("test");
    }
}
