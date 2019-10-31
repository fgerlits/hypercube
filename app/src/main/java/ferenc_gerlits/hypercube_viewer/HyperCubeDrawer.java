package ferenc_gerlits.hypercube_viewer;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

public class HyperCubeDrawer {
    public static final int COORDS_PER_VERTEX = 3;  // x, y, z
    public static final int COLORS_PER_VERTEX = 3;  // red, green, blue
    public static final int SIZE_OF_FLOAT = 4;

    private static final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec4 vColor;" +
                    "varying vec4 fragmentColor;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "    gl_Position = uMVPMatrix * vPosition;" +
                    "    fragmentColor = vColor;" +
                    "}";

    private static final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 fragmentColor;" +
                    "void main() {" +
                    "    gl_FragColor = fragmentColor;" +
                    "}";

    private static final int vertexStride = COORDS_PER_VERTEX * SIZE_OF_FLOAT;
    private static final int colorStride = COLORS_PER_VERTEX * SIZE_OF_FLOAT;

    private int program;
    private int positionHandle;
    private int colorHandle;
    private int vPMatrixHandle;

    private HyperCube hyperCube;

    public HyperCubeDrawer() {
        hyperCube = new HyperCube();
        createProgram();
    }

    private void createProgram() {
        program = GLES20.glCreateProgram();

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        GLES20.glAttachShader(program, vertexShader);

        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        GLES20.glAttachShader(program, fragmentShader);

        GLES20.glLinkProgram(program);

    }

    public void draw(float[] vPMatrix, Hyperplane hyperplane) {
        GLES20.glUseProgram(program);

        List<Face> intersections = hyperCube.intersect(hyperplane);

        int numberOfVertices = countTriangles(intersections) * Utility.VERTICES_PER_TRIANGLE;

        FloatBuffer vertexBuffer = createVertexBuffer(intersections, numberOfVertices);
        createPositionHandle(vertexBuffer);

        FloatBuffer colorBuffer = createColorBuffer(intersections, numberOfVertices);
        createColorHandle(colorBuffer);

        createVPMatrixHandle(vPMatrix);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, numberOfVertices);

        releaseVertexAttribArrays();
    }

    private int countTriangles(List<Face> faces) {
        int numberOfTriangles = 0;
        for (Face face : faces) {
            numberOfTriangles += face.numberOfTriangles();
        }
        return numberOfTriangles;
    }

    private FloatBuffer createVertexBuffer(List<Face> faces, int numberOfVertices) {
        float[] vertexBufferData = new float[numberOfVertices * COORDS_PER_VERTEX];

        int offset = 0;
        for (Face face : faces) {
            offset = face.writeVerticestoFloatArray(vertexBufferData, offset);
        }

        ByteBuffer bb = ByteBuffer.allocateDirect(vertexBufferData.length * SIZE_OF_FLOAT);
        bb.order(ByteOrder.nativeOrder());

        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertexBufferData);
        vertexBuffer.position(0);

        return vertexBuffer;
    }

    private void createPositionHandle(FloatBuffer vertexBuffer) {
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        GLES20.glEnableVertexAttribArray(positionHandle);

        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
    }

    private FloatBuffer createColorBuffer(List<Face> faces, int numberOfVertices) {
        float[] colorBufferData = new float[numberOfVertices * COLORS_PER_VERTEX];

        int offset = 0;
        for (Face face : faces) {
            offset = face.writeColorToFloatArray(colorBufferData, offset);
        }

        ByteBuffer cb = ByteBuffer.allocateDirect(colorBufferData.length * SIZE_OF_FLOAT);
        cb.order(ByteOrder.nativeOrder());

        FloatBuffer colorBuffer = cb.asFloatBuffer();
        colorBuffer.put(colorBufferData);
        colorBuffer.position(0);

        return colorBuffer;
    }

    private void createColorHandle(FloatBuffer colorBuffer) {
        colorHandle = GLES20.glGetAttribLocation(program, "vColor");

        GLES20.glEnableVertexAttribArray(colorHandle);

        GLES20.glVertexAttribPointer(colorHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                colorStride, colorBuffer);
    }

    private void createVPMatrixHandle(float[] vPMatrix) {
        vPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");

        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, vPMatrix, 0);
    }

    private void releaseVertexAttribArrays() {
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(colorHandle);
    }
}
