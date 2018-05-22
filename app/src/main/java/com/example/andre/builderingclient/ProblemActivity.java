package com.example.andre.builderingclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProblemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        Intent intent = getIntent();

        TextView problemName = (TextView)findViewById(R.id.problemName);
        TextView problemGrade = (TextView)findViewById(R.id.problemGrade);
        ImageView problemImage = (ImageView)findViewById(R.id.problemImage);

        int id = intent.getIntExtra("id",0);
        problemName.setText(intent.getStringExtra("name"));
        problemGrade.setText(intent.getStringExtra("grade"));

        byte[] byteArray = intent.getByteArrayExtra("pic");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        problemImage.setImageBitmap(bmp);
    }

    public void onReport(View view){

    }
}
