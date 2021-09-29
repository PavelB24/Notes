package com.gb.notes;

import java.util.List;

public interface NotesRepositoryInterface {

    List<NoteEntity> getAllNotes();

    void addNote(NoteEntity note);

    boolean removeNote(int id);

    boolean updateNote(int id, NoteEntity note);

}
