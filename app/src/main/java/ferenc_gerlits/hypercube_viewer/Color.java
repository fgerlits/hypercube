package ferenc_gerlits.hypercube_viewer;

public enum Color {
    RED(1, 0, 0),
    GREEN(0, 1, 0),
    BLUE(0, 0, 1),
    YELLOW(1, 1, 0),
    MAGENTA(1, 0, 1),
    CYAN(0, 1, 1);

    private float red;
    private float green;
    private float blue;

    Color(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int writeToFloatArray(float[] array, int offset) {
        array[offset++] = red;
        array[offset++] = green;
        array[offset++] = blue;
        return offset;
    }
}
