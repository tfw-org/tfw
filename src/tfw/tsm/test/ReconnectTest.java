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
package tfw.tsm.test;

import junit.framework.TestCase;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Branch;
import tfw.tsm.RootFactory;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StringECD;


/**
 *
 */
public class ReconnectTest extends TestCase
{
    public void testReconnectSynchronizer() throws Exception
    {
        EventChannelDescription a1Port = new StringECD("a1");
        EventChannelDescription a2Port = new StringECD("a1");
        RootFactory rf = new RootFactory();
        rf.addTerminator(a1Port, null);
        rf.addTerminator(a2Port, null);

        Branch branch = rf.create("test", new BasicTransactionQueue());

    }
}
