package com.example.user.bi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper{
     private static final int DATABASE_VERSION = 1;

     private static final String DATABASE_NAME = "BI";
     String TABLE_USER = "user",
            KEY_ID = "id",
            KEY_NAME = "name",
            KEY_USERNAME = "username",
            KEY_EMAIL = "email",
            KEY_PASSWORD = "password",
            KEY_IMAGEURI = "imageUri";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_USERNAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT," + KEY_IMAGEURI + " TEXT)");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXIST " + TABLE_USER);

        onCreate(db);
    }

    public void createUser(User user){
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, user.getName());
            values.put(KEY_USERNAME, user.getUsername());
            values.put(KEY_EMAIL, user.getEmail());
            values.put(KEY_PASSWORD, user.getPassword());
            values.put(KEY_IMAGEURI, user.getImageUri().toString());

            db.insert(TABLE_USER, null, values);
            db.close();
        }
        catch (Exception e){
            System.out.print(e);
        }
    }



    public User getUser(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID, KEY_NAME, KEY_USERNAME, KEY_EMAIL, KEY_PASSWORD, KEY_IMAGEURI }, KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null);

       if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getString(4));
        db.close();
        cursor.close();
        return user;
    }

    public void deleteUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public int getUserCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

        int count = cursor.getCount();

        db.close();
        cursor.close();

        return count;
    }

    public int updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, user.getName());
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_IMAGEURI, user.getImageUri().toString());
        int rowsAffected = db.update(TABLE_USER, values,KEY_ID + "=?", new String[] {String.valueOf(user.getId())});
        db.close();

        return rowsAffected;
    }

    public List<User> getAllUsers() {

        List<User> user = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

        if (cursor.moveToFirst()) {

            do {
                user.add(new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return user;
    }
}
