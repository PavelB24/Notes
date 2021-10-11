package com.gb.notes.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.gb.notes.NoteEntity;
import com.gb.notes.R;

import java.util.UUID;

public class NoteEditFragment extends Fragment {
    Button applyButton;
    EditText titleEditText;
    EditText descriptionEditText;
    NoteEntity note;
    DatePicker datePicker;
    UUID uuid;
    Bundle data;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_edit_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyButton = view.findViewById(R.id.apply_button);
        titleEditText = view.findViewById(R.id.title_edittext);
        descriptionEditText = view.findViewById(R.id.description_edittext);
        datePicker = view.findViewById(R.id.date_picker_actions);
        toFillTheNote();
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uuid = UUID.randomUUID();
                //Редактируем заметку ниже
                if (toCheckIfEdit() && !(titleEditText.getText().toString().isEmpty() && descriptionEditText.getText().toString().isEmpty())) {
                    note = new NoteEntity(note.getId(), titleEditText.getText().toString(), descriptionEditText.getText().toString(), datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
                    data.putParcelable(NoteEntity.class.getCanonicalName(), note);
                    getParentFragmentManager().setFragmentResult(NoteEditFragment.class.getCanonicalName(), data);
                    getParentFragmentManager().popBackStackImmediate();

                    //Создаём новую заметку
                } else if (!titleEditText.getText().toString().isEmpty() && !descriptionEditText.getText().toString().isEmpty()) {
                    note = new NoteEntity(uuid.toString(), titleEditText.getText().toString(), descriptionEditText.getText().toString(), datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
                    data = new Bundle();
                    data.putParcelable(NoteEntity.class.getCanonicalName(), note);
                    getParentFragmentManager().setFragmentResult(NoteEditFragment.class.getCanonicalName(), data);
                    getParentFragmentManager().popBackStackImmediate();
                } else {
                    Toast.makeText(getActivity(), R.string.warning_toast, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void toFillTheNote() {
        if (toCheckIfEdit()) {
            note = data.getParcelable(NoteEntity.class.getCanonicalName());
            titleEditText.setText(note.getTitle());
            descriptionEditText.setText(note.getDetail());
            datePicker.updateDate(note.getOriginYear(), note.getOriginMonth() - 1, note.getOriginDay());

        }
    }

    private boolean toCheckIfEdit() {
        //Если есть бандл с классом заметка
        if (!(getArguments() == null)) {
            if (data == null) {
                data = getArguments();
            }
            return true;
        } else {
            return false;
        }

    }

    public static NoteEditFragment getInstance(Bundle data) {
        NoteEditFragment noteEdit = new NoteEditFragment();
        noteEdit.setArguments(data);
        return noteEdit;
    }

}