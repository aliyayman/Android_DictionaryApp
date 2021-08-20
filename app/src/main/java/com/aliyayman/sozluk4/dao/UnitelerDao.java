package com.aliyayman.sozluk4.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aliyayman.sozluk4.database.Veritabani;
import com.aliyayman.sozluk4.model.Uniteler;

import java.util.ArrayList;

public class UnitelerDao {

    public ArrayList<Uniteler> tumUniteler(Veritabani vt) {
        ArrayList<Uniteler> unitelerList = new ArrayList<>();

        SQLiteDatabase db=vt.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM uniteler",null);
        while (c.moveToNext()){
            Uniteler u=new Uniteler(c.getInt(c.getColumnIndex("unite_id"))
                    ,c.getString(c.getColumnIndex("unite_ad")));

            unitelerList.add(u);
        }
        return unitelerList;

    }
    public void uniteEkle(Veritabani vt, String unite_ad){
        SQLiteDatabase db=vt.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("unite_ad",unite_ad);
        db.insertOrThrow("uniteler",null,values);
        db.close();

    }
}
