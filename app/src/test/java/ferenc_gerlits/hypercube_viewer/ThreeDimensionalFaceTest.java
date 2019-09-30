package ferenc_gerlits.hypercube_viewer;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ThreeDimensionalFaceTest {
    HyperCube hyperCube = new HyperCube();

    public static final float[] BASIS_FOR_XYZ_HYPERPLANE = new float[]{1, 0, 0, 0,
                                                                       0, 1, 0, 0,
                                                                       0, 0, 1, 0,
                                                                       0, 0, 0, 1};

    @Test
    public void intersect_face_parallel_to_xyz_by_space_parallel_to_xyz_at_the_same_distance() {
        ThreeDimensionalFace cube = hyperCube.getFaces().get(1);    // face with w = 1
        assertEquals(6, cube.intersect(BASIS_FOR_XYZ_HYPERPLANE, 1).size());
    }

    @Test
    public void intersect_face_parallel_to_xyz_by_space_parallel_to_xyz_NOT_at_the_same_distance() {
        ThreeDimensionalFace cube = hyperCube.getFaces().get(1);    // face with w = 1
        assertEquals(0, cube.intersect(BASIS_FOR_XYZ_HYPERPLANE, 0).size());
    }
}
