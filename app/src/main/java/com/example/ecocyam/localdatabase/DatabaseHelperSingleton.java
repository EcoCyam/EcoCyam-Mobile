package com.example.ecocyam.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ecocyam.entities.ScannedProduct;
import com.example.ecocyam.entities.User;

public final class DatabaseHelperSingleton extends SQLiteOpenHelper {
    private static DatabaseHelperSingleton databaseHelperInstance;

    private static final String DATABASE_NAME = "ecocyam.db";
    private static final int VERSION = 2;

    //Table user
    private static final String TABLE_NAME = "user_table";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_FISTNAME = "FIRSTNAME";
    private static final String COL_LASTNAME = "LASTNAME";
    private static final String COL_PASSWORD = "PASSWORD";

    //Table scanned_product
    private static final String TABLE_NAME_product = "scanned_product_table";
    private static final String COL_TITTLE = "TITTLE";
    private static final String COL_BRAND = "BRAND";
    private static final String COL_RATING = "RATING";
    private static final String COL_DATESCAN = "DATE_SCAN";
    private static final String COL_REFUSER = "REF_USER";

    public static final String SELECT_ALL_STR = "SELECT * FROM ";

    //singleton
    public static DatabaseHelperSingleton getInstance(Context context){
        synchronized (DatabaseHelperSingleton.class) {
            if (databaseHelperInstance == null) {
                databaseHelperInstance = new DatabaseHelperSingleton(context.getApplicationContext());
            }
            return databaseHelperInstance;
        }
    }


    private DatabaseHelperSingleton(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "create table "+ TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT," +
                " FIRSTNAME TEXT, LASTNAME TEXT, PASSWORD TEXT)";
        db.execSQL(query);
        //bien vérifier pour la fk
        query = "create table "+ TABLE_NAME_product +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITTLE TEXT," +
                " BRAND TEXT, RATING RATING, DATE_SCAN TEXT, REF_USER INTEGER, FOREIGN KEY(REF_USER) REFERENCES TABLE_NAME(ID) )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on Update supprime ancienne base => à améliorer avec num de version
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_product);
        onCreate(db);
    }


    /* PARTI USER */
    public boolean createUser(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_EMAIL,user.getEmail());
        contentValues.put(COL_FISTNAME,user.getFirstName());
        contentValues.put(COL_LASTNAME,user.getLastName());
        contentValues.put(COL_PASSWORD,user.getPassword());

        long result = db.insert(TABLE_NAME,null,contentValues);

        return result != -1;

    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery(SELECT_ALL_STR +TABLE_NAME,null);
    }

    public boolean updateUser(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_EMAIL,user.getEmail());
        contentValues.put(COL_FISTNAME,user.getFirstName());
        contentValues.put(COL_LASTNAME,user.getLastName());
        contentValues.put(COL_PASSWORD,user.getPassword());

        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] {String.valueOf(user.getId())});
        return true;
    }

    public int deleteUser(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?", new String[]{id});

    }

    public boolean isUserUnique(User user) {
        SQLiteDatabase db=this.getWritableDatabase();

        try (Cursor cursor = db.rawQuery(SELECT_ALL_STR +TABLE_NAME + " WHERE EMAIL=?",new String[] {user.getEmail()})){
            return cursor.getCount() == 0;
        }
        //user already exists
    }


    public boolean verifyUserInfo(String email, String password) {
        SQLiteDatabase db=this.getWritableDatabase();

        try (Cursor cursor = db.rawQuery(SELECT_ALL_STR +TABLE_NAME + " WHERE EMAIL=? AND PASSWORD=?",new String[] {email,password})){
            return cursor.getCount() != 0;
        }
        //user exist
    }

    //renvoie user associé à l'email qui est unique
    public User getUserByEmail(String email) {
        SQLiteDatabase db=this.getWritableDatabase();

        try (Cursor cursor = db.rawQuery(SELECT_ALL_STR +TABLE_NAME + " WHERE EMAIL=?",new String[] {email})){
            if (cursor.moveToNext()){
                return new User(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),cursor.getString(4));
            }
        }
        return null;
    }

    /* FIN PARTIE USER */

    /* PARTIE PRODUCT */
    public boolean createProdcut(ScannedProduct scannedProduct){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_TITTLE,scannedProduct.getTitle());
        contentValues.put(COL_BRAND,scannedProduct.getMarque());
        contentValues.put(COL_RATING,scannedProduct.getRating());
        contentValues.put(COL_DATESCAN,scannedProduct.getLocalDate().toString());
        contentValues.put(COL_REFUSER,scannedProduct.getRefUser());

        long result = db.insert(TABLE_NAME_product,null,contentValues);

        return result != -1;

    }

    public Cursor getAllScannedProduct(){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery(SELECT_ALL_STR +TABLE_NAME_product,null);
    }

    //supprime produits quand utilisateur supprimé
    public int deleteProductByRefId(String refId){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME_product,"REF_USER = ?", new String[]{refId});

    }


}