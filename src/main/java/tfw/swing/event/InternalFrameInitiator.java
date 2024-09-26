package tfw.swing.event;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.EventChannelDescriptionUtil;
import tfw.tsm.ecd.StatelessTriggerECD;

public class InternalFrameInitiator extends Initiator implements InternalFrameListener {
    private final StatelessTriggerECD internalFrameActivatedTriggerECD;
    private final StatelessTriggerECD internalFrameClosedTriggerECD;
    private final StatelessTriggerECD internalFrameClosingTriggerECD;
    private final StatelessTriggerECD internalFrameDeactivatedTriggerECD;
    private final StatelessTriggerECD internalFrameDeiconifiedTriggerECD;
    private final StatelessTriggerECD internalFrameIconifiedTriggerECD;
    private final StatelessTriggerECD internalFrameOpenedTriggerECD;

    public InternalFrameInitiator(
            String name,
            StatelessTriggerECD internalFrameActivatedTriggerECD,
            StatelessTriggerECD internalFrameClosedTriggerECD,
            StatelessTriggerECD internalFrameClosingTriggerECD,
            StatelessTriggerECD internalFrameDeactivatedTriggerECD,
            StatelessTriggerECD internalFrameDeiconifiedTriggerECD,
            StatelessTriggerECD internalFrameIconifiedTriggerECD,
            StatelessTriggerECD internalFrameOpenedTriggerECD) {
        super("InternalFrameInitiator[" + name + "]", EventChannelDescriptionUtil.create(new EventChannelDescription[] {
            internalFrameActivatedTriggerECD,
            internalFrameClosedTriggerECD,
            internalFrameClosingTriggerECD,
            internalFrameDeactivatedTriggerECD,
            internalFrameDeiconifiedTriggerECD,
            internalFrameIconifiedTriggerECD,
            internalFrameOpenedTriggerECD
        }));

        this.internalFrameActivatedTriggerECD = internalFrameActivatedTriggerECD;
        this.internalFrameClosedTriggerECD = internalFrameClosedTriggerECD;
        this.internalFrameClosingTriggerECD = internalFrameClosingTriggerECD;
        this.internalFrameDeactivatedTriggerECD = internalFrameDeactivatedTriggerECD;
        this.internalFrameDeiconifiedTriggerECD = internalFrameDeiconifiedTriggerECD;
        this.internalFrameIconifiedTriggerECD = internalFrameIconifiedTriggerECD;
        this.internalFrameOpenedTriggerECD = internalFrameOpenedTriggerECD;
    }

    @Override
    public final void internalFrameActivated(InternalFrameEvent e) {
        if (internalFrameActivatedTriggerECD != null) {
            trigger(internalFrameActivatedTriggerECD);
        }
    }

    @Override
    public final void internalFrameClosed(InternalFrameEvent e) {
        if (internalFrameClosedTriggerECD != null) {
            trigger(internalFrameClosedTriggerECD);
        }
    }

    @Override
    public final void internalFrameClosing(InternalFrameEvent e) {
        if (internalFrameClosingTriggerECD != null) {
            trigger(internalFrameClosingTriggerECD);
        }
    }

    @Override
    public final void internalFrameDeactivated(InternalFrameEvent e) {
        if (internalFrameDeactivatedTriggerECD != null) {
            trigger(internalFrameDeactivatedTriggerECD);
        }
    }

    @Override
    public final void internalFrameDeiconified(InternalFrameEvent e) {
        if (internalFrameDeiconifiedTriggerECD != null) {
            trigger(internalFrameDeiconifiedTriggerECD);
        }
    }

    @Override
    public final void internalFrameIconified(InternalFrameEvent e) {
        if (internalFrameIconifiedTriggerECD != null) {
            trigger(internalFrameIconifiedTriggerECD);
        }
    }

    @Override
    public final void internalFrameOpened(InternalFrameEvent e) {
        if (internalFrameOpenedTriggerECD != null) {
            trigger(internalFrameOpenedTriggerECD);
        }
    }
}
