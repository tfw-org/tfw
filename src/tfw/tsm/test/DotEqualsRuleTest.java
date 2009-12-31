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
package tfw.tsm.test;

import junit.framework.TestCase;
import tfw.tsm.DotEqualsRule;
import tfw.tsm.StateChangeRule;

/**
 * 
 */
public class DotEqualsRuleTest extends TestCase {
	public void testIsChange(){
		StateChangeRule rule = DotEqualsRule.RULE;
		Object currentState = new Object();
		Object newState = new Object();
		assertTrue("Different state",rule.isChange(currentState, newState));
		assertFalse("Same state",rule.isChange(currentState, currentState));
		assertTrue("Null currentState",rule.isChange(null, newState));
		try{
			rule.isChange(currentState, null);
			fail("isChange() accepted null new state");
		}catch (IllegalArgumentException expected){
			//System.out.println(expected);
		}
	}
}
