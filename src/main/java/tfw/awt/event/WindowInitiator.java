package tfw.awt.event;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.EventChannelDescriptionUtil;
import tfw.tsm.ecd.StatelessTriggerECD;

public class WindowInitiator extends Initiator implements WindowListener {
    private final StatelessTriggerECD windowActivatedTriggerECD;
    private final StatelessTriggerECD windowClosedTriggerECD;
    private final StatelessTriggerECD windowClosingTriggerECD;
    private final StatelessTriggerECD windowDeactivatedTriggerECD;
    private final StatelessTriggerECD windowDeiconifiedTriggerECD;
    private final StatelessTriggerECD windowIconifiedTriggerECD;
    private final StatelessTriggerECD windowOpenedTriggerECD;

    public WindowInitiator(
            String name,
            StatelessTriggerECD windowActivatedTriggerECD,
            StatelessTriggerECD windowClosedTriggerECD,
            StatelessTriggerECD windowClosingTriggerECD,
            StatelessTriggerECD windowDeactivatedTriggerECD,
            StatelessTriggerECD windowDeiconifiedTriggerECD,
            StatelessTriggerECD windowIconifiedTriggerECD,
            StatelessTriggerECD windowOpenedTriggerECD) {
        super("WindowInitiator[" + name + "]", EventChannelDescriptionUtil.create(new EventChannelDescription[] {
            windowActivatedTriggerECD, windowClosedTriggerECD,
            windowClosingTriggerECD, windowDeactivatedTriggerECD,
            windowDeiconifiedTriggerECD, windowIconifiedTriggerECD,
            windowOpenedTriggerECD
        }));

        this.windowActivatedTriggerECD = windowActivatedTriggerECD;
        this.windowClosedTriggerECD = windowClosedTriggerECD;
        this.windowClosingTriggerECD = windowClosingTriggerECD;
        this.windowDeactivatedTriggerECD = windowDeactivatedTriggerECD;
        this.windowDeiconifiedTriggerECD = windowDeiconifiedTriggerECD;
        this.windowIconifiedTriggerECD = windowIconifiedTriggerECD;
        this.windowOpenedTriggerECD = windowOpenedTriggerECD;
    }

    @Override
    public final void windowActivated(WindowEvent e) {
        if (windowActivatedTriggerECD != null) {
            trigger(windowActivatedTriggerECD);
        }
    }

    @Override
    public final void windowClosed(WindowEvent e) {
        if (windowClosedTriggerECD != null) {
            trigger(windowClosedTriggerECD);
        }
    }

    @Override
    public final void windowClosing(WindowEvent e) {
        if (windowClosingTriggerECD != null) {
            trigger(windowClosingTriggerECD);
        }
    }

    @Override
    public final void windowDeactivated(WindowEvent e) {
        if (windowDeactivatedTriggerECD != null) {
            trigger(windowDeactivatedTriggerECD);
        }
    }

    @Override
    public final void windowDeiconified(WindowEvent e) {
        if (windowDeiconifiedTriggerECD != null) {
            trigger(windowDeiconifiedTriggerECD);
        }
    }

    @Override
    public final void windowIconified(WindowEvent e) {
        if (windowIconifiedTriggerECD != null) {
            trigger(windowIconifiedTriggerECD);
        }
    }

    @Override
    public final void windowOpened(WindowEvent e) {
        if (windowOpenedTriggerECD != null) {
            trigger(windowOpenedTriggerECD);
        }
    }
}
