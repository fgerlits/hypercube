package ferenc_gerlits.hypercube_viewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TwoDimensionalFace {
    private List<FourDimensionalVertex> vertices;
    private List<FourDimensionalEdge> edges;

    public TwoDimensionalFace(List<FourDimensionalVertex> vertices) {
        if (vertices.size() < 3) {
            throw new IllegalArgumentException("Face with only " + vertices.size() + " vertices; must be >= 3");
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

    public List<Edge> intersect(float[] basisVectors, float translation) {
        return Collections.emptyList();     // TODO
    }

    public boolean liesInTheHyperplane(float[] basisVectors, float translation) {
        float[] normalVector = new float[]{basisVectors[0], basisVectors[1], basisVectors[2], basisVectors[3]};
        for (FourDimensionalVertex vertex : vertices) {
            if (! vertex.liesInTheHyperplane(normalVector, translation)) {
                return false;
            }
        }
        return true;
    }

    public List<Vertex> verticesProjectedToHyperplane(float[] basisVectors) {
        List<Vertex> projections = new ArrayList<>();
        for (FourDimensionalVertex vertex : vertices) {
            projections.add(vertex.projectedToHyperplane(basisVectors));
        }
        return projections;
    }
}
