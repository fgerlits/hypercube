package ferenc_gerlits.hypercube_viewer;

import static ferenc_gerlits.hypercube_viewer.Utility.EPSILON;

public class Vertex {
    private double x;
    private double y;
    private double z;

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int writeToFloatArray(float[] array, int offset) {
        array[offset++] = (float) x;
        array[offset++] = (float) y;
        array[offset++] = (float) z;
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
