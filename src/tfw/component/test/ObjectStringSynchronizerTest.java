/*
 * Created on Jul 7, 2005
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
package tfw.component.test;

import junit.framework.TestCase;
import tfw.component.ObjectStringSynchronizer;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.StringRollbackECD;
import tfw.value.IntegerCodec;
import tfw.value.ValueCodec;

/**
 */
public class ObjectStringSynchronizerTest extends TestCase
{
    public void testConstruction()
    {
        String componentName = "myComponent";
        String valueName = "myValue";
        IntegerECD integerECD = new IntegerECD("Integer");
        StringECD stringECD = new StringECD("String");
        StringRollbackECD errorECD = new StringRollbackECD("Error");
        ValueCodec codec = IntegerCodec.getInstance();
        try
        {
            new ObjectStringSynchronizer(null, valueName, integerECD,
                    stringECD, errorECD, codec);
            fail("Constructor accepted null component name");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
        try
        {
            new ObjectStringSynchronizer(componentName, null, integerECD,
                    stringECD, errorECD, codec);
            fail("Constructor accepted null value name");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
        try
        {
            new ObjectStringSynchronizer(componentName, valueName, null,
                    stringECD, errorECD, codec);
            fail("Constructor accepted null event channel description");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
        try
        {
            new ObjectStringSynchronizer(componentName, valueName, integerECD,
                    null, errorECD, codec);
            fail("Constructor accepted null stringECD");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
        try
        {
            new ObjectStringSynchronizer(componentName, valueName, integerECD,
                    stringECD, null, codec);
            fail("Constructor accepted null errorECD");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
        
        try
        {
            new ObjectStringSynchronizer(componentName, valueName, integerECD,
                    stringECD, errorECD, null);
            fail("Constructor accepted null codec");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
    }

    public void testBehavior()
    {
        IntegerECD integerECD = new IntegerECD("Integer");
        StringECD stringECD = new StringECD("String");
        StringRollbackECD errorECD = new StringRollbackECD("Error");
        RootFactory rf = new RootFactory();
        rf.addEventChannel(integerECD);
        rf.addEventChannel(stringECD);
        rf.addEventChannel(errorECD);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("Test", queue);

        ValueCodec codec = IntegerCodec.getInstance();
        Initiator integerInitiator = new Initiator("integer", integerECD);
        Initiator stringInitiator = new Initiator("string", stringECD);
        ObjectStringSynchronizer oss = new ObjectStringSynchronizer("sync",
                "value", integerECD, stringECD, errorECD, codec);
        IntegerCommit ic = new IntegerCommit(integerECD);
        StringCommit sc = new StringCommit(stringECD);
        ErrorCommit ec = new ErrorCommit(errorECD);

        root.add(integerInitiator);
        root.add(stringInitiator);
        root.add(oss);
        root.add(ic);
        root.add(sc);
        root.add(ec);

        String strValue = "1";
        Integer intValue = new Integer(1);

        // Test string to integer conversion...
        stringInitiator.set(stringECD, strValue);
        queue.waitTilEmpty();
        assertEquals("String to integer value wrong", intValue, ic.value);

        // Test integer to string conversion...
        strValue = "8";
        intValue = new Integer(8);
        integerInitiator.set(integerECD, intValue);
        queue.waitTilEmpty();
        assertEquals("Integer to string value wrong", strValue, sc.value);

        // Test invalid string conversion...
        ec.value = null;
        stringInitiator.set(stringECD, "laksjdf");
        queue.waitTilEmpty();
        assertNotNull("String to integer error not found", ec.value);

        //        // Test invalid integer conversion...
        //        ec.value = null;
        //        integerInitiator.set(integerECD, "laksjdf");
        //        queue.waitTilEmpty();
        //        assertNotNull("String to integer error not found", ec.value);
    }

    private class IntegerCommit extends Commit
    {
        private final IntegerECD integerECD;

        private Integer value = null;

        public IntegerCommit(IntegerECD integerECD)
        {
            super("IntegerCommit", new IntegerECD[] { integerECD });
            this.integerECD = integerECD;
        }

        protected void commit()
        {
            this.value = (Integer) get(integerECD);
        }
    }

    private class StringCommit extends Commit
    {
        private final StringECD stringECD;

        private String value = null;

        public StringCommit(StringECD stringECD)
        {
            super(stringECD.getEventChannelName() + "Commit",
                    new StringECD[] { stringECD });
            this.stringECD = stringECD;
        }

        protected void commit()
        {
            this.value = (String) get(stringECD);
        }
    }

    private class ErrorCommit extends Commit
    {
        private final StringRollbackECD errorECD;

        private String value = null;

        public ErrorCommit(StringRollbackECD errorECD)
        {
            super(errorECD.getEventChannelName() + "Commit",
                    new StringRollbackECD[] { errorECD });
            this.errorECD = errorECD;
        }

        protected void commit()
        {
            this.value = (String) get(errorECD);
        }
    }
}
