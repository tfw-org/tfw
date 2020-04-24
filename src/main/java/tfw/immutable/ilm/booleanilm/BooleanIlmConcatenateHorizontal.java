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
package tfw.immutable.ilm.booleanilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class BooleanIlmConcatenateHorizontal
{
    private BooleanIlmConcatenateHorizontal() {}

    public static BooleanIlm create(BooleanIlm leftIlm, BooleanIlm rightIlm)
    {
    	Argument.assertNotNull(leftIlm, "leftIlm");
    	Argument.assertNotNull(rightIlm, "rightIlm");
    	Argument.assertEquals(leftIlm.height(), rightIlm.height(),
    		"leftIlm.height()", "rightIlm.height()");

		return new MyBooleanIlm(leftIlm, rightIlm);
    }

    private static class MyBooleanIlm extends AbstractBooleanIlm
    	implements ImmutableProxy
    {
		private BooleanIlm leftIlm;
		private BooleanIlm rightIlm;

		MyBooleanIlm(BooleanIlm leftIlm, BooleanIlm rightIlm)
		{
		    super(leftIlm.width() + rightIlm.width(), leftIlm.height());
		    
		    this.leftIlm = leftIlm;
		    this.rightIlm = rightIlm;
		}
		
		protected void toArrayImpl(boolean[][] array, int rowOffset,
			int columnOffset, long rowStart, long columnStart,
			int width, int height) throws DataInvalidException
		{
		    if (columnStart + width <= leftIlm.width())
		    {
				leftIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart, width, height);
		    }
		    else if (columnStart >= leftIlm.width())
			{
				rightIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart - leftIlm.width(), width, height);
		    }
		    else
			{
				int firstamount = (int)(leftIlm.width() - columnStart);
				leftIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart, firstamount, height);
				rightIlm.toArray(array, rowOffset, columnOffset + firstamount,
					rowStart, 0, width - firstamount, height);
	    	}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "BooleanIlmConcatenateHorizontal");
			map.put("leftIlm", getImmutableInfo(leftIlm));
			map.put("rightIlm", getImmutableInfo(rightIlm));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}