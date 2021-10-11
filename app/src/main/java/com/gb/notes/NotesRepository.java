package com.gb.notes;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.gb.notes.Interfaces.NotesRepositoryInterface;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository implements NotesRepositoryInterface, Parcelable {
    public final String TAG = "@@@";
    private final ArrayList<NoteEntity> notesList = new ArrayList<>();
    private final ArrayList<NoteEntity> searchCache = new ArrayList<>();

    public ArrayList<NoteEntity> getSearchResult() {
        return searchCache;
    }

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
        for (int i = 0; i < notesList.size(); i++) {
            if (notesList.get(i).getId().equals(id)) {
                notesList.remove(i);
                return true;
            }
        }
        return false;
    }


    public boolean findById(String id) {
        for (int i = 0; i < notesList.size(); i++) {
            if (notesList.get(i).getId().equals(id)) {
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

    public void setAllMatches(String query) {
        //todo
        searchCache.clear();
        int size = query.length();
        for (NoteEntity note : notesList) {
            String title = note.getTitle();
            if (size > title.length()) {
                return;
            }
            for (int i = 0; i < size; i++) {
                if (title.charAt(i) != (query.charAt(i))) {
                    Log.d(TAG, "Не совпало " + title);
                } else {
                    if (i == size - 1) {
                        Log.d(TAG, "добавляю" + title);
                        searchCache.add(note);
                    }
                }
            }

        }
    }
}
