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

    public static int[] PLUS_OR_MINUS_ONE = {-1, 1};
    private final int vertexStride = COORDS_PER_VERTEX * SIZE_OF_FLOAT;
    private final int colorStride = COLORS_PER_VERTEX * SIZE_OF_FLOAT;

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
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                MyGLRenderer.vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                MyGLRenderer.fragmentShaderCode);

        // create empty OpenGL ES Program
        program = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(program, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(program, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(program);

    }

    public void draw(float[] vPMatrix, float[] basisVectors, float translation) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program);

        List<Face> intersections = hyperCube.intersect(basisVectors, translation);

        int numberOfVertices = countTriangles(intersections) * Face.VERTICES_PER_TRIANGLE;

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

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                vertexBufferData.length * SIZE_OF_FLOAT);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(vertexBufferData);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);

        return vertexBuffer;
    }

    private void createPositionHandle(FloatBuffer vertexBuffer) {
        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        // Enable a handle to the cube vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the cube coordinate data
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

        // initialize color byte buffer for shape coordinates
        ByteBuffer cb = ByteBuffer.allocateDirect(
                colorBufferData.length * SIZE_OF_FLOAT);
        // use the device hardware's native byte order
        cb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        FloatBuffer colorBuffer = cb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        colorBuffer.put(colorBufferData);
        // set the buffer to read the first coordinate
        colorBuffer.position(0);

        return colorBuffer;
    }

    private void createColorHandle(FloatBuffer colorBuffer) {
        // get handle to vertex shader's vColor member
        colorHandle = GLES20.glGetAttribLocation(program, "vColor");

        // Enable a handle to the cube vertices
        GLES20.glEnableVertexAttribArray(colorHandle);

        // Prepare the cube coordinate data
        GLES20.glVertexAttribPointer(colorHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                colorStride, colorBuffer);
    }

    private void createVPMatrixHandle(float[] vPMatrix) {
        // get handle to shape's transformation matrix
        vPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, vPMatrix, 0);
    }

    private void releaseVertexAttribArrays() {
        // Release the handles
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(colorHandle);
    }
}
