package com.example.hotrotimtro._Fragment_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hotrotimtro.R;

public class DoiMatKhauActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        findViewById(R.id.imgvBack).setOnClickListener(view -> onBackPressed());
    }
}