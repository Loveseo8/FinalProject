package rna.FinalProjectAliceandRoma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BottomNavigation extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {

                    Intent i = new Intent(BottomNavigation.this, Login.class);
                    startActivity(i);

                }
            }
        };

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        loadFragment(new BooksListFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()) {

                    case R.id.books:
                        fragment = new BooksListFragment();
                        break;
                    case R.id.tests:
                        fragment = new TestsListFragment();
                        break;
                    case R.id.settings:
                        fragment = new SettingsListFragment();
                        break;
                    case R.id.dictionary:
                        fragment = new DictionaryFragment();
                        break;

                }

                return loadFragment(fragment);

            }
        });

    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;

        }

        return false;

    }

    protected void onStart() {

        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to quit?");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {

                finishAffinity();

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
