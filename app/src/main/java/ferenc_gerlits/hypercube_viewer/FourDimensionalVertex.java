package ferenc_gerlits.hypercube_viewer;

import static ferenc_gerlits.hypercube_viewer.Utility.EPSILON;

public class FourDimensionalVertex {
    private double w;
    private double x;
    private double y;
    private double z;

    public FourDimensionalVertex(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static FourDimensionalVertex pointInBetween(FourDimensionalVertex aPoint, double aWeight,
                                                       FourDimensionalVertex bPoint, double bWeight) {
        double total = aWeight + bWeight;
        return new FourDimensionalVertex(
                (aPoint.w * aWeight + bPoint.w * bWeight) / total,
                (aPoint.x * aWeight + bPoint.x * bWeight) / total,
                (aPoint.y * aWeight + bPoint.y * bWeight) / total,
                (aPoint.z * aWeight + bPoint.z * bWeight) / total);
    }

    public boolean liesInTheHyperplane(Hyperplane hyperplane) {
        Vector normalVector = hyperplane.getNormalVector();
        double translation = hyperplane.getTranslation();
        return Math.abs(this.scalarProductWith(normalVector) - translation) < EPSILON;
    }

    public double scalarProductWith(Vector vector) {
        return w * vector.getW() +
                x * vector.getX() +
                y * vector.getY() +
                z * vector.getZ();
    }

    public Vertex projectedToHyperplane(Hyperplane hyperplane) {
        BasisVectors basisVectors = hyperplane.getBasisVectors();
        return new Vertex(
                this.scalarProductWith(basisVectors.getA()),
                this.scalarProductWith(basisVectors.getB()),
                this.scalarProductWith(basisVectors.getC()));
    }
}
