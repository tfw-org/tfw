package tfw.awt.ecd;

import java.awt.FontMetrics;
import tfw.tsm.ecd.IsAssignableFromPredicate;
import tfw.tsm.ecd.ObjectECD;

public class FontMetricsECD extends ObjectECD {
    public FontMetricsECD(String name) {
        super(name, new IsAssignableFromPredicate(FontMetrics.class));
    }
}
