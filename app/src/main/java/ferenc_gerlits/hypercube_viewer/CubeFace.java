package ferenc_gerlits.hypercube_viewer;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class to reorder vertices of a square face.
 * When creating the cube, the natural order is (-1, -1), (-1, 1), (1, -1), (1, 1),
 * but when drawing it, we need adjacent vertices to form an edge.
 */
public class CubeFace extends Face {
    public CubeFace(List<Vertex> vertices, Color color) {
        super(reorder(vertices), color);
    }

    private static List<Vertex> reorder(List<Vertex> verticesInLexicographicOrder) {
        List<Vertex> verticesAroundTheFace = new ArrayList<>();
        verticesAroundTheFace.add(verticesInLexicographicOrder.get(0));
        verticesAroundTheFace.add(verticesInLexicographicOrder.get(2));
        verticesAroundTheFace.add(verticesInLexicographicOrder.get(3));
        verticesAroundTheFace.add(verticesInLexicographicOrder.get(1));
        return verticesAroundTheFace;
    }
}
