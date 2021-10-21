package com.gb.notes.domain;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gb.notes.R;

//Класс нужен лишь для того, чтоб хранить ссылки на вьюшки и не нагружать систему(???)
public class NoteViewHolder extends RecyclerView.ViewHolder {
    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public TextView titleTextView = itemView.findViewById(R.id.note_title_textview);
    public TextView descriptionTextView = itemView.findViewById(R.id.note_description_textview);


}
