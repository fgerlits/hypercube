package ferenc_gerlits.hypercube_viewer;

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
}
