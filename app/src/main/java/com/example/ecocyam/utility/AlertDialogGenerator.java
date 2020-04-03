package com.example.ecocyam.utility;



import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public final class AlertDialogGenerator {

    private AlertDialogGenerator(){

    }

    public static AlertDialog.Builder createAlertDialog(String title, String message, String negativeButton,
                                                        Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setNegativeButton(negativeButton, (dialog, which) -> dialog.dismiss());

        return builder;
    }
}
