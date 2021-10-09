package com.gb.notes.Interfaces;

import android.os.Parcelable;

import com.gb.notes.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public interface NotesRepositoryInterface {

    List<NoteEntity> getAllNotes();

    void addNote(NoteEntity note);

    void addAll(List<? extends NoteEntity> arrayList);

    boolean removeNote(String id);

    boolean updateNote(String id, NoteEntity note);

}
