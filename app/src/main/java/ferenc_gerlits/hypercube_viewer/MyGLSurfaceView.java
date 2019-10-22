package ferenc_gerlits.hypercube_viewer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

class MyGLSurfaceView extends GLSurfaceView {

    public static final double RIGHT_ANGLE = Math.PI / 2;

    private final MyGLRenderer renderer;

    public MyGLSurfaceView(Context context, AttributeSet attributes) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer(context, this);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private float previousX;
    private float previousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - previousX;
                float dy = y - previousY;

                // not sure why we need to reverse them, but it works better this way
                renderer.dragHorizontal(-dx);
                renderer.dragVertical(-dy);

                requestRender();
        }

        previousX = x;
        previousY = y;
        return true;
    }

    public void setTranslation(int position) {
        renderer.setTranslation(scaleToBetweenPlusAndMinusX(position, 2));
        requestRender();
    }

    public void setRotationWX(int position) {
        renderer.setRotationWX(scaleToBetweenPlusAndMinusX(position, RIGHT_ANGLE));
        requestRender();
    }

    public void setRotationWY(int position) {
        renderer.setRotationWY(scaleToBetweenPlusAndMinusX(position, RIGHT_ANGLE));
        requestRender();
    }

    public void setRotationWZ(int position) {
        renderer.setRotationWZ(scaleToBetweenPlusAndMinusX(position, RIGHT_ANGLE));
        requestRender();
    }

    private double scaleToBetweenPlusAndMinusX(int position, double x) {
        return (position - 500) / 500.0 * x;
    }
}
