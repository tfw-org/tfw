package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.value.ValueException;

final class BranchFactoryTest {
    @Test
    void factoryTest() {
        BranchFactory bf = new BranchFactory();

        assertThatThrownBy(() -> bf.addEventChannel(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("eventChannelDescription == null not allowed!");

        StringECD stringECD = new StringECD("myString");
        Object object = new Object();

        assertThatThrownBy(() -> bf.addEventChannel(null, object))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("eventChannelDescription == null not allowed!");
        assertThatThrownBy(() -> bf.addEventChannel(stringECD, object))
                .isInstanceOf(ValueException.class)
                .hasMessage("The value, of type 'java.lang.Object', is not assignable to type 'java.lang.String'.");
        assertThatThrownBy(() -> bf.addEventChannel(stringECD, "myString", null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rule == null not allowed!");

        bf.addEventChannel(stringECD);

        StatelessTriggerECD memoryLessECD = new StatelessTriggerECD("memoryLess");

        assertThatThrownBy(() -> bf.addEventChannel(memoryLessECD, "non-null state"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "Non-null 'initialState' is not permitted given eventChannelDescription.isFireOnConnect() == false");
        assertThatThrownBy(() -> bf.addEventChannel(stringECD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The event channel 'myString' is already exists");

        StringECD childECD = new StringECD("child");
        StringECD parentECD = new StringECD("parent");

        assertThatThrownBy(() -> bf.addTranslation(null, parentECD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("childEventChannel == null not allowed!");
        assertThatThrownBy(() -> bf.addTranslation(childECD, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("parentEventChannel == null not allowed!");
        assertThatThrownBy(() -> bf.addTranslation(stringECD, parentECD))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Attempt to translate the event channel 'myString' which is already terminated.");

        bf.addTranslation(childECD, parentECD);

        assertThatThrownBy(() -> bf.addTranslation(childECD, parentECD))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Attempt to translate the event channel 'child' which is already translated.");
        assertThatThrownBy(() -> bf.addEventChannel(childECD))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Attemp to terminate an event channel, 'child', which is already transalated.");
        assertThatThrownBy(() -> bf.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");

        bf.clear();

        IntegerECD integerECD = new IntegerECD("Full Range Integer");
        IntegerECD constrainedIntECD = new IntegerECD("constrained integer", 0, 1);

        assertThatThrownBy(() -> bf.addTranslation(integerECD, constrainedIntECD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "Incompatible event channels values from the child event channel 'Full Range Integer' are not assignable to the parent event channel 'constrained integer'");
        assertThatThrownBy(() -> bf.addTranslation(constrainedIntECD, integerECD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "Incompatible event channels, values from the parent event channel 'Full Range Integer' are not assignable to the child event channel 'constrained integer'");
    }
}
