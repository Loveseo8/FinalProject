package rna.FinalProjectAliceandRoma;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseWords = FirebaseDatabase.getInstance().getReference("words");

        editTextWord = view.findViewById(R.id.editTextWord);
        add = view.findViewById(R.id.button_add_word);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addWord();

            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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


                myWords.add(ds.getValue().toString());

        }

        adapter = new RecyclerViewAdapter(myWords, getContext());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void addWord() {

        String text_word = editTextWord.getText().toString().trim();

        if (TextUtils.isEmpty(text_word)) {

            editTextWord.setError("Enter word!");
            editTextWord.requestFocus();

        } else if (!text_word.contains("-")) {

            editTextWord.setError("Your entry should contain - sign");
            editTextWord.requestFocus();

        } else {

            String [] title = text_word.split("-");
            String word = title[0];
            databaseWords.child(user.getUid()).child(word).setValue(text_word);
            editTextWord.setText("");

        }

    }

    private void showUpdateDeleteDialog(final String word) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.update_data_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editWord = dialogView.findViewById(R.id.edit_word);
        final Button buttonUpdate = dialogView.findViewById(R.id.button_update_word);
        final Button buttonDelete = dialogView.findViewById(R.id.button_delete_Word);

        dialogBuilder.setTitle(word);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text_word = editWord.getText().toString().trim();

                if (TextUtils.isEmpty(text_word)) {

                    editWord.setError("Enter word!");
                    editWord.requestFocus();

                } else if (!text_word.contains("-")) {

                    editWord.setError("Your entry should contain - sign");
                    editWord.requestFocus();

                } else {

                    String [] title = text_word.split("-");
                    String word_title = title[0];

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("words").child(user.getUid()).child(word_title);
                    databaseReference.removeValue();

                    databaseWords.child(user.getUid()).child(word_title).setValue(text_word);
                    editWord.setText("");

                }

                b.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String [] title = word.split("-");
                String word_title = title[0];

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("words").child(user.getUid()).child(word_title);
                databaseReference.removeValue();
                b.dismiss();

            }
        });



    }

    @Override
    public void onItemClick(View view, int position) {

        String selected = adapter.getItem(position);

        String[] title = selected.split("-");
        String selected_title = title[0];

        showUpdateDeleteDialog(databaseWords.child(user.getUid()).child(selected_title).getKey());

    }

}