package ferenc_gerlits.hypercube_viewer;

import java.util.ArrayList;
import java.util.List;

public class HyperCube {
    public static int[] PLUS_OR_MINUS_ONE = {-1, 1};

    private List<FourDimensionalVertex> vertices;
    private List<ThreeDimensionalFace> faces;

    public HyperCube() {
        createVertices();
        createThreeDimensionalFaces();
    }

    private void createVertices() {
        vertices = new ArrayList<>();
        for (int w : PLUS_OR_MINUS_ONE) {
            for (int x : PLUS_OR_MINUS_ONE) {
                for (int y : PLUS_OR_MINUS_ONE) {
                    for (int z : PLUS_OR_MINUS_ONE) {
                        vertices.add(new FourDimensionalVertex(w, x, y, z));
                    }
                }
            }
        }
    }

    private void createThreeDimensionalFaces() {
        faces = new ArrayList<>();
        faces.add(new ThreeDimensionalFace(listOfVertices(0, 1, 2, 3, 4, 5, 6, 7), Color.RED));
        faces.add(new ThreeDimensionalFace(listOfVertices(8, 9, 10, 11, 12, 13, 14, 15), Color.GREEN));
        faces.add(new ThreeDimensionalFace(listOfVertices(0, 1, 2, 3, 8, 9, 10, 11), Color.BLUE));
        faces.add(new ThreeDimensionalFace(listOfVertices(4, 5, 6, 7, 12, 13, 14, 15), Color.YELLOW));
        faces.add(new ThreeDimensionalFace(listOfVertices(0, 1, 4, 5, 8, 9, 12, 13), Color.CYAN));
        faces.add(new ThreeDimensionalFace(listOfVertices(2, 3, 6, 7, 10, 11, 14, 15), Color.MAGENTA));
        faces.add(new ThreeDimensionalFace(listOfVertices(0, 2, 4, 6, 8, 10, 12, 14), Color.ORANGE));
        faces.add(new ThreeDimensionalFace(listOfVertices(1, 3, 5, 7, 9, 11, 13, 15), Color.LIGHT_BLUE));
    }

    private List<FourDimensionalVertex> listOfVertices(int... indices) {
        return Utility.listOfElementsAt(vertices, indices);
    }

    public List<FourDimensionalVertex> getVertices() {
        return vertices;
    }

    public List<ThreeDimensionalFace> getFaces() {
        return faces;
    }

    public List<Face> intersect(float[] basisVectors, float translation) {
        List<Face> intersections = new ArrayList<>();
        for (ThreeDimensionalFace face : getFaces()) {
            intersections.addAll(face.intersect(basisVectors, translation));
        }
        return intersections;
    }
}
