package ferenc_gerlits.hypercube_viewer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    // TODO: add controls to move the hyperplane around
    private static final BasisVectors RANDOM_BASIS = new BasisVectors(
            new Vector(0.62340519, 0.34202014, 0.46984631, 0.52309907),
            new Vector(-0.22690093, 0.93969262, -0.17101007, -0.19039249),
            new Vector(-0.38302222, 0, 0.86602540, -0.32139381),
            new Vector(-0.64278761, 0, 0, 0.76604444));

    private static final double TRANSLATION = 0;

    private float horizontalAngle = 0;
    private float verticalAngle = 0;
    private float radiansPerPixel = 1;

    private float[] projectionMatrix = new float[16];

    HyperCubeDrawer hyperCube;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LESS);

        hyperCube = new HyperCubeDrawer();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        radiansPerPixel = (float) (Math.PI / 2 / Math.min(width, height));
        projectionMatrix = createProjectionMatrix(width, height);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        float[] viewMatrix = createViewMatrix();

        float[] modelMatrix = createModelMatrix();

        // Note that the order must be projection * view * model.
        float[] mvpMatrix = multiplyMatrix(multiplyMatrix(projectionMatrix, viewMatrix), modelMatrix);

        hyperCube.draw(mvpMatrix, new Hyperplane(RANDOM_BASIS, TRANSLATION));
    }

    // Set the position of the model
    private float[] createModelMatrix() {
        float[] direction = sphericalToCartesian(horizontalAngle, verticalAngle);
        float[] right = {(float) Math.sin(horizontalAngle - Math.PI / 2),
                0f,
                (float) Math.cos(horizontalAngle - Math.PI / 2)};
        float[] up = crossProduct(right, direction);

        float[] modelMatrix = new float[16];
        Matrix.setLookAtM(modelMatrix, 0,
                0f, 0f, 0f,
                direction[0], direction[1], direction[2],
                up[0], up[1], up[2]);
        return modelMatrix;
    }

    private float[] sphericalToCartesian(float horizontalAngle, float verticalAngle) {
        double distance = 10.0;

        return new float[]{
                (float) (Math.cos(verticalAngle) * Math.sin(horizontalAngle) * distance),
                (float) (Math.sin(verticalAngle) * distance),
                (float) (Math.cos(verticalAngle) * Math.cos(horizontalAngle) * distance)
        };

    }

    // I'm sure there is a library function for this, but couldn't find it
    private float[] crossProduct(float[] a, float[] b) {
        return new float[]{
                a[1] * b[2] - a[2] * b[1],
                a[2] * b[0] - a[0] * b[2],
                a[0] * b[1] - a[1] * b[0]
        };
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
        float[] result = new float[16];
        Matrix.multiplyMM(result, 0, left, 0, right, 0);
        return result;
    }

    // type is either GLES20.GL_VERTEX_SHADER or GLES20.GL_FRAGMENT_SHADER
    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public void dragHorizontal(float dx) {
        horizontalAngle += dx * radiansPerPixel;
    }

    public void dragVertical(float dy) {
        verticalAngle += dy * radiansPerPixel;
    }
}
