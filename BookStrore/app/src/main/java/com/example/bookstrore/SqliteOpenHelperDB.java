package com.example.bookstrore;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteOpenHelperDB extends SQLiteOpenHelper {
    private static final String name = "Book";
    private static final int version =1;


    public SqliteOpenHelperDB(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="CREATE TABLE USER("+"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+"UName TEXT,"+"Password TEXT,"+"Email TEXT,"+"TelephoneNo INTEGER)";
        db.execSQL(sql);
        db.execSQL("CREATE TABLE BOOK("+"_bookId INTEGER PRIMARY KEY AUTOINCREMENT,"+"BookName TEXT,"+"Author TEXT,"+"PublishedDate TEXT)");

        setUser(db,"Nadeesha","123456","nadee@gmail.com",07111764232);
        setBook(db,"Cinderella","Kristoper","2023-10-11");
        setBook(db,"Snow White","Albert","2023-10-11");
        setBook(db,"Little Red Riding Hood","Melvin","2023-10-11");
        setBook(db,"Hansel and Gretel","Wanaganin","2023-10-11");
        setBook(db,"Rumpelstiltskin","Sidner","2023-10-11");
        setBook(db,"Sleeping Beauty","Reman","2023-10-11");
    }

    public void setUser(SQLiteDatabase sqLiteDatabase,String name,String password,String email,int phone){
        ContentValues contentValues = new ContentValues();
        contentValues.put("UName",name);
        contentValues.put("Password",password);
        contentValues.put("Email",email);
        contentValues.put("TelephoneNo",phone);
        sqLiteDatabase.insert("USER",null,contentValues);
    }

    public void setBook(SQLiteDatabase sqLiteDatabase,String book,String Author,String date){
        ContentValues contentValues = new ContentValues();
        contentValues.put("BookName",book);
        contentValues.put("Author",Author);
        contentValues.put("PublishedDate",date);
        sqLiteDatabase.insert("BOOK",null,contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
