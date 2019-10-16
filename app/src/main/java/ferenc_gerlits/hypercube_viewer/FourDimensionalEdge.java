package ferenc_gerlits.hypercube_viewer;

import java.util.Collections;
import java.util.List;

class FourDimensionalEdge {
    private FourDimensionalVertex from;
    private FourDimensionalVertex to;

    public FourDimensionalEdge(FourDimensionalVertex from, FourDimensionalVertex to) {
        this.from = from;
        this.to = to;
    }

    public List<FourDimensionalVertex> intersect(float[] basisVectors, float translation) {
        float[] normalVector = new float[]{basisVectors[0], basisVectors[1], basisVectors[2], basisVectors[3]};
        double fromProduct = from.scalarProductWith(normalVector);
        double toProduct = to.scalarProductWith(normalVector);

        if (fromProduct == translation) {               // if the 'to' end is on the hyperplane,
            return Collections.singletonList(from);     // it will be found as the 'from' end of the next edge

        } else if (fromProduct < translation && toProduct > translation) {
            return Collections.singletonList(FourDimensionalVertex.pointInBetween(
                    from, toProduct - translation, to, translation - fromProduct));

        } else if (fromProduct > translation && toProduct < translation) {
            return Collections.singletonList(FourDimensionalVertex.pointInBetween(
                    from,translation - toProduct, to,fromProduct - translation));

        } else {
            return Collections.emptyList();
        }
    }
}
