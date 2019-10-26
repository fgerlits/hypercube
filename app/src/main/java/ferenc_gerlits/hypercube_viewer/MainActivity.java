package ferenc_gerlits.hypercube_viewer;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

    private SeekBar translation;
    private SeekBar rotateWX;
    private SeekBar rotateWY;
    private SeekBar rotateWZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        translation = findViewById(R.id.translation);
        rotateWX = findViewById(R.id.rotateWX);
        rotateWY = findViewById(R.id.rotateWY);
        rotateWZ = findViewById(R.id.rotateWZ);
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
