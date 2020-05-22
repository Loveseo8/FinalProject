package rna.FinalProjectAliceandRoma;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

        editTextWord = (EditText) view.findViewById(R.id.editTextWord);
        add = (Button) view.findViewById(R.id.button_add_word);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addWord();

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
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

                myWords.add(ds.getKey());

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

        } else {

            databaseWords.child(user.getUid()).child(text_word).setValue(text_word);
            editTextWord.setText("");

        }

    }

    private void showUpdateDeleteDialog(final String word) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.update_data_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editWord = (EditText) dialogView.findViewById(R.id.edit_word);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.button_update_word);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.button_delete_Word);

        dialogBuilder.setTitle(word);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("words").child(user.getUid()).child(word);
                databaseReference.removeValue();

                String text_word = editWord.getText().toString().trim();

                if (TextUtils.isEmpty(text_word)) {

                    editTextWord.setError("Enter word!");
                    editTextWord.requestFocus();

                } else {

                    databaseWords.child(user.getUid()).child(text_word).setValue(text_word);
                    editTextWord.setText("");

                }

                b.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("words").child(user.getUid()).child(word);
                databaseReference.removeValue();
                b.dismiss();

            }
        });



    }

    @Override
    public void onItemClick(View view, int position) {

        showUpdateDeleteDialog(databaseWords.child(user.getUid()).child(adapter.getItem(position)).getKey());

    }

}