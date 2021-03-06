package rna.FinalProjectAliceandRoma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class SettingsListFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.recycler_view, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> settings = new ArrayList<>();

        settings.add("Изменить адрес электронной почты");
        settings.add("Изменить пароль");
        settings.add("Результаты тестов");
        settings.add("Удалить аккаунт");
        settings.add("Выйти");

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerViewAdapter(settings, getContext());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {

        switch (adapter.getItem(position)) {

            case "Результаты тестов":

                Intent i = new Intent(getActivity(), Results.class);
                startActivity(i);

                break;

            case "Выйти":

                new AlertDialog.Builder(getContext()).setCancelable(false).setTitle("Выйти").setMessage(R.string.are_you_sure_you_want_to_log_out).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseAuth.getInstance().signOut();

                        Intent i = new Intent(getActivity(), Login.class);
                        startActivity(i);

                    }
                }).setNeutralButton(R.string.no, null).show();

                break;

            case "Изменить адрес электронной почты":

                new AlertDialog.Builder(getContext()).setCancelable(false).setTitle(R.string.change_email).setMessage(R.string.are_you_sure_you_want_to_change_email).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(getActivity(), EnterNewUserInfo.class);
                        i.putExtra("change", "почту");
                        startActivity(i);

                    }
                }).setNeutralButton(R.string.no, null).show();

                break;

            case "Изменить пароль":

                new AlertDialog.Builder(getContext()).setCancelable(false).setTitle(R.string.change_password).setMessage(R.string.are_you_sure_you_want_to_change_password).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(getActivity(), EnterNewUserInfo.class);
                        i.putExtra("change", "пароль");
                        startActivity(i);

                    }
                }).setNeutralButton(R.string.no, null).show();

                break;

            case "Удалить аккаунт":

                new AlertDialog.Builder(getContext()).setCancelable(false).setTitle(R.string.delete_account).setMessage(R.string.are_you_sure_you_want_to_delete_account).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {


                   @Override
                    public void onClick(DialogInterface dialog, int which) {

                       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                       user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()) {

                                   Intent i = new Intent(getActivity(), Login.class);
                                   startActivity(i);

                               }
                           }
                       });

                    }
                }).setNeutralButton(R.string.no, null).show();

                break;
        }
    }
}
