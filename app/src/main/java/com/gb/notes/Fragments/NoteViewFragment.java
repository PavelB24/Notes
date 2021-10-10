package com.gb.notes.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gb.notes.NoteEntity;
import com.gb.notes.R;

public class NoteViewFragment extends Fragment {
    NoteEntity note;
    TextView title;
    TextView description;
    TextView date;
    Button backButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.note_view_frament_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        title= view.findViewById(R.id.note_title_text_view);
        description= view.findViewById(R.id.note_description_textview);
        date= view.findViewById(R.id.date_of_event_textview);
        backButton= view.findViewById(R.id.note_view_fragment_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
        toFillTheFields();
        super.onViewCreated(view, savedInstanceState);
    }

    private void toFillTheFields() {
        Bundle data = getArguments();
        note= data.getParcelable(NoteEntity.class.getCanonicalName());
        title.setText(note.getTitle());
        description.setText(note.getDetail());
        date.setText(note.getDateAsString());
    }

    public static NoteViewFragment getInstance(Bundle data){
        NoteViewFragment noteViewFragment = new NoteViewFragment();
        noteViewFragment.setArguments(data);
        return noteViewFragment;
    }
}
