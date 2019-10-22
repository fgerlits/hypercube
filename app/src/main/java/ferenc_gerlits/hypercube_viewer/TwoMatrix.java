package ferenc_gerlits.hypercube_viewer;

public class TwoMatrix {
    private double[][] elements;

    private TwoMatrix(double[][] elements) {
        this.elements = elements;
    }

    public static TwoMatrix rotationBy(double angle) {
        return new TwoMatrix(new double[][]{
                new double[]{Math.cos(angle), -Math.sin(angle)},
                new double[]{Math.sin(angle), Math.cos(angle)}
        });
    }

    public double get(int i, int j) {
        if (i >= 0 && i < 2 && j >= 0 && j < 2) {
            return elements[i][j];
        } else {
            throw new IllegalArgumentException("Coordinates out of range in TwoMatrix#get: (" + i + ", " + j + ")");
        }
    }
}
