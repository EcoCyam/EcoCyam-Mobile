package com.example.ecocyam.utility;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.ecocyam.entities.Product;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ConnectionTo {

    public static final Pattern VALID_EMAIL_WITH_REGEX = Pattern.compile(
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    private ConnectionTo(){}

    public static void switchActivity(Context context,Class to){ //rajouter une map de put extra ?
        Intent intent = new Intent(context, to);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        // intent.putExtra("id",id);
        context.startActivity(intent);
    }

    public static void switchActivityWithStringExtra(Context context,Class to,String stringToPass){ //rajouter une map de put extra ?
        Intent intent = new Intent(context, to);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
         intent.putExtra("email",stringToPass);
        context.startActivity(intent);
    }

    public static void switchActivityWithObejctExtra(Context context, Class to, Product product){
        Intent intent = new Intent(context, to);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("product",product);
        context.startActivity(intent);
    }

    //on pourrait passer une liste de edit text et une liste de message
    public static boolean verifyEmptyFields(EditText editText,String message){
        String strEditText = editText.getText().toString();

        if (TextUtils.isEmpty(strEditText)){
            editText.setError(message);
            return true;
        }
        return false;
    }

    public static boolean validateFormatWithRegex(String email){
        Matcher matcher = VALID_EMAIL_WITH_REGEX.matcher(email);
        return matcher.matches();
    }
}
