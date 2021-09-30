package com.gb.notes;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteList extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private RecyclerView recyclerView;
    private NotesRepository repository;
    private NotesAdapter adapter;
    ActivityResultLauncher<Intent> noteEditActivityLauncher;
    ActivityResultLauncher<Intent> settingsLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        repository = new NotesRepository();
        adapter = new NotesAdapter();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setData(repository.getAllNotes());
        //Мы передаём интерфейс с методом, логика которого прописана в активити
        adapter.setOnItemClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onClick(NoteEntity note) {
                NoteList.this.onClick(note);
            }
        });
        noteEditActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (Activity.RESULT_OK == result.getResultCode()) {
                Intent resultData= result.getData();

            }

        });
    }

    private void onClick(NoteEntity note) {
        Toast.makeText(this, "Флопа?", Toast.LENGTH_SHORT).show();
        Intent toEditNote = new Intent(this, NoteEditActivity.class);
        toEditNote.putExtra(NoteEntity.class.getCanonicalName(), note);
        noteEditActivityLauncher.launch(toEditNote);

        //todo

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notes_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_note_menu) {
            Intent toAddNewNoteIntent = new Intent(this, NoteEditActivity.class);
            noteEditActivityLauncher.launch(toAddNewNoteIntent);
            return true;
        } else {
            super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}