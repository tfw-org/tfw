package tfw.fuzz;

import java.io.IOException;

public final class IlaFuzzSpec<A, I> {

    @FunctionalInterface
    public interface CreateFunction<A, I> {

        I create(A array) throws Exception;
    }

    @FunctionalInterface
    public interface LengthFunction<I> {

        long length(I ila) throws IOException;
    }

    @FunctionalInterface
    public interface GetFunction<A, I> {

        void get(I ila, A destination, int offset, long start, int length) throws Exception;
    }

    private final String name;
    private final IlaArrayAdapter<A> adapter;
    private final CreateFunction<A, I> create;
    private final LengthFunction<I> length;
    private final GetFunction<A, I> get;

    public IlaFuzzSpec(
            String name,
            IlaArrayAdapter<A> adapter,
            CreateFunction<A, I> create,
            LengthFunction<I> length,
            GetFunction<A, I> get) {

        this.name = name;
        this.adapter = adapter;
        this.create = create;
        this.length = length;
        this.get = get;
    }

    public String name() {
        return name;
    }

    public IlaArrayAdapter<A> adapter() {
        return adapter;
    }

    public I create(A array) throws Exception {
        return create.create(array);
    }

    public long length(I ila) throws IOException {
        return length.length(ila);
    }

    public void get(I ila, A destination, int offset, long start, int length) throws Exception {

        get.get(ila, destination, offset, start, length);
    }
}
