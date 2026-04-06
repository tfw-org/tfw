package tfw.demo;

import java.util.List;
import tfw.awt.ecd.ColorECD;
import tfw.tsm.AbstractEventChannelEnum;
import tfw.tsm.DotEqualsRule;
import tfw.tsm.EventChannelEnum;
import tfw.tsm.StateChangeRule;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.StringRollbackECD;

public class TextFieldDemoEnum<T extends EventChannelDescription> extends AbstractEventChannelEnum<T> {
    public static final TextFieldDemoEnum<StringECD> RED_STRING =
            new TextFieldDemoEnum<>(new StringECD("redString"), DotEqualsRule.RULE, null, null);
    public static final TextFieldDemoEnum<StringECD> RED_STRING_ADJ =
            new TextFieldDemoEnum<>(new StringECD("redStringAdj"), DotEqualsRule.RULE, null, null);
    public static final TextFieldDemoEnum<RedGreenBlueECD> RED_INTEGER =
            new TextFieldDemoEnum<>(new RedGreenBlueECD("redInteger"), DotEqualsRule.RULE, 0, null);
    public static final TextFieldDemoEnum<StringECD> GREEN_STRING =
            new TextFieldDemoEnum<>(new StringECD("greenString"), DotEqualsRule.RULE, null, null);
    public static final TextFieldDemoEnum<StringECD> GREEN_STRING_ADJ =
            new TextFieldDemoEnum<>(new StringECD("greenStringAdj"), DotEqualsRule.RULE, null, null);
    public static final TextFieldDemoEnum<RedGreenBlueECD> GREEN_INTEGER =
            new TextFieldDemoEnum<>(new RedGreenBlueECD("greenInteger"), DotEqualsRule.RULE, 0, null);
    public static final TextFieldDemoEnum<StringECD> BLUE_STRING =
            new TextFieldDemoEnum<>(new StringECD("blueString"), DotEqualsRule.RULE, null, null);
    public static final TextFieldDemoEnum<StringECD> BLUE_STRING_ADJ =
            new TextFieldDemoEnum<>(new StringECD("blueStringAdj"), DotEqualsRule.RULE, null, null);
    public static final TextFieldDemoEnum<RedGreenBlueECD> BLUE_INTEGER =
            new TextFieldDemoEnum<>(new RedGreenBlueECD("blueInteger"), DotEqualsRule.RULE, 0, null);
    public static final TextFieldDemoEnum<StatelessTriggerECD> APPLY_TRIGGER =
            new TextFieldDemoEnum<>(new StatelessTriggerECD("applyTrigger"));
    public static final TextFieldDemoEnum<BooleanECD> APPLY_ENABLE =
            new TextFieldDemoEnum<>(new BooleanECD("applyEnable"), DotEqualsRule.RULE, null, null);
    public static final TextFieldDemoEnum<StatelessTriggerECD> RESET_TRIGGER =
            new TextFieldDemoEnum<>(new StatelessTriggerECD("resetTrigger"));
    public static final TextFieldDemoEnum<BooleanECD> RESET_ENABLE =
            new TextFieldDemoEnum<>(new BooleanECD("resetEnable"), DotEqualsRule.RULE, null, null);
    public static final TextFieldDemoEnum<BooleanECD> COLOR_BUTTON_ENABLE_NAME =
            new TextFieldDemoEnum<>(new BooleanECD("colorButtonEnable"), DotEqualsRule.RULE, true, null);
    public static final TextFieldDemoEnum<ColorECD> COLOR_NAME =
            new TextFieldDemoEnum<>(new ColorECD("color"), DotEqualsRule.RULE, null, null);
    public static final TextFieldDemoEnum<StringRollbackECD> ERROR_NAME =
            new TextFieldDemoEnum<>(new StringRollbackECD("error"), DotEqualsRule.RULE, null, null);

    TextFieldDemoEnum(T statelessTriggerECD) {
        super(statelessTriggerECD, null, null, null);
    }

    TextFieldDemoEnum(
            T eventChannelDescription, StateChangeRule stateChangeRule, Object initialState, String[] exportTags) {
        super(eventChannelDescription, stateChangeRule, initialState, exportTags);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EventChannelEnum<?>> values() {
        return AbstractEventChannelEnum.valuesFromClass(TextFieldDemoEnum.class);
    }
}
