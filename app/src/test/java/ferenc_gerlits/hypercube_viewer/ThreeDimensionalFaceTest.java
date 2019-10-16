package ferenc_gerlits.hypercube_viewer;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ThreeDimensionalFaceTest {
    private HyperCube hyperCube = new HyperCube();
    private ThreeDimensionalFace cube = hyperCube.getFaces().get(1);    // face with w = 1

    public static final float[] BASIS_FOR_XYZ_HYPERPLANE = new float[]{1, 0, 0, 0,
                                                                       0, 1, 0, 0,
                                                                       0, 0, 1, 0,
                                                                       0, 0, 0, 1};
    public static final float[] BASIS_FOR_DIAGONAL = new float[]{0.5f, 0.5f, 0.5f, 0.5f,
                                                                 0.5f, 0.5f, -0.5f, -0.5f,
                                                                 0.5f, -0.5f, -0.5f, 0.5f,
                                                                 0.5f, -0.5f, 0.5f, -0.5f};

    @Test
    public void intersect_face_parallel_to_xyz_by_space_parallel_to_xyz_at_the_same_distance() {
        // should be a cube
        assertEquals(6, cube.intersect(BASIS_FOR_XYZ_HYPERPLANE, 1).size());
    }

    @Test
    public void intersect_face_parallel_to_xyz_by_space_parallel_to_xyz_NOT_at_the_same_distance() {
        assertEquals(0, cube.intersect(BASIS_FOR_XYZ_HYPERPLANE, 0).size());
    }

    @Test
    public void intersect_face_general_case() {
        List<Face> intersection = cube.intersect(BASIS_FOR_DIAGONAL, 0.2f);
        // should be one hexagon
        assertEquals(1, intersection.size());
        assertEquals(6, intersection.get(0).getVertices().size());
    }

    @Test
    public void intersect_face_by_diagonal_through_the_origin() {
        List<Face> intersection = cube.intersect(BASIS_FOR_DIAGONAL, 0);
        // should be one triangle
        assertEquals(1, intersection.size());
        assertEquals(3, intersection.get(0).getVertices().size());
    }
}
