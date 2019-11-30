package ferenc_gerlits.hypercube_viewer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer renderer;

    private float previousX;
    private float previousY;

    public MyGLSurfaceView(Context context, AttributeSet attributes) {
        super(context, attributes);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer();
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

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

    public void resetXYZ() {
        renderer.resetXYZ();
    }

    public void setTranslation(double translation) {
        renderer.setTranslation(translation);
    }

    public void setRotationWX(double rotation) {
        renderer.setRotationWX(rotation);
    }

    public void setRotationWY(double rotation) {
        renderer.setRotationWY(rotation);
    }

    public void setRotationWZ(double rotation) {
        renderer.setRotationWZ(rotation);
    }
}
