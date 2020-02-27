package com.example.ecocyam.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecocyam.activities.CreateAccountActivity;
import com.example.ecocyam.activities.LoginActivity;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ConnectionTo {

    private ConnectionTo(){}

    public static void switchActivity(Context context,Class to){
        Intent intent = new Intent(context, to);
        // intent.putExtra("id",id);
        context.startActivity(intent);
    }
}
