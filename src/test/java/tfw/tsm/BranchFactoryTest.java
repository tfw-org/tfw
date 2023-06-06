package tfw.tsm;

import junit.framework.TestCase;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.value.ValueException;

/**
 *
 */
public class BranchFactoryTest extends TestCase {
    public void testFactory() {
        BranchFactory bf = new BranchFactory();

        try {
            bf.addEventChannel(null);
            fail("addTerminator() accepted null event channel description");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        StringECD stringECD = new StringECD("myString");

        try {
            bf.addEventChannel(null, new Object());
            fail("addTerminator() accepted null event channel description");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            bf.addEventChannel(stringECD, new Object());
            fail("addTerminator() accepted null event channel description");
        } catch (ValueException expected) {
            // System.out.println(expected);
        }

        try {
            bf.addEventChannel(stringECD, "myString", null, null);
            fail("addTerminator() accepted null state change rule");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        bf.addEventChannel(stringECD);

        StatelessTriggerECD memoryLessECD = new StatelessTriggerECD("memoryLess");
        try {
            bf.addEventChannel(memoryLessECD, "non-null state");
            fail("addEventChannel() accepted non-null state on memory less event channel");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            bf.addEventChannel(stringECD);
            fail("addTerminator() accepted duplicate terminator");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        StringECD childECD = new StringECD("child");
        StringECD parentECD = new StringECD("parent");

        try {
            bf.addTranslation(null, parentECD);
            fail("addTranslator() accepted null child event channel");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            bf.addTranslation(childECD, null);
            fail("addTranslator() accepted null parent event channel");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            bf.addTranslation(stringECD, parentECD);
            fail("addTranslator() accepted terminated event channel");
        } catch (IllegalStateException expected) {
            // System.out.println(expected);
        }

        bf.addTranslation(childECD, parentECD);

        try {
            bf.addTranslation(childECD, parentECD);
            fail("addTranslator() accepted duplicate translation");
        } catch (IllegalStateException expected) {
            // System.out.println(expected);
        }

        try {
            bf.addEventChannel(childECD);
            fail("addEventChannel() accepted translated event channel");
        } catch (IllegalStateException expected) {
            // System.out.println(expected);
        }

        try {
            bf.create(null);
            fail("create() accepted null name");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        bf.clear();

        IntegerECD integerECD = new IntegerECD("Full Range Integer");
        IntegerECD int0_1ECD = new IntegerECD("constrained integer", 0, 1);

        try {
            bf.addTranslation(integerECD, int0_1ECD);
            fail("addTranslator() parent un-assignable to child");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
        try {
            bf.addTranslation(int0_1ECD, integerECD);
            fail("addTranslator() child un-assignable to parent");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }
}
