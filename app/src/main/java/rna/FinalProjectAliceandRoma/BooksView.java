package rna.FinalProjectAliceandRoma;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BooksView extends AppCompatActivity {

    WebView pdf_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_view_activity);

        pdf_view = (WebView) findViewById(R.id.book_view);
        pdf_view.loadUrl("file:///android_asset/topics/" + getIntent().getExtras().getString("title") + ".html");

    }
}
