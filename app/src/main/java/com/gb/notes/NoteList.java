package com.gb.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class NoteList extends Fragment implements NotesAdapter.OnNoteClickListener {
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private NotesRepository repository;
    NoteEntity tempNote;
    private androidx.appcompat.widget.Toolbar toolbar;
    Bundle data;
    public final String TAG = "@@@";
    Bundle savedData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.note_list_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savedData = getArguments();
        repository = savedData.getParcelable(NotesRepository.class.getCanonicalName());
        adapter = new NotesAdapter();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setData(repository.getAllNotes());
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //Мы передаём интерфейс с методом, логика которого прописана в активити (а теперь в фрагменте)
        adapter.setOnItemClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onClickEdit(NoteEntity note) {
                NoteList.this.onClickEdit(note);
            }

            @Override
            public void onClickDelete(NoteEntity note) {
                NoteList.this.onClickDelete(note);

            }
        });
        //TODO переписать получение результата
        getParentFragmentManager().setFragmentResultListener(NoteEdit.class.getCanonicalName(), getActivity(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (!(result.isEmpty())) {
                    tempNote = result.getParcelable(NoteEntity.class.getCanonicalName());
                    if (!repository.findById(tempNote.getId())) {
                        repository.addNote(tempNote);
                        adapter.setData(repository.getAllNotes());
                    } else {
                        repository.updateNote(tempNote.getId(), tempNote);
                        adapter.setData(repository.getAllNotes());
                    }
                }
            }
        });
    }

    @Override
    public void onClickEdit(NoteEntity note) {
        Toast.makeText(getActivity(), R.string.edition_mode_toast_text, Toast.LENGTH_SHORT).show();
        data = new Bundle();
        data.putParcelable(NoteEntity.class.getCanonicalName(), note);
        ((FragmentsCall) requireActivity()).callEditionFragment(data);

    }

    @Override
    public void onClickDelete(NoteEntity note) {
        Toast.makeText(getActivity(), R.string.deleted_note_toast_text, Toast.LENGTH_SHORT).show();
        repository.removeNote(note.getId());
        adapter.setData(repository.getAllNotes());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (!menu.hasVisibleItems()) {
            inflater.inflate(R.menu.notes_list_menu, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_note_menu) {
            getParentFragmentManager().clearFragmentResult(NoteList.class.getCanonicalName());
            data = null;
            ((FragmentsCall) requireActivity()).callEditionFragment(data);
            return true;
        } else {
            super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public static NoteList getInstance(Bundle data) {
        NoteList noteList = new NoteList();
        if (!data.isEmpty()) {
            noteList.setArguments(data);
        }
        return noteList;
    }

}

