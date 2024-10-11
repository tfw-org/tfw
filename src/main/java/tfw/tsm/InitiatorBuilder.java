package tfw.tsm;

import java.util.ArrayList;
import java.util.List;
import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;

public class InitiatorBuilder {
    private String name = null;
    private List<EventChannelDescription> eventChannelDescriptions = new ArrayList<>();
    private StateQueueFactory stateQueueFactory = new DefaultStateQueueFactory();

    public InitiatorBuilder setName(final String name) {
        this.name = name;

        return this;
    }

    public InitiatorBuilder addEventChannelDescription(final EventChannelDescription eventChannelDescription) {
        eventChannelDescriptions.add(eventChannelDescription);

        return this;
    }

    public InitiatorBuilder setStateQueueFactory(final StateQueueFactory stateQueueFactory) {
        this.stateQueueFactory = stateQueueFactory;

        return this;
    }

    public Initiator build() {
        Argument.assertNotNull(name, "name");
        Argument.assertGreaterThan(eventChannelDescriptions.size(), 0, "eventChannelDescriptions.size()");

        final EventChannelDescription[] ecds =
                eventChannelDescriptions.toArray(new EventChannelDescription[eventChannelDescriptions.size()]);

        return new Initiator(name, ecds, stateQueueFactory);
    }
}
