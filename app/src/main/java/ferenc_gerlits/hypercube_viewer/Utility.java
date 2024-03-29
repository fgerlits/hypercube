package ferenc_gerlits.hypercube_viewer;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static final double EPSILON = 1e-8;

    public static final int VERTICES_PER_TRIANGLE = 3;

    public static <T> List<T> listOfElementsAt(List<T> allElements, int... indices) {
        List<T> list = new ArrayList<>();
        for (int index : indices) {
            list.add(allElements.get(index));
        }
        return list;
    }

    public static boolean allVerticesLieInTheHyperplane(List<FourDimensionalVertex> vertices,
                                                        Hyperplane hyperplane) {
        for (FourDimensionalVertex vertex : vertices) {
            if (!vertex.liesInTheHyperplane(hyperplane)) {
                return false;
            }
        }
        return true;
    }
}
