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
 * A constaint where the only legal value is <code>null</code>.
 */
public class NullConstaint implements ValueConstraint
{
	public static final NullConstaint INSTANCE = new NullConstaint();
	private NullConstaint(){}
	
    /* (non-Javadoc)
     * @see co2.value.Constraint#isCompatable(co2.value.Constraint)
     */
    public boolean isCompatable(ValueConstraint constraint)
    {
        Argument.assertNotNull(constraint, "constraint");

        return (constraint instanceof NullConstaint);
    }

    /* (non-Javadoc)
     * @see co2.value.Constraint#getValueCompliance(java.lang.Object)
     */
    public String getValueCompliance(Object value)
    {
        if (value == null)
        {
            return VALID;
        }
        else
        {
            return "Trigger event channels have no values, so no value complies with this constraint";
        }
    }

    /* (non-Javadoc)
     * @see co2.value.Constraint#checkValue(java.lang.Object)
     */
    public void checkValue(Object value) throws ValueException
    {
        if (value != null)
        {
            throw new ValueException(getValueCompliance(value));
        }
    }
}
