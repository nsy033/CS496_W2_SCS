package com.example.week2;

import android.graphics.Bitmap;

public class ListViewItem {
    private Bitmap iconDrawable;
    private String nameStr=null;
    private String numberStr=null;
    private String mail=null;
    private String address=null;

    public void setIcon(Bitmap icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        nameStr = title ;
    }
    public void setDesc(String desc) {
        numberStr = desc ;
    }
    public void setMail(String mail) {this.mail = mail;}
    public void setAddress(String address) {this.address = address;}

    public Bitmap getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.nameStr ;
    }
    public String getDesc() {
        return this.numberStr ;
    }
    public String getMail() { return this.mail;}
    public String getAddress() { return this.address;}
}
