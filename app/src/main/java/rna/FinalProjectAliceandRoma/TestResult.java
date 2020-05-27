package rna.FinalProjectAliceandRoma;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TestResult extends AppCompatActivity {

    String result;
    TextView testResult;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        init();

        result = getIntent().getExtras().getString("result");
        testResult = (TextView) findViewById(R.id.testResult);
        testResult.setText(result);

    }

    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Test Result");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
