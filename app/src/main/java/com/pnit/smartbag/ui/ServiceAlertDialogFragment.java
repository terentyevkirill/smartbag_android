package com.pnit.smartbag.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.pnit.smartbag.MainActivity;
import com.pnit.smartbag.R;
import com.pnit.smartbag.ui.home.HomeFragment;

public class ServiceAlertDialogFragment extends DialogFragment {
    public static ServiceAlertDialogFragment newInstance(int title) {
        ServiceAlertDialogFragment frag = new ServiceAlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_bluetooth_black_24dp)
                .setTitle(title)
                .setMessage(R.string.alert_message_bluetooth_service)
                .setPositiveButton(R.string.alert_dialog_ok,
                        (dialog, whichButton) -> ((MainActivity)getActivity()).doPositiveClick()
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        (dialog, whichButton) -> ((MainActivity)getActivity()).doNegativeClick()
                )
                .create();
    }
}
