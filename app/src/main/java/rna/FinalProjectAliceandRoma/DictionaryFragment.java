package rna.FinalProjectAliceandRoma;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DictionaryFragment extends Fragment {

    DatabaseReference wordsDatabase;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Button add_word;
    EditText editTextWord;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wordsDatabase = FirebaseDatabase.getInstance().getReference();

        add_word = (Button) view.findViewById(R.id.button_add_word);
        editTextWord = (EditText) view.findViewById(R.id.editTextWord);
        add_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = editTextWord.getText().toString();
                String [] word = input.split(" ");
                writeNewWord(word[0], word[1]);

            }
        });

    }

    private void writeNewWord(String word, String translation) {


        String key = wordsDatabase.child("words").push().getKey();
        Word word1 = new Word(word, translation);
        Map<String, Object> wordValues = word1.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/user-word/" + user.getUid() + "/" + key, wordValues);

        wordsDatabase.updateChildren(childUpdates);

    }
}

class Word {

    String word;
    String translation;

    public Word() {
    }

    public Word(String word, String translation) {

        this.word = word;
        this.translation = translation;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> word = new HashMap<>();

        word.put("word", word);
        word.put("translation", translation);

        return word;

    }

}