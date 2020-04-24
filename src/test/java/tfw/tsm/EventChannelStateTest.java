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
package tfw.tsm;

import tfw.tsm.EventChannelState;
import tfw.tsm.ecd.StringECD;

import junit.framework.TestCase;

/**
 *
 */
public class EventChannelStateTest extends TestCase {
	public void testEventChannelState() throws Exception {
		StringECD stringECD = new StringECD("myChannel");
		String value = "Hello World";

		try {
			new EventChannelState(null, value);
			fail("constructor accepted null ecd");
		} catch (IllegalArgumentException expected) {
			//System.out.println(expected);
		}

		EventChannelState state = new EventChannelState(stringECD, value);
		assertEquals("returned wrong ecd", stringECD.getEventChannelName(), state.getEventChannelName());
		assertEquals("returned wrong value", value, state.getState());
	}

	public void testEquals() throws Exception {
		StringECD stringECD = new StringECD("myChannel");
		String value = "Hello World";

		EventChannelState state1 = new EventChannelState(stringECD, value);
		EventChannelState state2 = new EventChannelState(stringECD, value);
		assertEquals("equivalent values not equal", state1, state2);
		assertEquals(
			"equivalent values different hashCodes",
			state1.hashCode(),
			state2.hashCode());
		assertFalse("null is equal", state1.equals(null));
		assertFalse("wrong type is equal", state1.equals(new Object()));

		state2 = new EventChannelState(new StringECD("different"), value);
		assertFalse("different ecd is equal", state1.equals(state2));

		state2 = new EventChannelState(stringECD, "different");
		assertFalse("different ecd is equal", state1.equals(state2));
	}
}
