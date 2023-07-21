package tfw.immutable.ila.objectila;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class ObjectIlaUtilTest {
    @Test
    void testToArrayTwoArgs() throws Exception {
        final String[] expectedStrings = new String[] {"Zero", "One", "Two", "Three", "Four"};
        final ObjectIla<String> stringsIla = ObjectIlaFromArray.create(expectedStrings);
        final String[] actualStringsShorter = ObjectIlaUtil.toArray(stringsIla, new String[0]);

        assertArrayEquals(expectedStrings, actualStringsShorter);

        final String[] actualStringsEqualLength =
                ObjectIlaUtil.toArray(stringsIla, new String[(int) stringsIla.length()]);

        assertArrayEquals(expectedStrings, actualStringsEqualLength);

        final String[] expectedStrings2 = new String[] {"Zero", "One", "Two", "Three", "Four", null, "Six"};
        final String[] actualStringsLonger =
                ObjectIlaUtil.toArray(stringsIla, new String[] {null, null, null, null, null, "Five", "Six"});

        assertArrayEquals(expectedStrings2, actualStringsLonger);
    }
}
