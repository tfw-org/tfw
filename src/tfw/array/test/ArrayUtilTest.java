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
package tfw.array.test;

import junit.framework.TestCase;
import tfw.array.ArrayUtil;
/**
 *
 */
public class ArrayUtilTest extends TestCase {
	public void testUnorderedEquals() {
		Object[] array1 = new Object[0];
		Object[] array2 = new Object[0];

		assertTrue("empty arrays", ArrayUtil.unorderedEquals(array1, array2));
		assertFalse("empty & null", ArrayUtil.unorderedEquals(array1, null));
		assertFalse("null & empty", ArrayUtil.unorderedEquals(null, array2));

		array1 = new Object[] { null };
		assertFalse("null element & empty", ArrayUtil.unorderedEquals(array1, array2));
		array2 = new Object[] { new Object() };
		assertFalse("null element & object element", ArrayUtil.unorderedEquals(array1, array2));
		
		Object o1 = new Object();
		Object o2 = new Object();
		array1 = new Object[]{ o1, o2 };
		array2 = new Object[]{ o2, o1 };
		assertTrue("same elements different order", ArrayUtil.unorderedEquals(array1, array2));
		array1 = new Object[]{ o1, o2, o1 };
		array2 = new Object[]{ o2, o1, o2 };
		assertFalse("different numbers of different elements", ArrayUtil.unorderedEquals(array1, array2));
		array1 = new Object[]{ o1, o2, o1 };
		array2 = new Object[]{ o2, o1, o1 };
		assertTrue("redunant elements in different orders", ArrayUtil.unorderedEquals(array1, array2));
		
	}
}
