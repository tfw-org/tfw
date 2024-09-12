package tfw.tsm;

interface CommitRollbackListener {
    void commit();

    void rollback();

    String getName();
}
