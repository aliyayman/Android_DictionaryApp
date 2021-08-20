package com.aliyayman.sozluk4.model;

import java.io.Serializable;

public class Uniteler implements Serializable {
    private int unite_id;
    private String unite_ad;

    public Uniteler() {
    }

    public Uniteler(int unite_id, String unite_ad) {
        this.unite_id = unite_id;
        this.unite_ad = unite_ad;
    }

    public int getUnite_id() {
        return unite_id;
    }

    public void setUnite_id(int unite_id) {
        this.unite_id = unite_id;
    }

    public String getUnite_ad() {
        return unite_ad;
    }

    public void setUnite_ad(String unite_ad) {
        this.unite_ad = unite_ad;
    }
}
