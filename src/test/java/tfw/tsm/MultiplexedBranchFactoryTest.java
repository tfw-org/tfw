package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

final class MultiplexedBranchFactoryTest {
    private ObjectECD valueECD = new StringECD("value");
    private ObjectIlaECD multiValueECD = new ObjectIlaECD("multiValue");

    @Test
    void addMultiplexerTest() {
        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();

        assertThatThrownBy(() -> mbf.addMultiplexer(null, multiValueECD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("valueECD == null not allowed!");

        assertThatThrownBy(() -> mbf.addMultiplexer(valueECD, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("multiValueECD == null not allowed!");

        ObjectIlaECD newMultiValueECD = new ObjectIlaECD("differentMultiValue");

        mbf.addMultiplexer(valueECD, newMultiValueECD);

        assertThatThrownBy(() -> mbf.addMultiplexer(valueECD, multiValueECD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Attempt to add multiple multiplexers for value event channel 'value'");
    }

    @Test
    void createTest() {
        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();

        assertThatThrownBy(() -> mbf.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");

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

        assertThatThrownBy(() -> mbf.addMultiplexer(valueECD, multiValueECD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Attempt to add multiple multiplexers for multi event channel 'multiValue'");

        MultiplexedBranch branch = mbf.create("test");
        assertThat(branch).isNotNull();
    }
}
