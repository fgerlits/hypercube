package ferenc_gerlits.hypercube_viewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import static ferenc_gerlits.hypercube_viewer.ShowTextActivity.TEXT;
import static ferenc_gerlits.hypercube_viewer.ShowTextActivity.TITLE;

public class MainActivity extends AppCompatActivity {

    private static final double DISTANCE_OF_VERTICES_FROM_THE_ORIGIN = 2;
    private static final double RIGHT_ANGLE = Math.PI / 2;
    private static final int HALF_OF_MAX_RANGE_OF_SLIDERS = 500;

    private MyGLSurfaceView surfaceView;
    private SeekBar translation;
    private SeekBar rotateWX;
    private SeekBar rotateWY;
    private SeekBar rotateWZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.myGLSurfaceView);
        translation = findViewById(R.id.translation);
        rotateWX = findViewById(R.id.rotateWX);
        rotateWY = findViewById(R.id.rotateWY);
        rotateWZ = findViewById(R.id.rotateWZ);

        attachHandlers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                reset();
                return true;
            case R.id.help:
                showHelp();
                return true;
            case R.id.about:
                showAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void attachHandlers() {
        translation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean byUser) {
                surfaceView.setTranslation(scaleToBetweenPlusAndMinusX(position, DISTANCE_OF_VERTICES_FROM_THE_ORIGIN));
                surfaceView.requestRender();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { /* ignore */ }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { /* ignore */ }
        });

        rotateWX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean byUser) {
                surfaceView.setRotationWX(scaleToBetweenPlusAndMinusX(position, RIGHT_ANGLE));
                surfaceView.requestRender();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { /* ignore */ }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { /* ignore */ }
        });

        rotateWY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean byUser) {
                surfaceView.setRotationWY(scaleToBetweenPlusAndMinusX(position, RIGHT_ANGLE));
                surfaceView.requestRender();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { /* ignore */ }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { /* ignore */ }
        });

        rotateWZ.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean byUser) {
                surfaceView.setRotationWZ(scaleToBetweenPlusAndMinusX(position, RIGHT_ANGLE));
                surfaceView.requestRender();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { /* ignore */ }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { /* ignore */ }
        });
    }

    private static double scaleToBetweenPlusAndMinusX(double position, double x) {
        return (position - HALF_OF_MAX_RANGE_OF_SLIDERS) / HALF_OF_MAX_RANGE_OF_SLIDERS * x;
    }

    private void reset() {
        surfaceView.resetXYZ();
        translation.setProgress(HALF_OF_MAX_RANGE_OF_SLIDERS);
        rotateWX.setProgress(HALF_OF_MAX_RANGE_OF_SLIDERS);
        rotateWY.setProgress(HALF_OF_MAX_RANGE_OF_SLIDERS);
        rotateWZ.setProgress(HALF_OF_MAX_RANGE_OF_SLIDERS);
    }

    private void showHelp() {
        Intent intent = new Intent(this, ShowTextActivity.class);
        intent.putExtra(TITLE, getString(R.string.menu_help));
        intent.putExtra(TEXT, getString(R.string.help_text));
        startActivity(intent);
    }

    private void showAbout() {
        Intent intent = new Intent(this, ShowTextActivity.class);
        intent.putExtra(TITLE, getString(R.string.menu_about));
        intent.putExtra(TEXT, getString(R.string.about_text));
        startActivity(intent);
    }
}
