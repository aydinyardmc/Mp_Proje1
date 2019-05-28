package com.aydinyardimci.mp_proje1;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Not implements Serializable {

    private  long time;
    private String baslik;
    private String icerik;
    private boolean trash;
    private Integer priorty;
    private String color;

    public Not(String baslik, String icerik,long time,Integer priorty) {
        this.time = time;
        this.baslik = baslik;
        this.icerik = icerik;
        this.priorty = priorty;
        setTrash(false);
        setColor("#FFFFFF");
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPriorty() {
        return priorty;
    }

    public void setPriorty(Integer priorty) {
        this.priorty = priorty;
    }

    public boolean isTrash() {
        return trash;
    }

    public void setTrash(boolean trash) {
        this.trash = trash;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getTimeString(Context context){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy   HH:mm", context.getResources().getConfiguration().locale);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(new Date(time));

    }
}
