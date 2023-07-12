package tfw.demo;

import java.awt.Color;
import tfw.awt.ecd.ColorECD;
import tfw.tsm.Synchronizer;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.IntegerConstraint;

/**
 * Converts back and forth between red, green, and blue <code>
 * java.lang.Integer</code> values and <code>java.awt.Color</code>.
 */
public class IntegerColorConverter extends Synchronizer {
    /**
     * The value constraint for RGB event channels.
     */
    public static final IntegerConstraint RGB_CONSTRAINT = RGBConstraint.CONSTRAINT;

    private final RedGreenBlueECD redInteger;
    private final RedGreenBlueECD greenInteger;
    private final RedGreenBlueECD blueInteger;
    private final ColorECD color;

    /**
     * Creates an instance of <code>IntegerColorConverter</code>.
     *
     * @param name the name of this <code>LeafNode</code>
     * @param redInteger the name of the red event channel, which will
     * carry <code>java.lang.Integer</code> events.
     * @param greenInteger the name of the green event channel, which will
     * carry <code>java.lang.Integer</code> events.
     * @param blueInteger the name of the blue event channel, which will
     * carry <code>java.lang.Integer</code> events.
     * @param color the name of the color event channel, which will
     * carry </code>java.awt.Color</code> events.
     */
    public IntegerColorConverter(
            String name,
            RedGreenBlueECD redInteger,
            RedGreenBlueECD greenInteger,
            RedGreenBlueECD blueInteger,
            ColorECD color) {
        super(name, createSinks(redInteger, blueInteger, greenInteger), createSources(color), null, null);
        this.redInteger = redInteger;
        this.greenInteger = greenInteger;
        this.blueInteger = blueInteger;
        this.color = color;

        //		System.out.println("IntegerColorCovernter["+redInteger
        //				+", "+greenInteger+", "+blueInteger+
        //				", "+color+"]");
    }

    private static ObjectECD[] createSinks(RedGreenBlueECD red, RedGreenBlueECD green, RedGreenBlueECD blue) {
        return new ObjectECD[] {red, green, blue};
    }

    private static ObjectECD[] createSources(ColorECD color) {
        return new ObjectECD[] {color};
    }

    protected final void convertAToB() {
        //		System.out.println("IntegerColorConverter.convertAToB() = " + get());
        int red = ((Integer) get(redInteger)).intValue();
        int green = ((Integer) get(greenInteger)).intValue();
        int blue = ((Integer) get(blueInteger)).intValue();
        set(color, new Color(red, green, blue));
    }

    protected void debugConvertAToB() {
        //		System.out.println("IntegerColorConverter.debugConvertAToB() = " + get());
    }

    protected void debugConvertBToA() {
        //		System.out.println("IntegerColorConverter.debugConvertBToA() = " + get());
    }

    protected final void convertBToA() {
        //		System.out.println("IntegerColorConverter.convertBToA() = " + get());
        Color c = (Color) get(color);
        set(redInteger, c.getRed());
        set(greenInteger, c.getGreen());
        set(blueInteger, c.getBlue());
    }
}
