package rna.FinalProjectAliceandRoma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EnterPassword extends AppCompatActivity {

    EditText enter_password;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_your_password);

        enter_password = findViewById(R.id.enter_password);
        next = findViewById(R.id.button_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                switch (getIntent().getExtras().getString("change")) {

                    case "email":

                    user.updateEmail(enter_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Intent i = new Intent(EnterPassword.this, Login.class);
                                startActivity(i);

                            }
                        }
                    });

                    break;

                    case "password":

                        user.updatePassword(enter_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Intent i = new Intent(EnterPassword.this, Login.class);
                                    startActivity(i);

                                }
                            }
                        });

                        break;

                }
            }
        });
    }
}
