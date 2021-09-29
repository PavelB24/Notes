package com.gb.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

public class NoteList extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private RecyclerView recyclerView;
    private NotesRepository repository;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        repository= new NotesRepository();
        adapter= new NotesAdapter();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView= findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.notes_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_note_menu){
            Toast.makeText(this,"fgdfgdf", Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}