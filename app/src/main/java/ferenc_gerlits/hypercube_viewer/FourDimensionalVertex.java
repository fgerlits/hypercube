package ferenc_gerlits.hypercube_viewer;

import static ferenc_gerlits.hypercube_viewer.Utility.EPSILON;

public class FourDimensionalVertex {
    private static final double THRESHOLD = 1e-8;

    private float w;
    private float x;
    private float y;
    private float z;

    public FourDimensionalVertex(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean liesInTheHyperplane(float[] normalVector, float translation) {
        return Math.abs(scalarProductWith(normalVector) - translation) < EPSILON;
    }

    public float scalarProductWith(float[] vector) {
        return (float)(
                ((double) w) * vector[0] +
                ((double) x) * vector[1] +
                ((double) y) * vector[2] +
                ((double) z) * vector[3]
        );  // TODO: everything should be double at this point
    }

    public Vertex projectedToHyperplane(float[] basisVectors) {
        return new Vertex(scalarProductWith(new float[]{basisVectors[4], basisVectors[5], basisVectors[6], basisVectors[7]}),
                scalarProductWith(new float[]{basisVectors[8], basisVectors[9], basisVectors[10], basisVectors[11]}),
                scalarProductWith(new float[]{basisVectors[12], basisVectors[13], basisVectors[14], basisVectors[15]}));
        // TODO: create matrix class instead of this mess
    }

    public static FourDimensionalVertex pointInBetween(FourDimensionalVertex aPoint, double aWeight,
                                                       FourDimensionalVertex bPoint, double bWeight) {
        double total = aWeight + bWeight;
        return new FourDimensionalVertex(
                (float) ((aPoint.w * aWeight + bPoint.w * bWeight) / total),
                (float) ((aPoint.x * aWeight + bPoint.x * bWeight) / total),
                (float) ((aPoint.y * aWeight + bPoint.y * bWeight) / total),
                (float) ((aPoint.z * aWeight + bPoint.z * bWeight) / total));
    }
}
