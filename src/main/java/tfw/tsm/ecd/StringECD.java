package tfw.tsm.ecd;

import tfw.value.ClassValueConstraint;
import tfw.value.SetConstraint;

/**
 * A <code>java.lang.String</code> event channel description
 */
public class StringECD extends ObjectECD
{
    /**
     * Creates an event channel description with the specified name.
     * 
     * @param name
     *            the name of the event channel.
     */
    public StringECD(String name)
    {
        super(name, ClassValueConstraint.STRING);
    }

    /**
     * Creates an event channel description with the specified name and an
     * explicit set of legal values.
     * 
     * @param name
     *            the name of the event channel.
     * @param validValues
     *            This list of legal values for the event channel.
     */
    public StringECD(String name, String[] validValues)
    {
        super(name, new SetConstraint(validValues));
    }
}
