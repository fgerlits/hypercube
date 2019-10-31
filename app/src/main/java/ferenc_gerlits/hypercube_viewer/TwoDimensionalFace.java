package ferenc_gerlits.hypercube_viewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TwoDimensionalFace {
    private List<FourDimensionalVertex> vertices;
    private List<FourDimensionalEdge> edges;

    public TwoDimensionalFace(List<FourDimensionalVertex> vertices) {
        if (vertices.size() < 3) {
            throw new IllegalArgumentException("TwoDimensionalFace constructor called with " + vertices.size() +
                    " vertices; we need at least three");
        }

        this.vertices = vertices;

        createEdges(vertices);
    }

    private void createEdges(List<FourDimensionalVertex> vertices) {
        edges = new ArrayList<>();
        FourDimensionalVertex from = vertices.get(vertices.size() - 1);
        for (FourDimensionalVertex to : vertices) {
            edges.add(new FourDimensionalEdge(from, to));
            from = to;
        }
    }

    public List<Edge> intersect(Hyperplane hyperplane) {
        List<FourDimensionalVertex> intersections = new ArrayList<>();
        for (FourDimensionalEdge edge : edges) {
            intersections.addAll(edge.intersect(hyperplane));
        }

        if (intersections.size() == 2) {
            return Collections.singletonList(
                    new Edge(intersections.get(0).projectedToHyperplane(hyperplane),
                            intersections.get(1).projectedToHyperplane(hyperplane))
            );
        } else {
            // TODO: log some diagnostics if intersections.size() > 2; should never happen
            return Collections.emptyList();
        }
    }

    public boolean liesInTheHyperplane(Hyperplane hyperplane) {
        for (FourDimensionalVertex vertex : vertices) {
            if (!vertex.liesInTheHyperplane(hyperplane)) {
                return false;
            }
        }
        return true;
    }

    public List<Vertex> verticesProjectedToHyperplane(Hyperplane hyperplane) {
        List<Vertex> projections = new ArrayList<>();
        for (FourDimensionalVertex vertex : vertices) {
            projections.add(vertex.projectedToHyperplane(hyperplane));
        }
        return projections;
    }
}
