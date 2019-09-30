package ferenc_gerlits.hypercube_viewer;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class HyperCubeDrawer {
    public static final int COORDS_PER_VERTEX = 3;  // x, y, z
    public static final int COLORS_PER_VERTEX = 3;  // red, green, blue
    public static final int SIZE_OF_FLOAT = 4;

    public static int[] PLUS_OR_MINUS_ONE = {-1, 1};
    private final int vertexStride = COORDS_PER_VERTEX * SIZE_OF_FLOAT;
    private final int colorStride = COLORS_PER_VERTEX * SIZE_OF_FLOAT;
    private int program;
    private HyperCube hyperCube;
    private int positionHandle;
    private int colorHandle;
    private int vPMatrixHandle;

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

    public void draw(float[] vPMatrix, float[] normalVector, float translation) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program);

        List<Face> intersections = new ArrayList<>();
        for (ThreeDimensionalFace face : hyperCube.getFaces()) {
            intersections.addAll(face.intersect(normalVector, translation));
        }

        int vertexCount = 0;                // TODO

        FloatBuffer vertexBuffer = null;    // TODO
        createPositionHandle(vertexBuffer);

        FloatBuffer colorBuffer = null;     // TODO
        createColorHandle(colorBuffer);

        createVPMatrixHandle(vPMatrix);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        releaseVertexAttribArrays();
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
