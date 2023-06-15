package tfw.visualizer;

import java.util.Comparator;
import tfw.tsm.Proxy;

public class ProxyNameComparator implements Comparator<Object> {
    public static final ProxyNameComparator INSTANCE = new ProxyNameComparator();

    public int compare(Object obj1, Object obj2) {
        Proxy proxy1 = (Proxy) obj1;
        Proxy proxy2 = (Proxy) obj2;
        String className1 = proxy1.getClass().getName();
        String className2 = proxy2.getClass().getName();

        int result = className1.compareTo(className2);

        if (result == 0) {
            result = proxy1.getName().compareTo(proxy2.getName());
        }

        return (result);
    }
}
