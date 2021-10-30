package com.gb.notes.domain;

import android.view.View;

public interface OnNoteClickListener {
        void onClickEdit(NoteEntity note);

        void onClickDelete(NoteEntity note);

        void onNoteClick(NoteEntity note);

        void onNoteLongClick(NoteEntity note, View view);
    }


