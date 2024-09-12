package tfw.demo;

import java.awt.Component;
import javax.swing.JOptionPane;
import tfw.tsm.Converter;
import tfw.tsm.ecd.StringRollbackECD;

public class ErrorDialog extends Converter {
    private final StringRollbackECD errorName;
    private final Component parent;

    public ErrorDialog(Component parent, StringRollbackECD errorName) {
        super("ErrorDialog", new StringRollbackECD[] {errorName}, null, null);

        this.parent = parent;
        this.errorName = errorName;
    }

    protected void convert() {
        JOptionPane.showMessageDialog(parent, get(errorName), "Error Message", JOptionPane.ERROR_MESSAGE);
    }
}
