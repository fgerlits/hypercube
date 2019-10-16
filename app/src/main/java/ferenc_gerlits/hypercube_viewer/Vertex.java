package ferenc_gerlits.hypercube_viewer;

import static ferenc_gerlits.hypercube_viewer.Utility.EPSILON;

public class Vertex {
    private float x;
    private float y;
    private float z;

    public Vertex(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int writeToFloatArray(float[] array, int offset) {
        array[offset++] = x;
        array[offset++] = y;
        array[offset++] = z;
        return offset;
    }

    public boolean approximatelyEquals(Vertex other) {
        if (this == other) return true;
        if (other == null) return false;

        return Math.abs(other.x - x) +
                Math.abs(other.y - y) +
                Math.abs(other.z - z) < EPSILON;
    }
}
