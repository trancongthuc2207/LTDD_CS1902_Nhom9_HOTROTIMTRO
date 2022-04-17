package com.example.hotrotimtro;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hotrotimtro._Anima.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btnNext;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
        //

        auth = FirebaseAuth.getInstance();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = auth.getCurrentUser();

                Toast.makeText(MainActivity.this, user.getEmail().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhXa()
    {
        btnNext = findViewById(R.id.btnNext);
    }
}