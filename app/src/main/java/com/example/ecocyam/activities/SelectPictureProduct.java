package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecocyam.R;
import com.example.ecocyam.localdatabase.DatabaseHelperSingleton;
import com.example.ecocyam.utility.PictureFormatting;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class SelectPictureProduct extends AppCompatActivity {

    /* default */  private static final int GALLERY_REQUEST = 2;
    /* default */  private DatabaseHelperSingleton db;
    /* default */private int idProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture_product);

        db = DatabaseHelperSingleton.getInstance(this);
        Intent intent = getIntent();
        idProduct = Integer.parseInt(intent.getStringExtra("email"));


        Button button = findViewById(R.id.buttonSelectPicture);
        button.setOnClickListener(v -> {
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

            startActivityForResult(chooserIntent, GALLERY_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK && reqCode == GALLERY_REQUEST) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ImageView imageView = findViewById(R.id.imageViewSelectedPicture);
                db.addPictureToProduct(idProduct, PictureFormatting.getByteArray(selectedImage, "png"));

                Bitmap getImageFromDB = db.getProductPictureById(idProduct);
                imageView.setImageBitmap(getImageFromDB);
                // ConnectionTo.switchActivity(this,HistoryActivity.class);

            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}
