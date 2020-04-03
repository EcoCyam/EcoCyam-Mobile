package com.example.ecocyam.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public final class PictureFormatting {

    private PictureFormatting(){}

    public static byte[] getByteArray(Bitmap bmp,String format){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        switch (format){
            case "png" :
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                break;
            case "jpg" :
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                break;
            default :
                return null;
        }
        return stream.toByteArray();
    }

    public static Bitmap getBitmap(byte[] byteArray){
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
