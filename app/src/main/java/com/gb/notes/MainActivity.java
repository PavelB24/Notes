package com.gb.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    NoteEntity tempNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void onClick(NoteEntity note) {
        Toast.makeText(this, "Edition mode", Toast.LENGTH_SHORT).show();
        Intent toEditNote = new Intent(this, NoteEditActivity.class);
        toEditNote.putExtra(NoteEntity.class.getCanonicalName(), note);
        tempNote = note;
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