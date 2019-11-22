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

    private void reset() {
        surfaceView.resetXYZ();
        translation.setProgress((int)MyGLRenderer.HALF_OF_MAX_RANGE_OF_SLIDERS);
        rotateWX.setProgress((int)MyGLRenderer.HALF_OF_MAX_RANGE_OF_SLIDERS);
        rotateWY.setProgress((int)MyGLRenderer.HALF_OF_MAX_RANGE_OF_SLIDERS);
        rotateWZ.setProgress((int)MyGLRenderer.HALF_OF_MAX_RANGE_OF_SLIDERS);
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

    public SeekBar getTranslation() {
        return translation;
    }

    public SeekBar getRotateWX() {
        return rotateWX;
    }

    public SeekBar getRotateWY() {
        return rotateWY;
    }

    public SeekBar getRotateWZ() {
        return rotateWZ;
    }
}
