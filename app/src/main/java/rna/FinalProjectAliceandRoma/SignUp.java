package rna.FinalProjectAliceandRoma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    public EditText get_email, get_password;
    Button get_signUp;
    TextView signIn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        get_email = findViewById(R.id.input_email);
        get_password = findViewById(R.id.input_password);
        get_signUp = findViewById(R.id.button_signUp);
        signIn = findViewById(R.id.textViewSignIn);

        get_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailID = get_email.getText().toString();
                String password = get_password.getText().toString();

                if (emailID.isEmpty() && password.isEmpty()) {

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.sign_up_layout), "Поля пустые!", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else if (emailID.isEmpty()) {

                    get_email.setError("Введите почту!");
                    get_email.requestFocus();

                } else if (password.isEmpty()) {

                    get_password.setError("Введите пароль!");
                    get_password.requestFocus();

                } else if (!(emailID.isEmpty() && password.isEmpty())) {

                    firebaseAuth.createUserWithEmailAndPassword(emailID, password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {

                                Snackbar snackbar = Snackbar.make(findViewById(R.id.sign_up_layout), "Регистрация провалена : " + task.getException().getMessage(), Snackbar.LENGTH_LONG);
                                snackbar.show();

                            } else {

                                Intent i = new Intent(SignUp.this, Login.class);
                                startActivity(i);

                            }
                        }
                    });

                } else {

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.sign_up_layout), "Ошибка!", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SignUp.this, Login.class);
                startActivity(i);

            }
        });

    }
}
