package ferenc_gerlits.hypercube_viewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ThreeDimensionalFace {
    private List<FourDimensionalVertex> vertices;
    private List<TwoDimensionalFace> faces;
    private Color color;

    public ThreeDimensionalFace(List<FourDimensionalVertex> vertices, Color color) {
        this.vertices = vertices;
        this.color = color;
        createFaces();
    }

    private void createFaces() {
        faces = new ArrayList<>();
        faces.add(new TwoDimensionalFace(listOfVertices(0, 1, 2, 3)));
        faces.add(new TwoDimensionalFace(listOfVertices(4, 5, 6, 7)));
        faces.add(new TwoDimensionalFace(listOfVertices(0, 1, 4, 5)));
        faces.add(new TwoDimensionalFace(listOfVertices(2, 3, 6, 7)));
        faces.add(new TwoDimensionalFace(listOfVertices(0, 2, 4, 6)));
        faces.add(new TwoDimensionalFace(listOfVertices(1, 3, 5, 7)));
    }

    private List<FourDimensionalVertex> listOfVertices(int... indices) {
        return Utility.listOfElementsAt(vertices, indices);
    }

    public List<Face> intersect(float[] basisVectors, float translation) {
        if (this.liesInTheHyperplane(basisVectors, translation)) {
            return this.projectedToHyperplane(basisVectors);
        }
        else {
            List<Edge> edges = new ArrayList<>();
            for (TwoDimensionalFace face : faces) {
                if (face.liesInTheHyperplane(basisVectors, translation)) {
                    return Collections.singletonList(new Face(face.verticesProjectedToHyperplane(basisVectors), color));
                } else {
                    edges.addAll(face.intersect(basisVectors, translation));
                }
            }
            if (edges.size() >= 3) {
                return Collections.singletonList(new Face(edges));
            } else {
                return Collections.emptyList();
            }
        }
    }

    private boolean liesInTheHyperplane(float[] basisVectors, float translation) {
        float[] normalVector = new float[]{basisVectors[0], basisVectors[1], basisVectors[2], basisVectors[3]};
        for (FourDimensionalVertex vertex : vertices) {
            if (! vertex.liesInTheHyperplane(normalVector, translation)) {
                return false;
            }
        }
        return true;
    }

    private List<Face> projectedToHyperplane(float[] basisVectors) {
        List<Face> projections = new ArrayList<>();
        for (TwoDimensionalFace face : faces) {
            projections.add(new Face(face.verticesProjectedToHyperplane(basisVectors), color));
        }
        return projections;
    }
}
