package ferenc_gerlits.hypercube_viewer;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static final double EPSILON = 1e-8;

    public static <T> List<T> listOfElementsAt(List<T> allElements, int... indices) {
        List<T> list = new ArrayList<>();
        for (int index : indices) {
            list.add(allElements.get(index));
        }
        return list;
    }

}
