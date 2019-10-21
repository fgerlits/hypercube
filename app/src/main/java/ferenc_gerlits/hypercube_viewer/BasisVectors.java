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
