package com.gb.notes.Interfaces;

import android.view.View;

import com.gb.notes.NoteEntity;

public interface OnNoteClickListener {
        void onClickEdit(NoteEntity note);

        void onClickDelete(NoteEntity note);

        void onNoteClick(NoteEntity note);

        void onNoteLongClick(NoteEntity note, View view);
    }


