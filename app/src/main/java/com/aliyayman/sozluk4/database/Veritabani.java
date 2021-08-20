package com.aliyayman.sozluk4.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Veritabani extends SQLiteOpenHelper {
    public Veritabani(@Nullable Context context) {
        super(context, "sozluk.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS uniteler  (\n" +
                "unite_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "unite_ad TEXT\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS  \"kelimeler\" (\n" +
                "\t\"kelime_id\"\tINTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                "\t\"ing\"\tTEXT,\n" +
                "\t\"tc\"\tTEXT,\n" +
                "\t\"unite_id\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"unite_id\") REFERENCES \"uniteler\"(\"unite_id\")\n" +
                ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS uniteler");
        db.execSQL("DROP TABLE IF EXISTS kelimeler");
        onCreate(db);

    }
}
