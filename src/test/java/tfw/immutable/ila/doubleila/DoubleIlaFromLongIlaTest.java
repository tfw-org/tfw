package tfw.immutable.ila.doubleila;

import org.junit.jupiter.api.Test;

class DoubleIlaFromLongIlaTest {
    @Test
    void testDoubleIlaFromLongIla() throws Exception {
        /*
        		final int LENGTH = 64;
        		final long SKIP = Long.MAX_VALUE / LENGTH * 2;
        		long[] longArray = new long[LENGTH];
        		double[] doubleArray = new double[LENGTH];

        		long v=-Long.MAX_VALUE-1;
        		for (int i=0 ; i < LENGTH ; i++,v+=SKIP)
        		{
        			longArray[i] = v;
        			doubleArray[i] = Double.longBitsToDouble(v);
        		}

        		LongIla longIla = LongIlaFromArray.create(longArray);
        		DoubleIla targetIla = DoubleIlaFromArray.create(doubleArray);

        		DoubleIla actualIla = DoubleIlaFromLongIla.create(longIla);
                final double epsilon = (double) 0.0;
                DoubleIlaCheck.checkAll(targetIla, actualIla,
                                        IlaTestDimensions.defaultOffsetLength(),
                                        IlaTestDimensions.defaultMaxStride(),
                                        epsilon);

        */
    }
}
