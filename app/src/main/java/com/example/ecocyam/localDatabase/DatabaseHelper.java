package com.example.ecocyam.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ecocyam.db";
    private static final int VERSION = 2;

    private static final String TABLE_NAME = "user_table";
    private static final String COL_ID = "ID";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_FISTNAME = "FIRSTNAME";
    private static final String COL_LASTNAME = "LASTNAME";
    private static final String COL_PASSWORD = "PASSWORD";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "create table "+ TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, FIRSTNAME TEXT, LASTNAME TEXT, PASSWORD TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion>oldVersion)
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean createUser(String email, String firstName, String lastName, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_FISTNAME,firstName);
        contentValues.put(COL_LASTNAME,lastName);
        contentValues.put(COL_PASSWORD,password);

        long result = db.insert(TABLE_NAME,null,contentValues);

        return result != -1;

    }

    public Cursor get_data(){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }

    public boolean updateUser(String id,String email, String firstName, String lastName, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_FISTNAME,firstName);
        contentValues.put(COL_LASTNAME,lastName);
        contentValues.put(COL_PASSWORD,password);

        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] {id});
        return true;
    }

    public int deleteUser(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?", new String[]{id});

    }

    public boolean isUserUnique(String email) {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME + " WHERE EMAIL=?",new String[] {email});
        return cursor.getCount() == 0;
        //user already exists
    }

    public boolean testConnectionInfo(String email, String password) {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME + " WHERE EMAIL=? AND PASSWORD=?",new String[] {email,password});
        return cursor.getCount() != 0;
        //user exist
    }
}
