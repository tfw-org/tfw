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
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.Validator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.RollbackECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.StringRollbackECD;

/**
 * 
 */
public class ValidatorTest extends TestCase
{
    public void testConstruction()
    {
        ObjectECD[] sinks = new ObjectECD[] { new StringECD("Test") };

        try
        {
            new MyValidator(null, sinks, null, null);

            fail("constructor accepted null name");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        try
        {
            new MyValidator("Test", new ObjectECD[] { null }, null, null);

            fail("constructor accepted null element in sink event channels");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        try
        {
            new MyValidator("Test", sinks, new ObjectECD[] { null }, null);

            fail("constructor accepted null element in non-triggering sinks");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        try
        {
            new MyValidator("Test", null, null, null);
            fail("constructor accepted null sink event channels");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }
    }

    private final StringECD eventChannelAECD = new StringECD("ecA");

    private final StringECD eventChannelBECD = new StringECD("ecB");

    public void testValidator()
    {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(eventChannelAECD);
        rf.addEventChannel(eventChannelBECD);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("ValidatorTestRoot", queue);
        Initiator initiator = new Initiator("ValidatorTestInitiator",
                new StringECD[] { eventChannelAECD, eventChannelBECD });
        root.add(initiator);
        MyValidator validator = new MyValidator("TestValidator",
                new StringECD[] { eventChannelAECD },
                new StringECD[] { eventChannelBECD }, null);
        root.add(validator);
        String valueA = "valueA";
        initiator.set(eventChannelAECD, valueA);
        queue.waitTilEmpty();

        assertEquals("validateState() called with wrong channelA state", null,
                validator.channelA);
        assertEquals("validateState() called with wrong channelB state", null,
                validator.channelB);
        assertEquals("debugValdateState() called with wrong channelA state",
                valueA, validator.debugChannelA);
        assertEquals("debugValdateState() called with wrong channelB state",
                null, validator.debugChannelB);

        validator.reset();
        valueA = "newValueA";
        String valueB = "valueB";
        initiator.set(eventChannelBECD, valueB);
        initiator.set(eventChannelAECD, valueA);
        queue.waitTilEmpty();
        assertEquals("validateState() called with wrong channelA state",
                valueA, validator.channelA);
        assertEquals("validateState() called with wrong channelB state",
                valueB, validator.channelB);
        assertEquals("debugValdateState() called with wrong channelA state",
                null, validator.debugChannelA);
        assertEquals("debugValdateState() called with wrong channelB state",
                null, validator.debugChannelB);
    }

    public void testTriggeredValidation()
    {
        StatelessTriggerECD trigger = new StatelessTriggerECD("trigger");
        final IntegerECD minECD = new IntegerECD("min");
        final IntegerECD maxECD = new IntegerECD("max");
        final StringRollbackECD error = new StringRollbackECD("error");
        RootFactory rf = new RootFactory();
        rf.addEventChannel(trigger);
        rf.addEventChannel(error);
        rf.addEventChannel(minECD, new Integer(0));
        rf.addEventChannel(maxECD, new Integer(1));
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("Test", queue);
        Initiator initiator = new Initiator("Initiator",
                new EventChannelDescription[] { trigger, minECD, maxECD });
        root.add(initiator);
        root.add(new Validator("TestValidator", trigger, new ObjectECD[] {
                minECD, maxECD }, new RollbackECD[] {error})
        {
            protected void validateState()
            {
                int min = ((Integer) get(minECD)).intValue();
                int max = ((Integer) get(maxECD)).intValue();
                if (min > max)
                {
                    rollback(error, "min must be less than or equal to max");
                }
            }

        });
        
        ErrorHandler errorHandler = new ErrorHandler(error);
        root.add(errorHandler);
        queue.waitTilEmpty();
        assertNull("Initialization failed", errorHandler.errorMsg);
        
        initiator.set(minECD, new Integer(3));
        queue.waitTilEmpty();
        assertNull("Non triggered event cause validation", errorHandler.errorMsg);
        
        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertNotNull("Trigger failed to cause validation", errorHandler.errorMsg);
        
        errorHandler.errorMsg = null;
        initiator.set(maxECD, new Integer(4));
        queue.waitTilEmpty();
        assertNull("Non triggered event cause validation", errorHandler.errorMsg);
    }

    private class ErrorHandler extends Commit
    {
        private final StringRollbackECD errorECD;

        String errorMsg = null;

        public ErrorHandler(StringRollbackECD errorECD)
        {
            super("ErrorHandler", new ObjectECD[] { errorECD });
            this.errorECD = errorECD;
        }

        protected void commit()
        {
            this.errorMsg = (String) get(this.errorECD);
        }
    }

    private class MyValidator extends Validator
    {
        private String channelA = null;

        private String channelB = null;

        private String debugChannelA = null;

        private String debugChannelB = null;

        public MyValidator(String name, ObjectECD[] triggeringSinks,
                ObjectECD[] nonTriggeringSinks, RollbackECD[] initiators)
        {
            super(name, triggeringSinks, nonTriggeringSinks, initiators);
        }

        protected void validateState()
        {
            channelA = (String) get(eventChannelAECD);
            channelB = (String) get(eventChannelBECD);
        }

        protected void debugValidateState()
        {
            debugChannelA = (String) get(eventChannelAECD);
            debugChannelB = (String) get(eventChannelBECD);
        }

        public void reset()
        {
            this.channelA = null;
            this.channelB = null;
            this.debugChannelA = null;
            this.debugChannelB = null;
        }
    }
}
