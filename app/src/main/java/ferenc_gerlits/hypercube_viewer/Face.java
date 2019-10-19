package ferenc_gerlits.hypercube_viewer;

import java.util.ArrayList;
import java.util.List;

public class Face {
    private List<Vertex> vertices;
    private Color color;

    public static final int VERTICES_PER_TRIANGLE = 3;

    public Face(List<Vertex> vertices, Color color) {
        if (vertices.size() < 3) {
            throw new IllegalArgumentException("Face constructor called with " + vertices.size() +
                    " vertices; we need at least three");
        }

        this.vertices = vertices;
        this.color = color;
    }

    public Face(List<Edge> edges, Color color, int unused) {
        if (edges.size() < 3) {
            throw new IllegalArgumentException("Face constructor called with " + edges.size() +
                    " edges; we need at least three");
        }

        List<Edge> orderedEdges = order(edges);
        if (orderedEdges.size() < 3) {
            throw new IllegalArgumentException("Face constructor called with " + edges.size() +
                    " edges which don't join up (found " + orderedEdges.size() + " consecutive edges only)");
        }

        vertices = new ArrayList<>();
        for (Edge edge : orderedEdges) {
            vertices.add(edge.getFrom());
        }

        this.color = color;
    }

    private List<Edge> order(List<Edge> edges) {
        List<Edge> copy = new ArrayList(edges);
        List<Edge> orderedEdges = new ArrayList<>();

        Edge latestEdge = copy.get(0);
        orderedEdges.add(latestEdge);
        copy.remove(0);

        while (!copy.isEmpty()) {
            int i = findEdgeStartingAt(copy, latestEdge.getTo());
            if (i != -1) {
                latestEdge = copy.get(i);
                orderedEdges.add(latestEdge);
                copy.remove(i);
            } else {
                i = findEdgeEndingAt(copy, latestEdge.getTo());
                if (i != -1) {
                    latestEdge = copy.get(i).reverse();
                    orderedEdges.add(latestEdge);
                    copy.remove(i);
                } else {
                    // TODO: log some diagnostics
                    break;
                }
            }
        }

        return orderedEdges;
    }

    private int findEdgeStartingAt(List<Edge> edges, Vertex vertex) {
        for (int i = 0; i < edges.size(); ++i) {
            if (edges.get(i).getFrom().approximatelyEquals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    private int findEdgeEndingAt(List<Edge> edges, Vertex vertex) {
        for (int i = 0; i < edges.size(); ++i) {
            if (edges.get(i).getTo().approximatelyEquals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    public int writeVerticestoFloatArray(float[] array, int offset) {
        for (int i = 0; i < numberOfTriangles(); ++i) {
            offset = vertices.get(0).writeToFloatArray(array, offset);
            offset = vertices.get(i + 1).writeToFloatArray(array, offset);
            offset = vertices.get(i + 2).writeToFloatArray(array, offset);
        }
        return offset;
    }

    public int numberOfTriangles() {
        return vertices.size() - 2;
    }

    public int writeColorToFloatArray(float[] array, int offset) {
        for (int i = 0; i < numberOfTriangles(); ++i) {
            for (int j = 0; j < VERTICES_PER_TRIANGLE; ++j) {
                offset = color.writeToFloatArray(array, offset);
            }
        }
        return offset;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public Color getColor() {
        return color;
    }
}
