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
 * without even the implied warranty of
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
 * A value constraint which constrains the value to a range based on the
 * <code>java.lang.Comparable</code> interface.
 */
public class RangeConstraint extends ClassValueConstraint
{
    private final Comparable min;
    private final Comparable max;
    private final boolean minInclusive;
    private final boolean maxInclusive;

    /**
     * Creates a constraint with the specified attributes.
     *
     * @param valueType the 'type' of valid values.
     * @param min the minimum value.
     * @param max the maximum value.
     * @param minInclusive if true <code>min</code> is a valid value,
     * otherwise it is not valid.
     * @param maxInclusive if true <code>max</code> is a valid value,
     * otherwise it is not valid.
     */
    public RangeConstraint(Class valueType, Comparable min, Comparable max,
        boolean minInclusive, boolean maxInclusive)
    {
        super(valueType);
        Argument.assertNotNull(min, "min");
        Argument.assertNotNull(max, "max");

        if (!Comparable.class.isAssignableFrom(valueType))
        {
            throw new IllegalArgumentException(
                "valueType is not assignable to " + Comparable.class.getName());
        }

        int compare = min.compareTo(max);

        if (compare > 0)
        {
            throw new IllegalArgumentException("min > max not allowed!");
        }

        if ((compare == 0) && (minInclusive == false) &&
                (maxInclusive == false))
        {
            throw new IllegalArgumentException(
                "Empty range, min == max and neither are inclusive");
        }

        this.max = max;
        this.min = min;
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

	/**
	 * Returns {@link #VALID} if the value complies with the constraint,
	 * otherwise it returns a string indicating why the value does not comply.
	 *
	 * @param value The value to be evaluated.
	 * @return {@link #VALID} if the value complies with the constraint,
	 * otherwise it returns a string indicating why the value does not comply.
	 */
    public String getValueCompliance(Object value)
    {
        String str = super.getValueCompliance(value);

        if (str != VALID)
        {
            return str;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("value = '").append(value).append("' is out of range, ");

        int minCompare = this.min.compareTo(value);

        // if value is less than min or equal to min and min is not valid...
        if ((minCompare > 0) || ((minCompare == 0) && !minInclusive))
        {
            sb.append("must be greater than ");

            if (minInclusive)
            {
                sb.append("or equal to ");
            }

            sb.append("'").append(min).append("'");

            return sb.toString();
        }

        int maxCompare = this.max.compareTo(value);

        // if value is greater than max or equal to max and max is not valid...
        if ((maxCompare < 0) || ((maxCompare == 0) && !maxInclusive))
        {
            sb.append("must be less than ");

            if (maxInclusive)
            {
                sb.append("or equal to ");
            }

            sb.append("'").append(max).append("'");

            return sb.toString();
        }
        return VALID;
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
    public boolean isCompatible(ValueConstraint constraint)
    {
        if (constraint == this)
        {
            // if we use a factory to create constraints
            // so that we done have object explosion, this
            // check will work for most cases and be
            // very efficient.
            return true;
        }

        // check constraint type...
        if (!(constraint instanceof RangeConstraint))
        {
            return false;
        }

        // check value type...
        if (!super.isCompatible(constraint))
        {
            return false;
        }

        RangeConstraint rc = (RangeConstraint) constraint;

        int minCompare = this.min.compareTo(rc.min);

        // if this min is greater than rc min...
        if (minCompare > 0)
        {
            return false;
        }

        // if mins are equal...
        if (minCompare == 0)
        {
            // if this min is not inclusive && rc min is inclusive...
            if (!this.minInclusive && rc.minInclusive)
            {
                return false;
            }
        }

        int maxCompare = this.max.compareTo(rc.max);

        // if rc.max > this.max...
        if (maxCompare < 0)
        {
            return false;
        }

        // if maxs are equal...
        if (maxCompare == 0)
        {
            // if this max is not inclusive && rc max is inclusive...
            if (!this.maxInclusive && rc.maxInclusive)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a string representation of the constraint.
     * @return a string representation of the constraint.
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("RangeConstraint[type = ").append(valueType.getName());
        sb.append(", min = ").append(min);
        sb.append(", max = ").append(max);
        sb.append("]");

        return sb.toString();
    }
}
