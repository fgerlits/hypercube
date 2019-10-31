package ferenc_gerlits.hypercube_viewer;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class FourMatrix {
    private double[][] elements;

    // package private for unit testing
    FourMatrix(double[][] elements) {
        this.elements = elements;
    }

    public static FourMatrix fromTwoMatrixWithFixedCoordinates(TwoMatrix twoMatrix, int coord1, int coord2) {
        double[][] elements = createBlankElements();

        int firstNonFixedCoord = -1;
        for (int i = 0; i < 4; ++i) {
            if (i == coord1 || i == coord2) {
                elements[i][i] = 1;
            } else {
                if (firstNonFixedCoord == -1) {
                    elements[i][i] = twoMatrix.get(0, 0);
                    firstNonFixedCoord = i;
                } else {
                    elements[firstNonFixedCoord][i] = twoMatrix.get(0, 1);
                    elements[i][firstNonFixedCoord] = twoMatrix.get(1, 0);
                    elements[i][i] = twoMatrix.get(1, 1);
                }
            }
        }

        return new FourMatrix(elements);
    }

    private static double[][] createBlankElements() {
        double[][] elements = new double[4][];
        for (int i = 0; i < 4; ++i) {
            elements[i] = new double[4];    // array components are automatically initialized to 0.0
        }
        return elements;
    }

    public FourMatrix times(FourMatrix that) {
        double[][] elements = createBlankElements();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                for (int k = 0; k < 4; ++k) {
                    elements[i][j] += this.elements[i][k] * that.elements[k][j];
                }
            }
        }
        return new FourMatrix(elements);
    }

    public double get(int i, int j) {
        if (i >= 0 && i < 4 && j >= 0 && j < 4) {
            return elements[i][j];
        } else {
            throw new IllegalArgumentException("Coordinates out of range in FourMatrix#get: (" + i + ", " + j + ")");
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FourMatrix that = (FourMatrix) o;

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                double diff = this.elements[i][j] - that.elements[i][j];
                if (Math.abs(diff) >= Utility.EPSILON) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    @NonNull
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < 4; ++i) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append("[");
            for (int j = 0; j < 4; ++j) {
                if (j > 0) {
                    builder.append(", ");
                }
                builder.append(elements[i][j]);
            }
            builder.append("]");
        }
        builder.append("]");
        return builder.toString();
    }
}
