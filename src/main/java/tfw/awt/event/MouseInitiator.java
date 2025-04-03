package tfw.awt.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import tfw.tsm.EventChannelStateBuffer;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescriptionUtil;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;

public class MouseInitiator extends Initiator implements MouseListener, MouseMotionListener {
    private final IntegerECD xECD;
    private final IntegerECD yECD;
    private final BooleanECD button1PressedECD;
    private final BooleanECD button2PressedECD;
    private final BooleanECD button3PressedECD;
    private final BooleanECD altPressedECD;
    private final BooleanECD controlPressedECD;
    private final BooleanECD shiftPressedECD;
    private final IntegerECD clickCountECD;

    public MouseInitiator(
            String name,
            IntegerECD xECD,
            IntegerECD yECD,
            BooleanECD button1PressedECD,
            BooleanECD button2PressedECD,
            BooleanECD button3PressedECD,
            BooleanECD altPressedECD,
            BooleanECD controlPressedECD,
            BooleanECD shiftPressedECD,
            IntegerECD clickCountECD) {
        super("MouseInitiator[" + name + "]", EventChannelDescriptionUtil.create(new ObjectECD[] {
            xECD,
            yECD,
            button1PressedECD,
            button2PressedECD,
            button3PressedECD,
            altPressedECD,
            controlPressedECD,
            shiftPressedECD,
            clickCountECD
        }));

        this.xECD = xECD;
        this.yECD = yECD;
        this.button1PressedECD = button1PressedECD;
        this.button2PressedECD = button2PressedECD;
        this.button3PressedECD = button3PressedECD;
        this.altPressedECD = altPressedECD;
        this.controlPressedECD = controlPressedECD;
        this.shiftPressedECD = shiftPressedECD;
        this.clickCountECD = clickCountECD;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        send(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Do Nothing...
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Do Nothing...
    }

    @Override
    public void mousePressed(MouseEvent e) {
        send(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        send(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        send(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        send(e);
    }

    private void send(MouseEvent e) {
        EventChannelStateBuffer ecsb = new EventChannelStateBuffer();

        if (xECD != null) {
            ecsb.put(xECD, e.getX());
        }
        if (yECD != null) {
            ecsb.put(yECD, e.getY());
        }
        if (button1PressedECD != null) {
            ecsb.put(
                    button1PressedECD,
                    (e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK);
        }
        if (button2PressedECD != null) {
            ecsb.put(
                    button2PressedECD,
                    (e.getModifiersEx() & MouseEvent.BUTTON2_DOWN_MASK) == MouseEvent.BUTTON2_DOWN_MASK);
        }
        if (button3PressedECD != null) {
            ecsb.put(
                    button3PressedECD,
                    (e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == MouseEvent.BUTTON3_DOWN_MASK);
        }
        if (altPressedECD != null) {
            ecsb.put(altPressedECD, e.isAltDown());
        }
        if (controlPressedECD != null) {
            ecsb.put(controlPressedECD, e.isControlDown());
        }
        if (shiftPressedECD != null) {
            ecsb.put(shiftPressedECD, e.isShiftDown());
        }
        if (clickCountECD != null) {
            ecsb.put(clickCountECD, e.getClickCount());
        }

        set(ecsb.toArray());
    }
}
