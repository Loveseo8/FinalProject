package rna.FinalProjectAliceandRoma;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DictionaryFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    EditText editTextWord;
    Button add;
    DatabaseReference databaseWords;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    RecyclerViewAdapter adapter;
    List<String> myWords = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseWords = FirebaseDatabase.getInstance().getReference("words");

        editTextWord = (EditText) view.findViewById(R.id.editTextWord);
        add = (Button) view.findViewById(R.id.button_add_word);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addWord();

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        databaseWords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {

        myWords.clear();


        for (DataSnapshot ds : dataSnapshot.child(user.getUid()).getChildren()) {

            for (DataSnapshot dataSnapshot1 : ds.getChildren()) {

                myWords.add(dataSnapshot1.getValue().toString());

            }

        }

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerViewAdapter(myWords, getContext());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void addWord() {

        String text_word = editTextWord.getText().toString().trim();

        if (TextUtils.isEmpty(text_word)) {

            editTextWord.setError("Enter word!");
            editTextWord.requestFocus();

        } else {

            String id = databaseWords.push().getKey();
            Word word = new Word(text_word);
            databaseWords.child(user.getUid()).child(id).setValue(word);

        }

    }

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_LONG).show();

    }

}

class Word {

    String word;

    public Word() {
    }

    public Word(String word) {

        this.word = word;

    }

    public String getWord() {

        return word;

    }

    public void setWord(String word) {

        this.word = word;

    }
}
