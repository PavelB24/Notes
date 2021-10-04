package com.gb.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteList extends Fragment {
    private RecyclerView recyclerView;
    private NotesRepository repository;
    private NotesAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_list_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = new NotesRepository();
        adapter = new NotesAdapter();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setData(repository.getAllNotes());
        //Мы передаём интерфейс с методом, логика которого прописана в активити
        adapter.setOnItemClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onClick(NoteEntity note) {
                onClick(note);
            }
        });
        noteEditActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (Activity.RESULT_OK == result.getResultCode()) {
                Intent resultData = result.getData();
                tempNote = resultData.getParcelableExtra(NoteEntity.class.getCanonicalName());
                if (!repository.findById(tempNote.getId())) {
                    repository.addNote(resultData.getParcelableExtra(NoteEntity.class.getCanonicalName()));
                    adapter.setData(repository.getAllNotes());
                } else {
                    repository.updateNote(tempNote.getId(), tempNote);
                    adapter.setData(repository.getAllNotes());
                }

            }

        });
    }
    private void onClick(NoteEntity note) {
        Toast.makeText(getActivity(), "Edition mode", Toast.LENGTH_SHORT).show();

        tempNote = note;


        //todo

    }
}

