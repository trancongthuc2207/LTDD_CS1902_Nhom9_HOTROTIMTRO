package com.example.hotrotimtro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.hotrotimtro._Login.LoginActivity;
import com.example.hotrotimtro._SignUp.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_DangNhap).setOnClickListener(
                v-> startActivity(new Intent(MainActivity.this, LoginActivity.class))
        );
        findViewById(R.id.btn_DangKy).setOnClickListener(
                v-> startActivity(new Intent(MainActivity.this, SignUpActivity.class))
        );
    }
}