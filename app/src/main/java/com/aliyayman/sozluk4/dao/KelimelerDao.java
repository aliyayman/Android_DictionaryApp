package com.aliyayman.sozluk4.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aliyayman.sozluk4.database.Veritabani;
import com.aliyayman.sozluk4.model.Kelimeler;
import com.aliyayman.sozluk4.model.Uniteler;

import java.util.ArrayList;

public class KelimelerDao {
    public ArrayList<Kelimeler> tumKelimeler(Veritabani vt) {
        ArrayList<Kelimeler> kelimelerList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM kelimeler,uniteler WHERE uniteler.unite_id=kelimeler.unite_id " , null);
        while (c.moveToNext()) {
            Uniteler u = new Uniteler(c.getInt(c.getColumnIndex("unite_id"))
                    , c.getString(c.getColumnIndex("unite_ad")));

            Kelimeler k = new Kelimeler(c.getInt(c.getColumnIndex("kelime_id"))
                    , c.getString(c.getColumnIndex("ing"))
                    , c.getString(c.getColumnIndex("tc")), u);

            kelimelerList.add(k);

        }
        return kelimelerList;



    }

    public ArrayList<Kelimeler> tumKelimelerUniteId(Veritabani vt, int unite_id) {
        ArrayList<Kelimeler> kelimelerList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM kelimeler,uniteler WHERE uniteler.unite_id=kelimeler.unite_id and kelimeler.unite_id=" + unite_id, null);
        while (c.moveToNext()) {
            Uniteler u = new Uniteler(c.getInt(c.getColumnIndex("unite_id"))
                    , c.getString(c.getColumnIndex("unite_ad")));

            Kelimeler k = new Kelimeler(c.getInt(c.getColumnIndex("kelime_id"))
                    , c.getString(c.getColumnIndex("ing"))
                    , c.getString(c.getColumnIndex("tc")), u);

            kelimelerList.add(k);

        }
        return kelimelerList;



    }
    public ArrayList<Kelimeler> kelimeAra(Veritabani vt, int unite_id, String aramaKelime) {
        ArrayList<Kelimeler> kelimelerList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM kelimeler,uniteler WHERE kelimeler.unite_id=uniteler.unite_id and kelimeler.unite_id="+unite_id +" and kelimeler.ing like '%"+aramaKelime+"%'" , null);
        while (c.moveToNext()) {
            Uniteler u = new Uniteler(c.getInt(c.getColumnIndex("unite_id"))
                    , c.getString(c.getColumnIndex("unite_ad")));

            Kelimeler k = new Kelimeler(c.getInt(c.getColumnIndex("kelime_id"))
                    , c.getString(c.getColumnIndex("ing"))
                    , c.getString(c.getColumnIndex("tc")),u);

            kelimelerList.add(k);


        }
        return kelimelerList;


    }

    public void kelimeEkle(Veritabani vt, String ing, String tc, int unite_id){
        SQLiteDatabase db=vt.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("ing",ing);
        values.put("tc",tc);
        values.put("unite_id",unite_id);
        db.insertOrThrow("kelimeler",null,values);
        db.close();

    }
    public void kelimeGuncelle(Veritabani vt, int kelime_id, String ing, String tc){
        SQLiteDatabase db=vt.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("ing",ing);
        values.put("tc",tc);
        db.update("kelimeler",values,"kelime_id=?",new String[]{String.valueOf(kelime_id)});
        db.close();

    }
    public void kelimeSil(Veritabani vt, int kelime_id ){
        SQLiteDatabase db=vt.getWritableDatabase();
        db.delete("kelimeler","kelime_id=?",new String[]{String.valueOf(kelime_id)});
        db.close();
    }

    public ArrayList<Kelimeler> rastgele5Getir(Veritabani vt){
        ArrayList<Kelimeler> kelimelerList=new ArrayList<>();
        SQLiteDatabase db=vt.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM kelimeler,uniteler WHERE uniteler.unite_id=kelimeler.unite_id ORDER BY RANDOM() LIMIT 10",null);
        while(c.moveToNext()){
            Uniteler u = new Uniteler(c.getInt(c.getColumnIndex("unite_id"))
                    , c.getString(c.getColumnIndex("unite_ad")));

            Kelimeler k = new Kelimeler(c.getInt(c.getColumnIndex("kelime_id"))
                    , c.getString(c.getColumnIndex("ing"))
                    , c.getString(c.getColumnIndex("tc")), u);

            kelimelerList.add(k);
        }
        return kelimelerList;

    }
    public ArrayList<Kelimeler> rastgele3YanlisGetir(Veritabani vt,int kelime_id){
        ArrayList<Kelimeler> kelimelerList=new ArrayList<>();
        SQLiteDatabase db=vt.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM kelimeler,uniteler WHERE uniteler.unite_id=kelimeler.unite_id and kelime_id !="+kelime_id+" ORDER BY RANDOM() LIMIT 3",null);
        while(c.moveToNext()){
            Uniteler u = new Uniteler(c.getInt(c.getColumnIndex("unite_id"))
                    , c.getString(c.getColumnIndex("unite_ad")));

            Kelimeler k = new Kelimeler(c.getInt(c.getColumnIndex("kelime_id"))
                    , c.getString(c.getColumnIndex("ing"))
                    , c.getString(c.getColumnIndex("tc")), u);

            kelimelerList.add(k);

        }
        return kelimelerList;

    }




}

