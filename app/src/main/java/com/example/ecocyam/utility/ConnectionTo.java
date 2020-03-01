package com.example.ecocyam.utility;

import android.content.Context;
import android.content.Intent;



public final class ConnectionTo {

    private ConnectionTo(){}

    public static void switchActivity(Context context,Class to){
        Intent intent = new Intent(context, to);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // intent.putExtra("id",id);
        context.startActivity(intent);
    }
}
