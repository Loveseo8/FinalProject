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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerViewAdapter(myWords, getContext());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void addWord() {

        String word = editTextWord.getText().toString().trim();

        if (TextUtils.isEmpty(word)) {

            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.dictionary_layout), "Enter word!", Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {

            String id = databaseWords.push().getKey();
            Word word1 = new Word(word, id, user.getUid());
            databaseWords.child(user.getUid()).child(id).setValue(word1);

            myWords.add(word1.getWord());

            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.dictionary_layout), "Word added!", Snackbar.LENGTH_LONG);
            snackbar.show();

        }

    }

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_LONG).show();

    }

}

class Word {

    String word;
    String wordID;
    String userID;

    public Word() {
    }

    public Word(String word, String wordID, String userID) {

        this.word = word;
        this.wordID = wordID;
        this.userID = userID;

    }

    public String getWord() {

        return word;

    }

    public String getWordID() {

        return wordID;

    }

    public String getUserID() {

        return userID;

    }
}
