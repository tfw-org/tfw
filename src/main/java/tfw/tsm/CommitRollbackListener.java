package tfw.tsm;

interface CommitRollbackListener {
    public void commit();

    public void rollback();

    public String getName();
}
