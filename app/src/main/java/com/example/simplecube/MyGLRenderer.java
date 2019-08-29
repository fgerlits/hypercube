package com.example.simplecube;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    Cube cube;

    public static final String vertexShaderCode =
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

    public static final String fragmentShaderCode =
                    "precision mediump float;" +
                    "varying vec4 fragmentColor;" +
                    //"out vec4 color; " +
                    "void main() {" +
                    "    gl_FragColor = fragmentColor;" +
                    //"    color = fragmentColor;" +
                    "}";

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LESS);

        cube = new Cube();
    }

    private float angle = 0.0f;

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        float[] viewMatrix = createViewMatrix();

        float[] modelMatrix = createModelMatrix();

        // Note that the order must be projection * view * model.
        float[] mvpMatrix = multiplyMatrix(multiplyMatrix(projectionMatrix, viewMatrix), modelMatrix);

        cube.draw(mvpMatrix);
    }

    // Set the position of the model
    private float[] createModelMatrix() {
        float[] rotationMatrix = new float[16];
        Matrix.setRotateM(rotationMatrix, 0, - angle, 0, 0, -1.0f);
        return rotationMatrix;
    }

    // Set the camera position (View matrix)
    private float[] createViewMatrix() {
        float[] viewMatrix = new float[16];
        Matrix.setLookAtM(viewMatrix, 0,
                4, 3, 5,
                0f, 0f, 0f,
                0f, 1.0f, 0.0f);
        return viewMatrix;
    }

    private float[] createProjectionMatrix(float width, float height) {
        float[] projectionMatrix = new float[16];
        Matrix.perspectiveM(projectionMatrix, 0, 45, width / height, 0.1f, 100.0f);
        return projectionMatrix;
    }

    private float[] multiplyMatrix(float[] left, float[] right) {
        float[] mvpMatrix = new float[16];
        Matrix.multiplyMM(mvpMatrix, 0, left, 0, right, 0);
        return mvpMatrix;
    }

    private float[] projectionMatrix = new float[16];

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        projectionMatrix = createProjectionMatrix(width, height);
    }

    // type is either GLES20.GL_VERTEX_SHADER or GLES20.GL_FRAGMENT_SHADER
    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

}
