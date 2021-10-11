package com.gb.notes.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gb.notes.Interfaces.FragmentsCall;
import com.gb.notes.Interfaces.OnNoteClickListener;
import com.gb.notes.NoteEntity;
import com.gb.notes.NotesAdapter;
import com.gb.notes.NotesRepository;
import com.gb.notes.R;


public class NoteListFragment extends Fragment implements OnNoteClickListener {
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private NotesRepository repository;
    private NoteEntity tempNote;
    private androidx.appcompat.widget.Toolbar toolbar;
    private Bundle data;
    private Bundle savedData;
    private SearchView searchView;
    public final String TAG = "@@@";
    public final String DELETE = "OK";


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
        searchView = view.findViewById(R.id.search_item_menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //Мы передаём интерфейс с методом, логика которого прописана в активити (а теперь в фрагменте)
        adapter.setOnItemClickListener(new OnNoteClickListener() {
            @Override
            public void onClickEdit(NoteEntity note) {
                NoteListFragment.this.onClickEdit(note);
            }

            @Override
            public void onClickDelete(NoteEntity note) {
                NoteListFragment.this.onClickDelete(note);

            }

            @Override
            public void onNoteClick(NoteEntity note) {
                NoteListFragment.this.onNoteClick(note);
            }

            @Override
            public void onNoteLongClick(NoteEntity note, View view) {
                NoteListFragment.this.onNoteLongClick(note, view);
            }
        });
        //Получение результата, можно было просто передавать бандл с ссылкой на репозиторий
        getParentFragmentManager().setFragmentResultListener(NoteEditFragment.class.getCanonicalName(), getActivity(), new FragmentResultListener() {
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
        AgreementDialog confirmation = new AgreementDialog();
        confirmation.show(getParentFragmentManager(), DELETE);
        getParentFragmentManager().setFragmentResultListener(AgreementDialog.class.getCanonicalName(), getActivity(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Boolean isConfirmed = result.getBoolean(confirmation.AGREEMENT_KEY);
                if (isConfirmed) {
                    Toast.makeText(getActivity(), R.string.deleted_note_toast_text, Toast.LENGTH_SHORT).show();
                    repository.removeNote(note.getId());
                    adapter.setData(repository.getAllNotes());
                }

            }
        });
    }

    @Override
    public void onNoteClick(NoteEntity note) {
        data = new Bundle();
        data.putParcelable(NoteEntity.class.getCanonicalName(), note);
        ((FragmentsCall) requireActivity()).callNoteViewFragment(data);
    }

    @Override
    public void onNoteLongClick(NoteEntity note, View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.inflate(R.menu.note_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.delete_note_item) {
                    onClickDelete(note);
                } else if (menuItem.getItemId() == R.id.edit_note_item) {
                    onClickEdit(note);

                }
                return false;
            }
        });
        popupMenu.show();
        //TODO
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (!menu.hasVisibleItems()) {
            inflater.inflate(R.menu.notes_list_menu, menu);
            MenuItem searchItem = menu.findItem(R.id.search_item_menu);
            searchView = (SearchView) searchItem.getActionView();
            //TODO настроить поиск
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, searchView.getQuery().toString());
                    repository.setAllMatches(searchView.getQuery().toString());
                    if (!repository.getSearchResult().isEmpty()) {
                        Log.d(TAG, repository.getSearchResult().toString());
                        adapter.setData(repository.getSearchResult());
                    } else {
                        Toast.makeText(getContext(), R.string.unsuccessful_search_toast_text, Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_note_item_menu) {
            getParentFragmentManager().clearFragmentResult(NoteListFragment.class.getCanonicalName());
            data = null;
            ((FragmentsCall) requireActivity()).callEditionFragment(data);
            return true;
        } else if (item.getItemId() == R.id.refresh_item_menu) {
            adapter.setData(repository.getAllNotes());
        } else {
            super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public static NoteListFragment getInstance(Bundle data) {
        NoteListFragment noteList = new NoteListFragment();
        if (!data.isEmpty()) {
            noteList.setArguments(data);
        }
        return noteList;
    }

}

