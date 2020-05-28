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

public class EnterNewUserInfo extends AppCompatActivity {

    EditText editTextDataToChange;
    Button button_next;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter);

        init();

        editTextDataToChange = findViewById(R.id.input);
        button_next = findViewById(R.id.button_next);

        switch (getIntent().getExtras().getString("change")) {

            case "почту":

                editTextDataToChange.setHint(R.string.enter_new_email);
                editTextDataToChange.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                button_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        user.updateEmail(editTextDataToChange.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Intent i = new Intent(EnterNewUserInfo.this, Login.class);
                                    startActivity(i);

                                }
                            }
                        });

                    }

                });

                break;

            case "пароль":

                editTextDataToChange.setHint(R.string.enter_new_password);
                editTextDataToChange.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                button_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        user.updatePassword(editTextDataToChange.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Intent i = new Intent(EnterNewUserInfo.this, Login.class);
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
        getSupportActionBar().setTitle("Изменить " + getIntent().getExtras().getString("change"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Изменить " + getIntent().getExtras().getString("change"));
        builder.setCancelable(false);
        builder.setMessage("Вы не изменили " + getIntent().getExtras().getString("change") + ". " + "Уверены, что хотите выйти?");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {

                EnterNewUserInfo.super.onBackPressed();

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
