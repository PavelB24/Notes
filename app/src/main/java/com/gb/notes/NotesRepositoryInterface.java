package com.gb.notes;

import java.util.List;

public interface NotesRepositoryInterface {

    List<NoteEntity> getAllNotes();

    void addNote(NoteEntity note);

    boolean removeNote(String id);

    boolean updateNote(String id, NoteEntity note);

}
