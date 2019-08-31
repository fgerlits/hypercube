package com.example.simplecube;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Cube {

    public static final int NUMBER_OF_FACES = 6;
    public static final int TRIANGLES_PER_FACE = 2;
    public static final int VERTICES_PER_TRIANGLE = 3;
    public static final int COORDS_PER_VERTEX = 3;  // x, y, z
    public static final int COLORS_PER_VERTEX = 3;  // red, green, blue
    public static final int SIZE_OF_FLOAT = 4;

    private final int program;
    private static int[] PLUS_OR_MINUS_ONE = {-1, 1};
    private final int vertexCount = NUMBER_OF_FACES * TRIANGLES_PER_FACE * VERTICES_PER_TRIANGLE;
    private List<Vertex> vertices;
    private List<Face> faces;

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private float[] g_vertex_buffer_data;
    private float[] g_color_buffer_data;

    public Cube() {
        program = createProgram();

        createVertices();
        createFaces();

        createVertexBuffer();
        createColorBuffer();

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                g_vertex_buffer_data.length * SIZE_OF_FLOAT);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(g_vertex_buffer_data);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);

        // initialize color byte buffer for shape coordinates
        ByteBuffer cb = ByteBuffer.allocateDirect(
                g_color_buffer_data.length * SIZE_OF_FLOAT);
        // use the device hardware's native byte order
        cb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        colorBuffer = cb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        colorBuffer.put(g_color_buffer_data);
        // set the buffer to read the first coordinate
        colorBuffer.position(0);
    }

    private void createVertices() {
        vertices = new ArrayList<>();
        for (int x : PLUS_OR_MINUS_ONE) {
            for (int y : PLUS_OR_MINUS_ONE) {
                for (int z : PLUS_OR_MINUS_ONE) {
                    vertices.add(new Vertex(x, y, z));
                }
            }
        }
    }

    private void createFaces() {
        faces = new ArrayList<>();
        faces.add(new Face(listOfVertices(0, 1, 2, 3), Color.RED));     // left
        faces.add(new Face(listOfVertices(0, 1, 4, 5), Color.GREEN));   // front
        faces.add(new Face(listOfVertices(2, 3, 6, 7), Color.BLUE));    // back
        faces.add(new Face(listOfVertices(0, 2, 4, 6), Color.YELLOW));  // bottom
        faces.add(new Face(listOfVertices(1, 3, 5, 7), Color.CYAN));    // top
        faces.add(new Face(listOfVertices(4, 5, 6, 7), Color.MAGENTA)); // right
    }

    private void createVertexBuffer() {
        g_vertex_buffer_data = new float[NUMBER_OF_FACES * TRIANGLES_PER_FACE * VERTICES_PER_TRIANGLE * COORDS_PER_VERTEX];
        int offset = 0;
        for (Face face : faces) {
            offset = face.writeVerticestoFloatArray(g_vertex_buffer_data, offset);
        }
    }

    private void createColorBuffer() {
        g_color_buffer_data = new float[NUMBER_OF_FACES * TRIANGLES_PER_FACE * VERTICES_PER_TRIANGLE * COLORS_PER_VERTEX];
        int offset = 0;
        for (Face face : faces) {
            for (int i = 0; i < TRIANGLES_PER_FACE; ++i) {
                for (int j = 0; j < VERTICES_PER_TRIANGLE; ++j) {
                    offset = face.writeColorToFloatArray(g_color_buffer_data, offset);
                }
            }
        }
    }

    private static int createProgram() {
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                MyGLRenderer.vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                MyGLRenderer.fragmentShaderCode);

        // create empty OpenGL ES Program
        int program = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(program, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(program, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(program);

        return program;
    }

    private int positionHandle;
    private int colorHandle;
    private int vPMatrixHandle;

    private List<Vertex> listOfVertices(int... indices) {
        List<Vertex> list = new ArrayList<>();
        for (int index : indices) {
            list.add(vertices.get(index));
        }
        return list;
    }
    private final int vertexStride = COORDS_PER_VERTEX * SIZE_OF_FLOAT;
    private final int colorStride = COLORS_PER_VERTEX * SIZE_OF_FLOAT;

    public void draw(float[] vPMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program);

        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        // Enable a handle to the cube vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the cube coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to vertex shader's vColor member
        colorHandle = GLES20.glGetAttribLocation(program, "vColor");

        // Enable a handle to the cube vertices
        GLES20.glEnableVertexAttribArray(colorHandle);

        // Prepare the cube coordinate data
        GLES20.glVertexAttribPointer(colorHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                colorStride, colorBuffer);

        // get handle to shape's transformation matrix
        vPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle,1, false, vPMatrix, 0);

        // Draw the cube
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0, vertexCount);

        // Release the handles
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(colorHandle);
        // should we release the matrix handle, too?
    }
}
