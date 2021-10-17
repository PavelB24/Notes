package com.gb.notes.domain.Interfaces;

import com.gb.notes.domain.NoteEntity;

import java.util.List;

public interface NotesRepositoryInterface {

    List<NoteEntity> getAllNotes();

    void addNote(NoteEntity note);

    void addAll(List<? extends NoteEntity> arrayList);

    boolean removeNote(String id);

    boolean updateNote(String id, NoteEntity note);

}
