/*
 * Created on Jul 7, 2005
 *
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * witout even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.component;

import tfw.check.Argument;
import tfw.tsm.Synchronizer;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.StringRollbackECD;
import tfw.value.CodecException;
import tfw.value.ValueCodec;

/**
 * A general component for converting between an object and it's string
 * representation.
 */
public class ObjectStringSynchronizer extends Synchronizer
{
    private final String valueName;

    private final ObjectECD objectECD;

    private final StringECD stringECD;

    private final StringRollbackECD errorECD;

    private final ValueCodec codec;

    /**
     * Creates a synchronizer with the specified attributes.
     * 
     * @param synchronizerName
     *            The name of the synchronizer.
     * @param valueName
     *            The name of the value to be used in formulating error
     *            messages.
     * @param objectECD
     *            The object event channel description
     * @param stringECD
     *            The string event channel description.
     * @param errorECD
     *            The rollback error event channel. If an error occurs during
     *            conversion a rollback will be performed and a message will be
     *            sent out on this event channel.
     * @param codec
     *            The codec to be used in the conversion.
     */
    public ObjectStringSynchronizer(String synchronizerName, String valueName,
            ObjectECD objectECD, StringECD stringECD,
            StringRollbackECD errorECD, ValueCodec codec)
    {
        super(synchronizerName, check("objectECD", objectECD), check(
                "stringECD", stringECD), null, check("errorECD", errorECD));
        Argument.assertNotNull(valueName, "valueName");
        Argument.assertNotNull(codec, "codec");
        this.valueName = valueName;
        this.objectECD = objectECD;
        this.stringECD = stringECD;
        this.errorECD = errorECD;
        this.codec = codec;
    }

    private static ObjectECD[] check(String name,
            ObjectECD ecd)
    {
        if (ecd == null)
        {
            throw new IllegalArgumentException(name + " == null not allowed");
        }
        return new ObjectECD[] { ecd };
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.Synchronizer#convertAToB()
     */
    protected void convertAToB()
    {
        try
        {
            set(stringECD, codec.encode(get(objectECD)));
        }
        catch (CodecException e)
        {
            rollback(errorECD, get(objectECD) + " is not valid");
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.Synchronizer#convertBToA()
     */
    protected void convertBToA()
    {
        try
        {
            set(objectECD, codec.decode((String) get(stringECD)));
        }
        catch (CodecException e)
        {
            rollback(errorECD, "'" + get(stringECD) + "' is not valid for "
                    + valueName);
        }

    }
}
