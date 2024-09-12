package tfw.tsm;

import tfw.tsm.TransactionMgr.AddComponentRunnable;
import tfw.tsm.TransactionMgr.RemoveComponentRunnable;

class TransactionMgrUtil {
    private TransactionMgrUtil() {}

    public static void postAddRemoveSetsToQueue(Object[] objects, TransactionMgr transactionMgr) {
        transactionMgr.lockTransactionQueue();

        try {
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] instanceof TransactionMgr.AddComponentRunnable) {
                    AddComponentRunnable acr = (AddComponentRunnable) objects[i];
                    acr.setTransactionMgr(transactionMgr);
                    transactionMgr.addComponent(acr, new Throwable("Add"));
                } else if (objects[i] instanceof TransactionMgr.RemoveComponentRunnable) {
                    RemoveComponentRunnable rcr = (RemoveComponentRunnable) objects[i];
                    rcr.setTransactionMgr(transactionMgr);
                    transactionMgr.removeComponent(rcr, new Throwable("REMOVE"));
                } else if (objects[i] instanceof Initiator.TransactionContainer) {
                    Initiator.TransactionContainer transactionContainer = (Initiator.TransactionContainer) objects[i];

                    transactionMgr.addStateChange(
                            transactionContainer.state.sources,
                            transactionContainer.state.state,
                            transactionContainer.transactionState,
                            transactionContainer.setLocation);
                }
            }
        } finally {
            transactionMgr.unlockTransactionQueue();
        }
    }
}
