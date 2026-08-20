package tfw.fuzz;

public interface IlaArrayAdapter<A> {

    A create(int length);

    void initialize(A array);

    A copy(A array);

    void assertElementEquals(
            A expected,
            int expectedIndex,
            A actual,
            int actualIndex);
}
