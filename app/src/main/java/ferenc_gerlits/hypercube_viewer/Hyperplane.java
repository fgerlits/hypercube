package ferenc_gerlits.hypercube_viewer;

public class Hyperplane {
    BasisVectors basisVectors;
    double translation;

    public Hyperplane(BasisVectors basisVectors, double translation) {
        this.basisVectors = basisVectors;
        this.translation = translation;
    }

    public Vector getNormalVector() {
        return basisVectors.getNormalVector();
    }

    public BasisVectors getBasisVectors() {
        return basisVectors;
    }

    public double getTranslation() {
        return translation;
    }
}
