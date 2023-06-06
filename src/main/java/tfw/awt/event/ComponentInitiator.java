package tfw.awt.event;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import tfw.tsm.EventChannelStateBuffer;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescriptionUtil;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;

public class ComponentInitiator extends Initiator implements ComponentListener {
    private final BooleanECD visibleECD;
    private final IntegerECD xECD;
    private final IntegerECD yECD;
    private final IntegerECD widthECD;
    private final IntegerECD heightECD;

    public ComponentInitiator(
            String name,
            BooleanECD visibleECD,
            IntegerECD xECD,
            IntegerECD yECD,
            IntegerECD widthECD,
            IntegerECD heightECD) {
        super(
                "ComponentInitiator[" + name + "]",
                EventChannelDescriptionUtil.create(new ObjectECD[] {visibleECD, xECD, yECD, widthECD, heightECD}));

        this.visibleECD = visibleECD;
        this.xECD = xECD;
        this.yECD = yECD;
        this.widthECD = widthECD;
        this.heightECD = heightECD;
    }

    public final void componentHidden(ComponentEvent e) {
        if (visibleECD != null) {
            set(visibleECD, Boolean.FALSE);
        }
    }

    public final void componentMoved(ComponentEvent e) {
        if (xECD == null) {
            if (yECD == null) {
                // Do Nothing...
            } else {
                set(yECD, new Integer(e.getComponent().getLocation().y));
            }
        } else {
            if (yECD == null) {
                set(xECD, new Integer(e.getComponent().getLocation().x));
            } else {
                EventChannelStateBuffer ecsb = new EventChannelStateBuffer();

                ecsb.put(xECD, new Integer(e.getComponent().getLocation().x));
                ecsb.put(yECD, new Integer(e.getComponent().getLocation().y));

                set(ecsb.toArray());
            }
        }
    }

    public final void componentResized(ComponentEvent e) {
        if (widthECD == null) {
            if (heightECD == null) {
                // Do Nothing...
            } else {
                set(heightECD, new Integer(e.getComponent().getSize().height));
            }
        } else {
            if (heightECD == null) {
                set(widthECD, new Integer(e.getComponent().getSize().width));
            } else {
                EventChannelStateBuffer ecsb = new EventChannelStateBuffer();

                ecsb.put(widthECD, new Integer(e.getComponent().getSize().width));
                ecsb.put(heightECD, new Integer(e.getComponent().getSize().height));

                set(ecsb.toArray());
            }
        }
    }

    public final void componentShown(ComponentEvent e) {
        if (visibleECD != null) {
            set(visibleECD, Boolean.TRUE);
        }
    }
}
