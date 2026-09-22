// booleanilm,byteilm,charilm,doubleilm,floatilm,intilm,longilm,objectilm,shortilm
package ${PACKAGE};

import static org.assertj.core.api.Assertions.assertThat;

${RANDOM_INCLUDE}import org.junit.jupiter.api.Test;

final class ${NAME}IlmFromArrayTest {
    @Test
    void ${TYPE}IlmFromArrayTest() throws Exception {
        ${RANDOM_INIT}${TYPE}[] array = new ${TYPE}[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = ${RANDOM_VALUE};
        }

        ${NAME}Ilm ${TYPE}Ilm = ${NAME}IlmFromArray.create(array, array.length / 2);

<#if TYPE == "Object">
        assertThat(array).isEqualTo(${NAME}IlmUtil.toArray(${TYPE}Ilm, new Object[0]));
<#else>
        assertThat(array).isEqualTo(${NAME}IlmUtil.toArray(${TYPE}Ilm));
</#if>
    }
}
