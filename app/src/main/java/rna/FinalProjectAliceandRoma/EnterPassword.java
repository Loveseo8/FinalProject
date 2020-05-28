package rna.FinalProjectAliceandRoma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EnterPassword extends AppCompatActivity {

    EditText enter;
    Button next;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter);

        init();

        enter = findViewById(R.id.input);
        next = findViewById(R.id.button_next);

        switch (getIntent().getExtras().getString("change")) {

            case "email":

                enter.setHint(R.string.enter_new_email);
                enter.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        user.updateEmail(enter.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Intent i = new Intent(EnterPassword.this, Login.class);
                                    startActivity(i);

                                }
                            }
                        });

                    }

                });

                break;

            case "password":

                enter.setHint(R.string.enter_new_password);
                enter.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        user.updatePassword(enter.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Intent i = new Intent(EnterPassword.this, Login.class);
                                    startActivity(i);

                                }
                            }
                        });

                    }
                });

                break;

        }

    }

    private void init() {

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change " + getIntent().getExtras().getString("change"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change " + getIntent().getExtras().getString("change"));
        builder.setCancelable(false);
        builder.setMessage("You did`t change your " + getIntent().getExtras().getString("change") + ". " + "Are you sure you want to quit?");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {

                EnterPassword.super.onBackPressed();

            }
        });
        builder.setNeutralButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
