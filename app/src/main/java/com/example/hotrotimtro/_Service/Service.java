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
        this.database = FirebaseDatabase.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void dangKyAccount(KhachHang kh){
        DatabaseReference myRef = database.getReference("USER").child(kh.getSdt());

        myRef.setValue(kh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            }
        });
    }

}
