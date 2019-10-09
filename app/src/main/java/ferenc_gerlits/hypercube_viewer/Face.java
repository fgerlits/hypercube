package ferenc_gerlits.hypercube_viewer;

import java.util.List;

public class Face {
    private List<Vertex> vertices;
    private Color color;

    public Face(List<Vertex> vertices, Color color) {
        if (vertices.size() < 3) {
            throw new IllegalArgumentException("Face constructor called with " + vertices.size() +
                    " vertices; we need at least three");
        }

        this.vertices = vertices;
        this.color = color;
    }

    public Face(List<Edge> edges) {
        if (edges.size() < 3) {
            throw new IllegalArgumentException("Face constructor called with " + edges.size() +
                    " edges; we need at least three");
        }

        // TODO
    }

    public int writeVerticestoFloatArray(float[] array, int offset) {
        for (int i = 1; i < vertices.size() - 1; ++i) {     // write each of the (vertices.size() - 2) triangles
            offset = vertices.get(0).writeToFloatArray(array, offset);
            offset = vertices.get(i).writeToFloatArray(array, offset);
            offset = vertices.get(i + 1).writeToFloatArray(array, offset);
        }
        return offset;
    }

    public int writeColorToFloatArray(float[] array, int offset) {
        return color.writeToFloatArray(array, offset);
    }
}
