package tfw.immutable.iba;

import java.math.BigInteger;

public abstract class AbstractIbaFromArray extends AbstractIba {
    protected final BigInteger arrayLength;

    protected AbstractIbaFromArray(BigInteger arrayLength) {
        this.arrayLength = arrayLength;
    }

    @Override
    protected void closeImpl() {
        // Nothing to do.
    }

    @Override
    protected BigInteger lengthImpl() {
        return arrayLength;
    }
}
