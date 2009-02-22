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

import java.util.HashMap;
import java.util.Map;

import tfw.check.Argument;
import tfw.immutable.ila.objectila.ObjectIla;



/**
 * The base class for a constraint where the value must be of a specifid type. 
 * If a value is assignable to the class type of the 
 * <code>ClassValueConstraint</code> then it is valid, otherwise it is 
 * not valid.
 */
public class ClassValueConstraint extends ValueConstraint
{
    /** A value constraint which allows any object. */
    public static final ClassValueConstraint OBJECT = new ClassValueConstraint(Object.class);
    
    /** A value constraint which allows an {@link ObjectIla} value. */
    public static final ClassValueConstraint OBJECTILA = new ClassValueConstraint(ObjectIla.class);

    /** A String value constraint which allows any string. */
    public static final ClassValueConstraint STRING = new ClassValueConstraint(String.class);

    /** A Boolean value constraint. */
    public static final ClassValueConstraint BOOLEAN = new ClassValueConstraint(Boolean.class);
    
	/** An Integer value constraint that allows any integer value. */
	public static final ClassValueConstraint INTEGER = new ClassValueConstraint(Integer.class);

	/** An Float value constraint that allows any float value. */
	public static final ClassValueConstraint FLOAT = new ClassValueConstraint(Float.class);

	/** An Double value constraint that allows any double value. */
	public static final ClassValueConstraint DOUBLE = new ClassValueConstraint(Double.class);

	/** A Long value constraint that allows any long value. */
	public static final ClassValueConstraint LONG = new ClassValueConstraint(Long.class);

	/** An Character value constraint that allows any character value. */
	public static final ClassValueConstraint CHARACTER = new ClassValueConstraint(Character.class);
	
	/** An Short value constraint that allows any short value. */
	public static final ClassValueConstraint SHORT = new ClassValueConstraint(Short.class);
	
	/** An Byte value constraint that allows any short value. */
	public static final ClassValueConstraint BYTE = new ClassValueConstraint(Byte.class);

    /** The string used to represent a value which complies with the constraint. */
    public static final String VALID = "Valid";
    
    private static final Map<Class<?>, ClassValueConstraint> constraints =
    	getInitialConstraints();
    
    /** The class of the value. */
    protected final Class<?> valueType;

    /**
     * Constructs a constraint
     * @param valueType the type for this constraint.
     */
    protected ClassValueConstraint(Class<?> valueType)
    {
        Argument.assertNotNull(valueType, "valueType");
        this.valueType = valueType;
    }

    private static Map<Class<?>, ClassValueConstraint> getInitialConstraints()
    {
        HashMap<Class<?>, ClassValueConstraint> map =
        	new HashMap<Class<?>, ClassValueConstraint>();
        
        map.put(BOOLEAN.valueType, BOOLEAN);
        map.put(OBJECT.valueType, OBJECT);
        map.put(STRING.valueType, STRING);

        return map;
    }

    /**
     * Returns an instance of a value constraint based on the specified class.
     * @param valueType the class for the value constraint.
     * @return an instance of a value constraint based on the specified class.
     */
    public static ClassValueConstraint getInstance(Class<?> valueType)
    {
        ClassValueConstraint constraint = constraints.get(valueType);

        if (constraint == null)
        {
            constraint = new ClassValueConstraint(valueType);
            constraints.put(valueType, constraint);
        }

        return constraint;
    }

    /**
     * Returns <code>true</code> if the specified value complies with the
     * constraint, otherwise returns <code>false</code>.
     *
     * @param value the value to be checked.
     * @return <code>true</code> if the specified value complies with the
     * constraint, otherwise returns <code>false</code>.
     */
    public boolean isValid(Object value)
    {
        String valueCompliance = getValueCompliance(value);

        if (valueCompliance != VALID)
        {
            return false;
        }

        return true;
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
        if (valueType.isInstance(value))
        {
            return VALID;
        }

        if (value == null)
        {
            return "value == null does not meet the constraints on this value";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("The value, of type '");
        sb.append(value.getClass().getName());
        sb.append("', is not assignable to type '");
        sb.append(valueType.getName());
        sb.append("'.");

        return sb.toString();
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
        if (constraint instanceof ClassValueConstraint)
        {
            return valueType.equals(((ClassValueConstraint) constraint).valueType);
        }

        return false;
    }

    /**
     * Returns a string representation of this constraint.
     * @return a string representation of this constraint.
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("ValueConstraint[");
        sb.append("type = ").append(valueType.getName());
        sb.append("]");

        return sb.toString();
    }
}
