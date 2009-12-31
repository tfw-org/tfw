/*
 * Created on Nov 24, 2005
 *
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
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Converter;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.ecd.StringECD;

public class SetStateTest extends TestCase
{
    /**
     * Verifies that an exception is thrown if the component attempts to set an
     * event channel twice in the same state change cyecle.
     */
    public void testDoubleSet()
    {
        RootFactory rf = new RootFactory();
        StringECD ecd = new StringECD("myECD");
        rf.addEventChannel(ecd);
        TestTransactionExceptionHandler exceptionHandler = new TestTransactionExceptionHandler();
        rf.setTransactionExceptionHandler(exceptionHandler);
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("Test", queue);
        root.add(new DoubleSetConverter(ecd));
        Initiator initiator = new Initiator("MyInitiator", ecd);
        root.add(initiator);
        initiator.set(ecd, "hello");
        queue.waitTilEmpty();

        assertNotNull("Double set failed to throw exception",
                exceptionHandler.exp);
    }

    private class DoubleSetConverter extends Converter
    {
        private final StringECD ecd;

        public DoubleSetConverter(StringECD ecd)
        {
            super("DoubleSetConverter", new StringECD[] { ecd },
                    new StringECD[] { ecd });
            this.ecd = ecd;
        }

        protected void convert()
        {
            set(ecd, "value");
            // Attempt to set the same event channel twice...
            set(ecd, "newValue");
        }
    }
}
