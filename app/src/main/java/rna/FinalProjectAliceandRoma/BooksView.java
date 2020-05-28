package rna.FinalProjectAliceandRoma;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BooksView extends AppCompatActivity {

    WebView pdf_view;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_view_activity);

        init();

        pdf_view = findViewById(R.id.book_view);
        pdf_view.loadUrl("file:///android_asset/booksTopics/" + getIntent().getExtras().getString("title") + ".html");

    }

    private void init() {

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("title"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
