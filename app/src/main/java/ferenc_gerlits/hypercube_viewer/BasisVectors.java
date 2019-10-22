package ferenc_gerlits.hypercube_viewer;

public class BasisVectors {
    private Vector normalVector;
    private Vector a;
    private Vector b;
    private Vector c;

    public BasisVectors(Vector normalVector, Vector a, Vector b, Vector c) {
        this.normalVector = normalVector;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    private BasisVectors(FourMatrix m) {
        normalVector = new Vector(m.get(0, 0), m.get(1, 0), m.get(2, 0), m.get(3, 0));
        a = new Vector(m.get(0, 1), m.get(1, 1), m.get(2, 1), m.get(3, 1));
        b = new Vector(m.get(0, 2), m.get(1, 2), m.get(2, 2), m.get(3, 2));
        c = new Vector(m.get(0, 3), m.get(1, 3), m.get(2, 3), m.get(3, 3));
    }

    public static BasisVectors createFromRotations(double rotationWX, double rotationWY, double rotationWZ) {
        return new BasisVectors(
                FourMatrix.fromTwoMatrixWithFixedCoordinates(TwoMatrix.rotationBy(rotationWX), 2, 3)
                        .times(FourMatrix.fromTwoMatrixWithFixedCoordinates(TwoMatrix.rotationBy(rotationWY), 1, 3))
                        .times(FourMatrix.fromTwoMatrixWithFixedCoordinates(TwoMatrix.rotationBy(rotationWZ), 1, 2)));
    }

    public Vector getNormalVector() {
        return normalVector;
    }

    public Vector getA() {
        return a;
    }

    public Vector getB() {
        return b;
    }

    public Vector getC() {
        return c;
    }
}
