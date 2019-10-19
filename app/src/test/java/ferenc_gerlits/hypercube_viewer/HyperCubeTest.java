package ferenc_gerlits.hypercube_viewer;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class HyperCubeTest {
    public static final float[] BASIS_FOR_XYZ_HYPERPLANE = new float[]{1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1};
    public static final float[] BASIS_FOR_DIAGONAL = new float[]{0.5f, 0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f, -0.5f};
    private HyperCube hyperCube = new HyperCube();

    @Test
    public void intersect_face_parallel_to_xyz_by_space_parallel_to_xyz_at_the_end() {
        List<Face> faces = hyperCube.intersect(BASIS_FOR_XYZ_HYPERPLANE, 1);
        // should be a green cube
        verifyResult(Arrays.asList(
                new ExpectedFace(4, Color.GREEN),
                new ExpectedFace(4, Color.GREEN),
                new ExpectedFace(4, Color.GREEN),
                new ExpectedFace(4, Color.GREEN),
                new ExpectedFace(4, Color.GREEN),
                new ExpectedFace(4, Color.GREEN)
        ), faces);
    }

    @Test
    public void intersect_face_parallel_to_xyz_by_space_parallel_to_xyz_in_the_middle() {
        List<Face> faces = hyperCube.intersect(BASIS_FOR_XYZ_HYPERPLANE, 0.1f);
        // should be a multi-colored cube
        verifyResult(Arrays.asList(
                new ExpectedFace(4, Color.BLUE),
                new ExpectedFace(4, Color.YELLOW),
                new ExpectedFace(4, Color.CYAN),
                new ExpectedFace(4, Color.MAGENTA),
                new ExpectedFace(4, Color.ORANGE),
                new ExpectedFace(4, Color.LIGHT_BLUE)
        ), faces);
    }

    @Test
    public void intersect_face_general_case() {
        List<Face> faces = hyperCube.intersect(BASIS_FOR_DIAGONAL, 0.2f);
        // should be truncated tetrahedron
        verifyResult(Arrays.asList(
                new ExpectedFace(3, Color.RED),
                new ExpectedFace(6, Color.GREEN),
                new ExpectedFace(3, Color.BLUE),
                new ExpectedFace(6, Color.YELLOW),
                new ExpectedFace(3, Color.CYAN),
                new ExpectedFace(6, Color.MAGENTA),
                new ExpectedFace(3, Color.ORANGE),
                new ExpectedFace(6, Color.LIGHT_BLUE)
        ), faces);
    }

    @Test
    public void intersect_face_by_diagonal_through_the_origin() {
        List<Face> faces = hyperCube.intersect(BASIS_FOR_DIAGONAL, 0);
        // should be an octahedron
        verifyResult(Arrays.asList(
                new ExpectedFace(3, Color.RED),
                new ExpectedFace(3, Color.GREEN),
                new ExpectedFace(3, Color.BLUE),
                new ExpectedFace(3, Color.YELLOW),
                new ExpectedFace(3, Color.CYAN),
                new ExpectedFace(3, Color.MAGENTA),
                new ExpectedFace(3, Color.ORANGE),
                new ExpectedFace(3, Color.LIGHT_BLUE)
        ), faces);
    }

    private static class ExpectedFace {
        private int size;
        private Color color;

        public ExpectedFace(int size, Color color) {
            this.size = size;
            this.color = color;
        }

        public int getSize() {
            return size;
        }

        public Color getColor() {
            return color;
        }
    }

    private void verifyResult(List<ExpectedFace> expected, List<Face> actual) {
        for (ExpectedFace expectedFace : expected) {
            int i = findFace(expectedFace, actual);
            assertNotEquals(expectedFace.getColor() + " face with " + expectedFace.getSize() + " sides not found",
                    -1, i);
            actual.remove(i);
        }
        assertTrue(actual.size() + " extra faces found", actual.isEmpty());
    }

    private int findFace(ExpectedFace expectedFace, List<Face> faceList) {
        for (int i = 0; i < faceList.size(); ++i) {
            Face face = faceList.get(i);
            if (face.getVertices().size() == expectedFace.getSize() &&
                    face.getColor() == expectedFace.getColor()) {
                return i;
            }
        }
        return -1;
    }
}
