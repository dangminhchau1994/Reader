package com.newspaper.chaudang.newspaper.Model;

/**
 * Created by Chau Dang on 8/2/2017.
 */

public class TinGui {

    private String noidung;
    private String email;

    public TinGui(String noidung, String email) {
        this.noidung = noidung;
        this.email = email;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
