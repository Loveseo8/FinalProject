package rna.FinalProjectAliceandRoma;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DictionaryFragment extends Fragment {

    EditText editTextName;
    Button buttonAddArtist;
    ListView listViewArtists;
    List<NameList> namelists;
    DatabaseReference databaseArtists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseArtists = FirebaseDatabase.getInstance().getReference("namelist");
        editTextName = (EditText) view.findViewById(R.id.editTextWord);
        listViewArtists = (ListView) view.findViewById(R.id.listViewWord);
        buttonAddArtist = (Button) view.findViewById(R.id.button_add_word);

        namelists = new ArrayList<>();
        buttonAddArtist.setOnClickListener(new View.OnClickListener() {

        @Override public void onClick(View view) {
        addnamelist();
        }
        });
        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

        @Override public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        NameList nmlist = namelists.get(i);
        showUpdateDeleteDialog(nmlist.getMyId(), nmlist.getMyName()); return true;
        }
        });
        }

        private void addnamelist() {
        String name = editTextName.getText().toString().trim();

        if (!TextUtils.isEmpty(name)) {
        String id = databaseArtists.push().getKey();
        NameList artist = new NameList(id, name);
        databaseArtists.child(id).setValue(artist);
        editTextName.setText("");
        Toast.makeText(getActivity(), "Name added", Toast.LENGTH_LONG).show();
        } else {
        Toast.makeText(getActivity(), "Please enter a name", Toast.LENGTH_LONG).show();
        }
        }
        @Override
        public void onStart() {
        super.onStart();
        databaseArtists.addValueEventListener(new ValueEventListener() {

        @Override public void onDataChange(DataSnapshot dataSnapshot) {
        namelists.clear();
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
        NameList nmlist = postSnapshot.getValue(NameList.class);
        namelists.add(nmlist); }
        ShowNameList myAdapter = new ShowNameList(getActivity(), namelists);
        listViewArtists.setAdapter(myAdapter);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
        });
        }
        private void showUpdateDeleteDialog(final String myId, String myName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_data_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.edit_word);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.button_update_word);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.button_delete_Word);
        dialogBuilder.setTitle(myName);
        final AlertDialog b = dialogBuilder.create();
        b.show(); buttonUpdate.setOnClickListener(new View.OnClickListener() {

        @Override public void onClick(View view) {
        String name = editTextName.getText().toString().trim(); if (!TextUtils.isEmpty(name)) {
        updateArtist(myId, name); b.dismiss();
        }
        }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        deleteArtist(myId); b.dismiss();
        }
        });
        }
        private boolean updateArtist(String id, String name) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("namelist").child(id);
        NameList nmlist = new NameList(id, name);
        dR.setValue(nmlist);
        Toast.makeText(getContext(), "Name Updated", Toast.LENGTH_LONG).show(); return true;
        }
        private boolean deleteArtist(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("namelist").child(id);
        dR.removeValue();
         Toast.makeText(getContext(), "Name Deleted", Toast.LENGTH_LONG).show();
         return true;
        }
        }

class NameList{

    private String myId;
    private String myName;

    public NameList() { }
    public NameList(String myId, String myName) {
        this.myId = myId;
        this.myName = myName;
    }
    public String getMyId() {
        return myId;
    }
    public String getMyName() { return myName; }
}

class ShowNameList extends ArrayAdapter<NameList> {
    private Activity context;
    List<NameList> namelists;
    public ShowNameList(Activity context, List<NameList> namelists) {
        super(context, R.layout.row, namelists);
        this.context = context;
        this.namelists = namelists;
    } @Override public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.row, parent, false);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.title);
        NameList namelist = namelists.get(position);
        textViewName.setText(namelist.getMyName());
        return listViewItem; }
}
