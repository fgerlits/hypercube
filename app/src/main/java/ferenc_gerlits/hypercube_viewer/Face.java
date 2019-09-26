package ferenc_gerlits.hypercube_viewer;

import java.util.ArrayList;
import java.util.List;

public class Face {
    private List<Vertex> vertices;
    private Color color;

    public Face(List<Vertex> vertices, Color color) {
        this.vertices = vertices;
        this.color = color;
    }

    public int writeVerticestoFloatArray(float[] array, int offset) {
        for (Vertex vertex : listOfVertices(0, 1, 2)) {     // first triangle
            offset = vertex.writeToFloatArray(array, offset);
        }
        for (Vertex vertex : listOfVertices(2, 1, 3)) {     // second triangle
            offset = vertex.writeToFloatArray(array, offset);
        }
        return offset;
    }

    private List<Vertex> listOfVertices(int... indices) {
        List<Vertex> list = new ArrayList<>();
        for (int index : indices) {
            list.add(vertices.get(index));
        }
        return list;
    }

    public int writeColorToFloatArray(float[] array, int offset) {
        return color.writeToFloatArray(array, offset);
    }
}
