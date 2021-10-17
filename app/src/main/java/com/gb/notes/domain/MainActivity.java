package com.gb.notes.domain;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.gb.notes.ui.Fragments.DataManagerFragment;
import com.gb.notes.ui.Fragments.NoteEditFragment;
import com.gb.notes.ui.Fragments.NoteListFragment;
import com.gb.notes.ui.Fragments.NoteViewFragment;
import com.gb.notes.ui.Fragments.ProfileFragment;
import com.gb.notes.ui.Fragments.SettingsFragment;
import com.gb.notes.domain.Interfaces.FragmentsCall;
import com.gb.notes.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentsCall {
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationItemView;
    private final String LOCAL_REPOSITORY_NAME = "repo.bin";




    //TODO модернизировать поиск
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        setNavigation();
        if(getRepo().getAllNotes().isEmpty()) {
        try {
            toInitNotesInRepository();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        }
        fragmentManager = getSupportFragmentManager();
        bottomNavigationItemView.setSelectedItemId(R.id.notes_item_menu);
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.popBackStack();
        }
    }

    private void setNavigation() {
        bottomNavigationItemView = findViewById(R.id.navigation_bar);
        Bundle savedData = new Bundle();
        savedData.putParcelable(NotesRepository.class.getCanonicalName(), getRepo());
        bottomNavigationItemView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.notes_item_menu) {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        fragmentManager.beginTransaction().replace(R.id.container_for_fragment_land_1, NoteListFragment.getInstance(savedData)).commit();
                    } else {
                        fragmentManager.beginTransaction().replace(R.id.container_for_fragment, NoteListFragment.getInstance(savedData)).commit();
                    }
                    fragmentManager.popBackStack();
                } else if (item.getItemId() == R.id.data_manager_item_menu) {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        fragmentManager.beginTransaction().replace(R.id.container_for_fragment_land_1, DataManagerFragment.getInstance(savedData)).commit();
                    } else {
                        fragmentManager.beginTransaction().replace(R.id.container_for_fragment, DataManagerFragment.getInstance(savedData)).commit();
                    }
                    fragmentManager.popBackStack();
                } else if (item.getItemId() == R.id.profile_item_menu) {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        fragmentManager.beginTransaction().replace(R.id.container_for_fragment_land_1, new ProfileFragment()).commit();
                    } else {
                        fragmentManager.beginTransaction().replace(R.id.container_for_fragment, new ProfileFragment()).commit();
                    }
                    fragmentManager.popBackStack();
                }
                return true;
            }
        });
    }

    private void toInitNotesInRepository() throws IOException, ClassNotFoundException {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<NoteEntity> jsonAdapter = moshi.adapter(NoteEntity.class);
        FileInputStream fileInputStream = openFileInput(LOCAL_REPOSITORY_NAME);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        int size = objectInputStream.readInt();
        List<NoteEntity> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String json = (String) objectInputStream.readObject();
            list.add(jsonAdapter.fromJson(json));
            Log.d("@@@", list.toString());
        }
        getRepo().addAll(list);
        Log.d("@@@", "size " + getRepo().getAllNotes().size());
        objectInputStream.close();
        fileInputStream.close();
        Log.d("@@@", "toInitNotesInRepository: ");


    }

    @Override
    public void callEditionFragment(Bundle data) {
        fragmentManager.popBackStack();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.beginTransaction().add(R.id.container_for_fragment_land_2, NoteEditFragment.getInstance(data)).addToBackStack(null).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.container_for_fragment, NoteEditFragment.getInstance(data)).addToBackStack(null).commit();
        }

    }

    @Override
    public void callSettingsFragment() {
        //TODO вписать этот вызов в настройки профиля
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.beginTransaction().replace(R.id.container_for_fragment_land_2, new SettingsFragment()).addToBackStack(null).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.container_for_fragment, new SettingsFragment()).addToBackStack(null).commit();
        }
    }

    @Override
    public void callNoteViewFragment(Bundle data) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.beginTransaction().add(R.id.container_for_fragment_land_2, NoteViewFragment.getInstance(data)).addToBackStack(null).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.container_for_fragment, NoteViewFragment.getInstance(data)).addToBackStack(null).commit();
        }
    }


    private void serializeNotes() throws IOException {
        FileOutputStream fos = openFileOutput(LOCAL_REPOSITORY_NAME, MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<NoteEntity> jsonAdapter = moshi.adapter(NoteEntity.class);
        objectOutputStream.writeInt(getRepo().getAllNotes().size());
        String json = null;
        for (NoteEntity note : getRepo().getAllNotes()) {
            json= jsonAdapter.toJson(note);
            objectOutputStream.writeObject(json);
        }
        objectOutputStream.close();
        fos.close();
        Log.d("@@@", "Записан");

    }
    private NotesRepository getRepo(){
        return ((Application) getApplication()).getRepository();

    }

    @Override
    protected void onStop() {
        try {
            serializeNotes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "До свидания!", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}