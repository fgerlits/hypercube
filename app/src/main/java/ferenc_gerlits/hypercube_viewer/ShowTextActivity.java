package ferenc_gerlits.hypercube_viewer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowTextActivity extends AppCompatActivity {

    public static final String TITLE = "ferenc_gerlits.hypercube_viewer.TITLE";
    public static final String TEXT = "ferenc_gerlits.hypercube_viewer.TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);

        Intent intent = getIntent();
        String title = intent.getStringExtra(TITLE);
        String text = intent.getStringExtra(TEXT);

        setTitle(title);

        TextView textView = findViewById(R.id.textView);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        Spanned formattedText = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        textView.setText(formattedText);
    }
}
