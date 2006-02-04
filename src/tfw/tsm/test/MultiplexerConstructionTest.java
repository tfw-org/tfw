/*
 * Created on Feb 3, 2006
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
package tfw.tsm.test;

import junit.framework.TestCase;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.MultiplexedBranch;
import tfw.tsm.MultiplexedBranchFactory;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class MultiplexerConstructionTest extends TestCase
{
    private static final StringECD VALUE_ECD = new StringECD("value");

    private static final ObjectIlaECD MULTIVALUE_ECD = new ObjectIlaECD(
            "multiValue");

    private static final StatelessTriggerECD TRIGGER_ECD = new StatelessTriggerECD(
            "trigger");

    private final ValueCommit vc1 = new ValueCommit("vc1");

    private final ValueCommit vc2 = new ValueCommit("vc2");

    public void testDynamicConstruction()
    {
        String value = "bob";
        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
        mbf.addMultiplexer(VALUE_ECD, MULTIVALUE_ECD);
        MultiplexedBranch multiBranch = mbf.create("multiBranch");
        multiBranch.add(vc1, 0);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        RootFactory rf = new RootFactory();
        rf.addEventChannel(MULTIVALUE_ECD, ObjectIlaFromArray
                .create(new String[] { value }));
        rf.addEventChannel(this.TRIGGER_ECD);
        Root root = rf.create("Test", queue);

        Initiator initiator = new Initiator("trigger", TRIGGER_ECD);
        root.add(multiBranch);
        root.add(initiator);
        root.add(new ComponentSwitchCommit(multiBranch));
        queue.waitTilEmpty();
        
        assertEquals("vc1.value", value, vc1.value);
        
        initiator.trigger(this.TRIGGER_ECD);
        queue.waitTilEmpty();
        assertEquals("vc2.value", value, vc2.value);
    }

    private class ValueCommit extends Commit
    {
        String value = null;

        public ValueCommit(String name)
        {
            super(name, new StringECD[] {VALUE_ECD});
        }

        protected void commit()
        {
            this.value = (String) get(VALUE_ECD);
        }
    }

    private class ComponentSwitchCommit extends TriggeredCommit
    {
        public final MultiplexedBranch mb;

        public ComponentSwitchCommit(MultiplexedBranch mb)
        {
            super("ComponentSwitchCommit", TRIGGER_ECD);
            this.mb = mb;
        }

        protected void commit()
        {
            mb.remove(vc1);
            mb.add(vc2, 0);
        }
    }
}
