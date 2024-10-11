package tfw.swing;

import tfw.check.Argument;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.TreeComponent;

public final class SwingUtil {
    private SwingUtil() {}

    public static void addObjectToBranch(final Object object, final Branch branch) {
        Argument.assertNotNull(object, "object");
        Argument.assertNotNull(branch, "branch");

        if (object instanceof BranchBox) {
            branch.add((BranchBox) object);
        } else if (object instanceof TreeComponent) {
            branch.add((TreeComponent) object);
        } else {
            throw new IllegalArgumentException("object != (BranchBox || TreeComponent) not allowed!");
        }
    }

    public static void removeObjectFromBranch(final Object object, final Branch branch) {
        Argument.assertNotNull(object, "object");
        Argument.assertNotNull(branch, "branch");

        if (object instanceof BranchBox) {
            branch.remove((BranchBox) object);
        } else if (object instanceof TreeComponent) {
            branch.remove((TreeComponent) object);
        } else {
            throw new IllegalArgumentException("object != (BranchBox || TreeComponent) not allowed!");
        }
    }
}
