package com.gb.notes.ui.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.gb.notes.domain.Application;
import com.gb.notes.domain.NotesRepository;
import com.gb.notes.R;

public class DataManagerFragment extends Fragment {
    private ImageButton deleteImageButton;
    private NotesRepository repository;
    private Bundle savedData;
    private ToggleButton secretButton;

    public final String CLEAR_DATABASE = "OK";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.data_manager_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        savedData = getArguments();
        repository = savedData.getParcelable(NotesRepository.class.getCanonicalName());
        deleteImageButton = view.findViewById(R.id.delete_storage_button);
        toInitDeleteImageButton();
        secretButton= view.findViewById(R.id.data_manager_data_transmitter_toggle_button);
        secretButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secretButton.setChecked(false);
                new AlertDialog.Builder(requireActivity()).setView(R.layout.password_dialog_layout).create().show();

            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void toInitDeleteImageButton() {
        deleteImageButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AgreementDialog confirmation = new AgreementDialog();
            confirmation.show(getParentFragmentManager(), CLEAR_DATABASE);
            getParentFragmentManager().setFragmentResultListener(AgreementDialog.class.getCanonicalName(), getActivity(), new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    boolean isConfirmed = result.getBoolean(confirmation.AGREEMENT_KEY);
                    if (isConfirmed) {
                        repository.deleteNoteList();
                    }
                }
            });

        }
    });
    }

    public static DataManagerFragment getInstance(Bundle data) {
        DataManagerFragment dataManager = new DataManagerFragment();
        if (!data.isEmpty()) {
            dataManager.setArguments(data);
        }
        return dataManager;
    }
}
