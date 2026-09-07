package tfw.tsm.ecd.io;

import java.io.File;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class FileECD extends ObjectECD {
    public FileECD(String name) {
        super(name, new IsAssignableFromPredicate(File.class));
    }
}
