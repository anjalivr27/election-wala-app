package com.example.database.pages;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.widget.Toast;

import com.example.database.R;

public class Officers extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officers);

        Intent intent = getIntent();


        if (intent != null) {
            String receivedData = intent.getStringExtra("pollingStation");


            Toast.makeText(Officers.this, receivedData, Toast.LENGTH_SHORT).show();

        }
    }
}