package com.example.hotrotimtro._Login;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotrotimtro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    EditText txtTK;
    EditText txtMK;

    Button btnDN;
    Button btnDK;
    //
    private ProgressDialog progressDialog;
    ///
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        //
        progressDialog = new ProgressDialog(this);
        ///
        btnDN = findViewById(R.id.btnDN);
        btnDK = findViewById(R.id.btnDK);
        txtTK = findViewById(R.id.txtTK);
        txtMK = findViewById(R.id.txtMK);
        ///
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                firebaseDatabase = FirebaseDatabase.getInstance();
//                databaseReference = firebaseDatabase.getReferenceFromUrl("https://hotrotimtro-e45df-default-rtdb.firebaseio.com/");
//                databaseReference.child("USER").child("User2").child("username").setValue("hung222");
                String tk = txtTK.getText().toString();
                String mk = txtMK.getText().toString();

                if(tk.equals("thuc") && mk.equals("123"))
                    Toast.makeText(Login.this,"DANG NHAP THANH CONG!!",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Login.this,"DANG NHAP THAT BAI!!",Toast.LENGTH_SHORT).show();
            }
        });

        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButtonDK();
            }
        });
    }
    public void onClickButtonDK(){
        String strEmail = txtTK.getText().toString();
        String strPass = txtMK.getText().toString();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.createUserWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Login.this, "Successed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            FirebaseUser user = auth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
