/*
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
package tfw.value;

import tfw.check.Argument;


/**
 * A utility for checking the type of a value
 */
public class CheckType
{
    /**
     * Checks the type of a value and throws a {@link CodecException} if
     * the value is not assignable to the specified type.
     * @param value The value to be checked.
     * @param type The expected type of the value.
     * @throws CodecException If the specified value is not assignable to the
     * specified type.
     */
    public static void checkValueType(Object value, Class type)
        throws CodecException
    {
        Argument.assertNotNull(type, "type");

        if (!(type.isInstance(value)))
        {
            throw new CodecException("The value is of type '" +
                value.getClass().getName() +
                "' and is not assignable to type '" + type.getName() + "'");
        }
    }
}
