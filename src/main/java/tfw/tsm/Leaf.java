package tfw.tsm;

/**
 * The base class for all leaf components. DO NOT TRY TO EXTEND THIS CLASS.
 * This class is made public for the purpose of providing a common super-class
 * for all of leaf components in the framework.
 */
public class Leaf extends TreeComponent {
    /**
     * Constructs a leaf component with the specified name.
     * @param name the name of the component.
     */
    Leaf(String name, Sink[] sinks, Source[] sources) {
        super(name, sinks, sources, null);
    }
}
