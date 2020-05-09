package rna.FinalProjectAliceandRoma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    public EditText login_email, login_password;
    Button button_login;
    TextView signUp;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);
        button_login = (Button) findViewById(R.id.button_login);
        signUp = (TextView) findViewById(R.id.textViewSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);

            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = login_email.getText().toString();
                String userPassword = login_password.getText().toString();

                if (userEmail.isEmpty() && userPassword.isEmpty()) {

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout), "Fields Empty!", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else if (userEmail.isEmpty()) {

                    login_email.setError("Check your email");
                    login_email.requestFocus();

                } else if (userPassword.isEmpty()) {

                    login_password.setError("Enter password!");
                    login_password.requestFocus();

                } else if (!(userEmail.isEmpty() && userPassword.isEmpty())) {

                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (!task.isSuccessful()) {

                                Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout), "Login is unsuccessful: " + task.getException().getMessage(), Snackbar.LENGTH_LONG);
                                snackbar.show();

                            } else startActivity(new Intent(Login.this, BottomNavigation.class));

                        }
                    });

                } else {

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout), "Error!", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }

            }
        });
    }
}
