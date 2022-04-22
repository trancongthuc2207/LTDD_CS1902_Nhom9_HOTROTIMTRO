package com.example.hotrotimtro._Service;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hotrotimtro._Pojo.KhachHang;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Service {

    FirebaseDatabase database;
    private DatabaseReference mDatabase;

    public Service() {
    }

    public void dangKyAccount(KhachHang kh){

        this.database = FirebaseDatabase.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference myRef = database.getReference("USER");
        myRef.setValue(kh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Log.e("Push","ThanhCong");
            }
        });
    }

}
