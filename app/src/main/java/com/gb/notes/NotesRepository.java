package com.gb.notes;

import android.os.Parcel;
import android.os.Parcelable;

import com.gb.notes.Interfaces.NotesRepositoryInterface;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository implements NotesRepositoryInterface, Parcelable{
    private ArrayList<NoteEntity> notesList= new ArrayList<>();

    @Override
    public ArrayList<NoteEntity> getAllNotes() {
        return notesList;
    }

    @Override
    public void addNote(NoteEntity note) {
        notesList.add(note);

    }

    @Override
    public void addAll(List<? extends NoteEntity> arrayList) {
        notesList.addAll(arrayList);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(notesList);

    }
    public static final Parcelable.Creator<NotesRepository> CREATOR = new Creator<NotesRepository>() {
        @Override
        public NotesRepository createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public NotesRepository[] newArray(int i) {
            return new NotesRepository[0];
        }
    };

}
