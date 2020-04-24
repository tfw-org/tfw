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

import junit.framework.TestCase;
import tfw.tsm.MultiplexedBranchFactory;
import tfw.tsm.MultiplexedBranch;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

// TODO: test compatibility between multiValueECD and valueECD

/**
 *
 */
public class MultiplexedBranchFactoryTest extends TestCase
{
    private ObjectECD valueECD = new StringECD("value");
    private ObjectIlaECD multiValueECD = new ObjectIlaECD("multiValue");

    public void testAddMultiplexer()
    {
        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();

        try
        {
            mbf.addMultiplexer(null, multiValueECD);
            fail("addMultiplexer() accepted null valueECD");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            mbf.addMultiplexer(valueECD, null);
            fail("addMultiplexer() accepted null multiValueECD");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        ObjectECD newValueECD = new StringECD("differentValue");
        ObjectIlaECD newMultiValueECD = new ObjectIlaECD("differentMultiValue");

        mbf.addMultiplexer(valueECD, newMultiValueECD);

        try
        {
            mbf.addMultiplexer(valueECD, multiValueECD);
            fail(
                "addMultiplexer() accepted multiple multiplexer with the same valueECD");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
    }

    public void testCreate()
    {
        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();

        try
        {
            mbf.create(null);
            fail("create() accepted null value");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

//        try
//        {
//            mbf.create("test");
//            fail("create() created empty multiplexed branch");
//        }
//        catch (IllegalStateException expected)
//        {
//            //System.out.println(expected);
//        }

        mbf.addMultiplexer(valueECD, multiValueECD);

        mbf.clear();

//        try
//        {
//            mbf.create("test");
//            fail("clear() didn't reset the multiplexer state");
//        }
//        catch (IllegalStateException expected)
//        {
//            //System.out.println(expected);
//        }

        mbf.addMultiplexer(valueECD, multiValueECD);

        try
        {
            mbf.addMultiplexer(valueECD, multiValueECD);
            fail("addMultiplexer() accepted the same ECDs twice!");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        MultiplexedBranch branch = mbf.create("test");
        assertNotNull("create() returned null", branch);
    }
}