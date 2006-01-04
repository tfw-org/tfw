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

import java.io.Serializable;

/**
 * 
 */
public abstract class ValueConstraint implements Serializable {
	/** The string used to represent a value which complies with the constraint. */
	public static final String VALID = "Valid";
 
    /**
     * Checks the value against the constraint and throws an exception if it
     * does not comply.
     *
     * @param value The value to be checked.
     * @throws ValueException if the value does not comply with the constraint.
     */
    public final void checkValue(Object value) throws ValueException
    {
        String valueCompliance = getValueCompliance(value);

        if (valueCompliance != VALID)
        {
            throw new ValueException(valueCompliance);
        }
    }

	/**
	 * Returns true if every value which meets the specified constraint
	 * also meets this constraint, otherwise returns false. Note that the
	 * reverse is not necessarily true.
	 *
	 * @param constraint the constraint to be checked.
	 *
	 * @return true if every value which meets the specified constraint
	 * also meets this constraint, otherwise returns false.
	 */
	public abstract boolean isCompatable(ValueConstraint constraint);


	/**
	 * Returns {@link #VALID} if the value complies with the constraint,
	 * otherwise it returns a string indicating why the value does not comply.
	 *
	 * @param value The value to be evaluated.
	 * @return {@link #VALID} if the value complies with the constraint,
	 * otherwise it returns a string indicating why the value does not comply.
	 */
	public abstract String getValueCompliance(Object value);

}
