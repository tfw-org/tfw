package tfw.tsm;

import tfw.check.Argument;

public final class ValidatorProxy implements Proxy {
    private final Validator validator;

    public ValidatorProxy(Validator validator) {
        Argument.assertNotNull(validator, "validator");

        this.validator = validator;
    }

    public String getName() {
        return (validator.getName());
    }

    public SinkProxy[] getSinkProxies() {
        Object[] sinks = (Object[]) validator.sinks.values().toArray();
        SinkProxy[] sp = new SinkProxy[sinks.length];

        for (int i = 0; i < sinks.length; i++) {
            sp[i] = new SinkProxy((Sink) sinks[i]);
        }
        return (sp);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ValidatorProxy) {
            ValidatorProxy ip = (ValidatorProxy) obj;

            return (validator.equals(ip.validator));
        }

        return (false);
    }

    public int hashCode() {
        return (validator.hashCode());
    }
}
