package rna.FinalProjectAliceandRoma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestResult extends AppCompatActivity {

    String result;
    TextView testResult;
    Toolbar toolbar;
    DatabaseReference databaseResults;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        init();

        databaseResults = FirebaseDatabase.getInstance().getReference("tests");

        result = getIntent().getExtras().getString("result");
        testResult = findViewById(R.id.testResult);
        testResult.setText(result);
        goBack = findViewById(R.id.button_go_back);

        addResult();

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), BottomNavigation.class);
                startActivity(i);

            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(getApplicationContext(), BottomNavigation.class);
        startActivity(i);

    }

    private void addResult() {

        databaseResults.child(user.getUid()).child(getIntent().getExtras().getString("title").trim()).setValue(getIntent().getExtras().getString("result").trim());

    }

    private void init() {

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("title"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
