package com.gb.notes;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//Класс нужен лишь для того, чтоб хранить ссылки на вьюшки и не нагружать систему(???)
public class NoteViewHolder extends RecyclerView.ViewHolder {
    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public TextView titleTextView = itemView.findViewById(R.id.note_title_textview);
    public TextView descriptionTextView = itemView.findViewById(R.id.note_description_textview);
    public ImageButton deleteImageButton= itemView.findViewById(R.id.delete_note_image_button);
    public ImageButton editImageButton= itemView.findViewById(R.id.edit_item_button);

}
