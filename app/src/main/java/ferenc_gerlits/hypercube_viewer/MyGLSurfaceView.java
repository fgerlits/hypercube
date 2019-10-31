package ferenc_gerlits.hypercube_viewer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

class MyGLSurfaceView extends GLSurfaceView {

    public static final double DISTANCE_OF_VERTICES_FROM_THE_ORIGIN = 2;
    public static final double RIGHT_ANGLE = Math.PI / 2;
    public static final double HALF_OF_MAX_RANGE_OF_SLIDERS = 500;

    private final MyGLRenderer renderer;

    public MyGLSurfaceView(Context context, AttributeSet attributes) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer(context, this);
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private float previousX;
    private float previousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
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
        renderer.setTranslation(scaleToBetweenPlusAndMinusX(position, DISTANCE_OF_VERTICES_FROM_THE_ORIGIN));
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
        return (position - HALF_OF_MAX_RANGE_OF_SLIDERS) / HALF_OF_MAX_RANGE_OF_SLIDERS * x;
    }
}
