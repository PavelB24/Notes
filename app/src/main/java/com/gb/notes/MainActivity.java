package com.gb.notes;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentsCall {
    private FragmentManager fragmentManager;
    private NotesRepository repository;
    private BottomNavigationView bottomNavigationItemView;
    private final String LOCAL_REPOSITORY_NAME = "repo.bin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        bottomNavigationItemView= findViewById(R.id.navigation_bar);
        repository = new NotesRepository();
        try {
            toInitNotesInRepository();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        fragmentManager = getSupportFragmentManager();
        Bundle savedData = new Bundle();
        savedData.putParcelable(NotesRepository.class.getCanonicalName(), repository);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.beginTransaction().add(R.id.container_for_fragment_land_1, NoteList.getInstance(savedData)).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.container_for_fragment, NoteList.getInstance(savedData)).commit();
        }

    }

    private void toInitNotesInRepository() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = openFileInput(LOCAL_REPOSITORY_NAME);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        int size = objectInputStream.readInt();
        List<NoteEntity> list =  new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add((NoteEntity)objectInputStream.readObject());
            Log.d("@@@", list.toString());
        }
        repository.addAll(list);
        Log.d("@@@", "size " +repository.getAllNotes().size());
        objectInputStream.close();
        fileInputStream.close();
        Log.d("@@@", "toInitNotesInRepository: ");


    }

    @Override
    public void callEditionFragment(Bundle data) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.beginTransaction().add(R.id.container_for_fragment_land_2, NoteEdit.getInstance(data)).addToBackStack(null).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.container_for_fragment, NoteEdit.getInstance(data)).addToBackStack(null).commit();
        }
    }


    private void serializeNotes() throws IOException {
        FileOutputStream fos = openFileOutput(LOCAL_REPOSITORY_NAME, MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        objectOutputStream.writeInt(repository.getAllNotes().size());
        for (NoteEntity note:repository.getAllNotes()) {
            objectOutputStream.writeObject(note);
        }
        objectOutputStream.close();
        fos.close();
        Log.d("@@@", "Записан");

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
}