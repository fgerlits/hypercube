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
        faces.add(new TwoDimensionalFace(listOfVertices(reorder(0, 1, 2, 3))));
        faces.add(new TwoDimensionalFace(listOfVertices(reorder(4, 5, 6, 7))));
        faces.add(new TwoDimensionalFace(listOfVertices(reorder(0, 1, 4, 5))));
        faces.add(new TwoDimensionalFace(listOfVertices(reorder(2, 3, 6, 7))));
        faces.add(new TwoDimensionalFace(listOfVertices(reorder(0, 2, 4, 6))));
        faces.add(new TwoDimensionalFace(listOfVertices(reorder(1, 3, 5, 7))));
    }

    private static int[] reorder(int... verticesInLexicographicOrder) {
        int[] verticesAroundTheFace = new int[]{
                verticesInLexicographicOrder[0],
                verticesInLexicographicOrder[2],
                verticesInLexicographicOrder[3],
                verticesInLexicographicOrder[1]
        };
        return verticesAroundTheFace;
    }

    private List<FourDimensionalVertex> listOfVertices(int... indices) {
        return Utility.listOfElementsAt(vertices, indices);
    }

    public List<Face> intersect(Hyperplane hyperplane) {
        if (this.liesInTheHyperplane(hyperplane)) {
            return this.projectedToHyperplane(hyperplane);
        }
        else {
            List<Edge> edges = new ArrayList<>();
            for (TwoDimensionalFace face : faces) {
                if (face.liesInTheHyperplane(hyperplane)) {
                    // if only one 2-face lies in the hyperplane but not the whole 3-face,
                    // then we ignore it in order to prevent double counting
                    // FIXME: this causes a hole in the model in some edge cases
                    return Collections.emptyList();
                } else {
                    edges.addAll(face.intersect(hyperplane));
                }
            }

            if (edges.size() >= 3) {
                return Collections.singletonList(new Face(edges, color, 0));
            } else {
                return Collections.emptyList();
            }
        }
    }

    private boolean liesInTheHyperplane(Hyperplane hyperplane) {
        return Utility.allVerticesLieInTheHyperplane(vertices, hyperplane);
    }

    private List<Face> projectedToHyperplane(Hyperplane hyperplane) {
        List<Face> projections = new ArrayList<>();
        for (TwoDimensionalFace face : faces) {
            projections.add(new Face(face.verticesProjectedToHyperplane(hyperplane), color));
        }
        return projections;
    }
}
