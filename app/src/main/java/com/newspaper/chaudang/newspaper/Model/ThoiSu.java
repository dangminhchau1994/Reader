package com.newspaper.chaudang.newspaper.Model;

/**
 * Created by Chau Dang on 7/31/2017.
 */

public class ThoiSu {

    private String tenThoiSu;
    private String hinhanh;
    private String link;

    public ThoiSu() {
    }

    public ThoiSu(String tenThoiSu, String hinhanh, String link) {
        this.tenThoiSu = tenThoiSu;
        this.hinhanh = hinhanh;
        this.link = link;
    }

    public String getTenThoiSu() {
        return tenThoiSu;
    }

    public void setTenThoiSu(String tenThoiSu) {
        this.tenThoiSu = tenThoiSu;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
