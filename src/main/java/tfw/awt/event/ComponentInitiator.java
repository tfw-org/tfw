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

    @Override
    public final void componentHidden(ComponentEvent e) {
        if (visibleECD != null) {
            set(visibleECD, Boolean.FALSE);
        }
    }

    @Override
    public final void componentMoved(ComponentEvent e) {
        if (xECD == null) {
            if (yECD != null) {
                set(yECD, e.getComponent().getLocation().y);
            }
        } else {
            if (yECD == null) {
                set(xECD, e.getComponent().getLocation().x);
            } else {
                EventChannelStateBuffer ecsb = new EventChannelStateBuffer();

                ecsb.put(xECD, e.getComponent().getLocation().x);
                ecsb.put(yECD, e.getComponent().getLocation().y);

                set(ecsb.toArray());
            }
        }
    }

    @Override
    public final void componentResized(ComponentEvent e) {
        if (widthECD == null) {
            if (heightECD != null) {
                set(heightECD, e.getComponent().getSize().height);
            }
        } else {
            if (heightECD == null) {
                set(widthECD, e.getComponent().getSize().width);
            } else {
                EventChannelStateBuffer ecsb = new EventChannelStateBuffer();

                ecsb.put(widthECD, e.getComponent().getSize().width);
                ecsb.put(heightECD, e.getComponent().getSize().height);

                set(ecsb.toArray());
            }
        }
    }

    @Override
    public final void componentShown(ComponentEvent e) {
        if (visibleECD != null) {
            set(visibleECD, Boolean.TRUE);
        }
    }
}
