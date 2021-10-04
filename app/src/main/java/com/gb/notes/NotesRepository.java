package com.gb.notes;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository implements NotesRepositoryInterface{
    private List<NoteEntity> notesList= new ArrayList<>();

    @Override
    public List<NoteEntity> getAllNotes() {
        return notesList;
    }

    @Override
    public void addNote(NoteEntity note) {
        notesList.add(note);

    }

    @Override
    public boolean removeNote(String id) {
        for (int i = 0; i < notesList.size() ; i++) {
            if(notesList.get(i).getId().equals(id)){
                notesList.remove(i);
                return true;
            }
        } return false;
    }

    public boolean findById(String id){
        for (int i = 0; i <notesList.size() ; i++) {
            if(notesList.get(i).getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean updateNote(String id, NoteEntity note) {
        removeNote(id);
        note.setId(id);
        notesList.add(note);
        return true;

    }
}
