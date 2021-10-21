package com.gb.notes.ui.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.gb.notes.R;

public class AgreementDialog extends DialogFragment {
    public String AGREEMENT_KEY = "OK";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle(R.string.clear_data_base_dialog_title_text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.clear_data_base_dialog_message_text)
                .setPositiveButton(R.string.clear_data_base_dialog_positive_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle result = new Bundle();
                        result.putBoolean(AGREEMENT_KEY, true);
                        getParentFragmentManager().setFragmentResult(AgreementDialog.class.getCanonicalName(), result);
                    }
                })
                .setNegativeButton(R.string.clear_data_base_dialog_negative_text, null).create();
    }
}
