package rna.FinalProjectAliceandRoma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);

        loadFragment(new BooksListFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()

            {
                @Override
                public boolean onNavigationItemSelected (@NonNull MenuItem item){

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
}
