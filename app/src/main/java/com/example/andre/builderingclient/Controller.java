package com.example.andre.builderingclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;

/**
 * Created by andre on 2018-02-23.
 */

public class Controller {
    private Communication client;


    public Controller(){
        this.client = new Communication();
    }

    //Context bara för exempel
    public Problem[] getProblems(Context context, Location location){
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.boulderexample);
        Problem[] problems = {
                new Problem(bm,"Generic", "6a", 55.602917, 12.998207),
                new Problem(bm,"Generic", "6a", 55.603935, 13.007906),
                new Problem(bm,"Generic", "6a", 55.602215, 13.018151),
                new Problem(bm,"Generic", "6a", 55.601233, 12.993153),
                new Problem(bm,"Generic", "6a", 55.605934, 12.997015)};
        return problems;
    }
    public Problem getProblem(Context context, int id){
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.boulderexample);
        return new Problem(bm,"Generic", "6a", 55.605934, 12.997015);
    }
}
