package com.aliyayman.sozluk4.model;

import java.io.Serializable;

public class Kelimeler implements Serializable {
    private int kelime_id;
    private String ing;
    private String tc;
    private Uniteler unite;

    public Kelimeler() {
    }


    public Kelimeler(int kelime_id, String ing, String tc, Uniteler unite) {
        this.kelime_id = kelime_id;
        this.ing = ing;
        this.tc = tc;
        this.unite = unite;
    }

    public int getKelime_id() {
        return kelime_id;
    }

    public void setKelime_id(int kelime_id) {
        this.kelime_id = kelime_id;
    }

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public Uniteler getUnite() {
        return unite;
    }

    public void setUnite(Uniteler unite) {
        this.unite = unite;
    }
}
