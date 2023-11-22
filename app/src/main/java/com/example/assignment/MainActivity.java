package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//this is my application for hike management.
public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    Button btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);

        //sets up an OnClickListener for btnAdd that, when clicked, navigates to the Add activity.
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToAdd = new Intent(MainActivity.this, Add.class);
                startActivity(intentToAdd);
            }
        });
        //sets up an OnClickListener for btnView that, when clicked, navigates to the ViewFunction activity.
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToView = new Intent(MainActivity.this, ViewFunction.class);
                startActivity(intentToView);
            }
        });
    }

}