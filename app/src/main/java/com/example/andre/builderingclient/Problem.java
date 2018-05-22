package com.example.andre.builderingclient;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by andre on 2018-02-23.
 */

public class Problem implements Serializable{
    private Bitmap pic;
    private String title;
    private String grade;
    private int id;
    private double lat;
    private double lng;

    public Problem(Bitmap pic, String title, String grade, double lat, double lng){
        this.pic = pic;
        this.title = title;
        this.grade = grade;
        this.lat = lat;
        this.lng = lng;
    }
    public void setId(int id){
        this.id = id;
    }
    public Bitmap getPic() {
        return pic;
    }

    public String getTitle() {
        return title;
    }

    public String getGrade() {
        return grade;
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
