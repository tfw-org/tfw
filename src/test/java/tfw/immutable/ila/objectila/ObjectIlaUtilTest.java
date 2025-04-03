package tfw.immutable.ila.objectila;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class ObjectIlaUtilTest {
    @Test
    void toArrayTwoArgsTest() throws Exception {
        final String[] expectedStrings = new String[] {"Zero", "One", "Two", "Three", "Four"};
        final ObjectIla<String> stringsIla = ObjectIlaFromArray.create(expectedStrings);
        final String[] actualStringsShorter = ObjectIlaUtil.toArray(stringsIla, new String[0]);

        assertThat(expectedStrings).isEqualTo(actualStringsShorter);

        final String[] actualStringsEqualLength =
                ObjectIlaUtil.toArray(stringsIla, new String[(int) stringsIla.length()]);

        assertThat(expectedStrings).isEqualTo(actualStringsEqualLength);

        final String[] expectedStrings2 = new String[] {"Zero", "One", "Two", "Three", "Four", null, "Six"};
        final String[] actualStringsLonger =
                ObjectIlaUtil.toArray(stringsIla, new String[] {null, null, null, null, null, "Five", "Six"});

        assertThat(expectedStrings2).isEqualTo(actualStringsLonger);
    }
}
