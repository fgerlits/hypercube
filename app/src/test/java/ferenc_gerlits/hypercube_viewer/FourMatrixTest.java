package ferenc_gerlits.hypercube_viewer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FourMatrixTest {

    private static final double SQRT_2_PER_2 = Math.sqrt(2) / 2;
    private static final double SQRT_3_PER_2 = Math.sqrt(3) / 2;

    @Test
    public void fromTwoMatrixWithFixedCoordinates() {
        assertEquals(createMatrix(
                row(1, 0, 0, 0),
                row(0, 1, 0, 0),
                row(0, 0, SQRT_3_PER_2, -0.5),
                row(0, 0, 0.5, SQRT_3_PER_2)),
                FourMatrix.fromTwoMatrixWithFixedCoordinates(TwoMatrix.rotationBy(Math.PI / 6), 0, 1)
        );

        assertEquals(createMatrix(
                row(SQRT_2_PER_2, 0, 0, -SQRT_2_PER_2),
                row(0, 1, 0, 0),
                row(0, 0, 1, 0),
                row(SQRT_2_PER_2, 0, 0, SQRT_2_PER_2)),
                FourMatrix.fromTwoMatrixWithFixedCoordinates(TwoMatrix.rotationBy(Math.PI / 4), 1, 2)
        );

        assertEquals(createMatrix(
                row(1, 0, 0, 0),
                row(0, 0.5, -SQRT_3_PER_2, 0),
                row(0, SQRT_3_PER_2, 0.5, 0),
                row(0, 0, 0, 1)),
                FourMatrix.fromTwoMatrixWithFixedCoordinates(TwoMatrix.rotationBy(Math.PI / 3), 0, 3)
        );

        assertEquals(createMatrix(
                row(0, -1, 0, 0),
                row(1, 0, 0, 0),
                row(0, 0, 1, 0),
                row(0, 0, 0, 1)),
                FourMatrix.fromTwoMatrixWithFixedCoordinates(TwoMatrix.rotationBy(Math.PI / 2), 2, 3)
        );
    }

    private FourMatrix createMatrix(double[]... rows) {
        return new FourMatrix(rows);
    }

    private double[] row(double... elements) {
        return elements;
    }

    @Test
    public void times() {
        FourMatrix unitMatrix = createMatrix(
                row(1, 0, 0, 0),
                row(0, 1, 0, 0),
                row(0, 0, 1, 0),
                row(0, 0, 0, 1));

        assertEquals(unitMatrix, unitMatrix.times(unitMatrix));

        FourMatrix someMatrix = createMatrix(
                row(1, 1, 1, 1),
                row(2, 2, 2, 2),
                row(3, 3, 3, 3),
                row(4, 4, 4, 4));

        FourMatrix someOtherMatrix = createMatrix(
                row(0, 1, 0, 1),
                row(0, 2, 0, 2),
                row(3, 0, 3, 0),
                row(4, 0, 4, 0));

        assertEquals(someMatrix, someMatrix.times(unitMatrix));

        assertEquals(someOtherMatrix, unitMatrix.times(someOtherMatrix));

        assertEquals(createMatrix(
                row(7, 3, 7, 3),
                row(14, 6, 14, 6),
                row(21, 9, 21, 9),
                row(28, 12, 28, 12)),
                someMatrix.times(someOtherMatrix));
    }
}
