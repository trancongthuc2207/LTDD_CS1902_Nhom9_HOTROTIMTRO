package com.example.hotrotimtro._Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotrotimtro.MainActivity;
import com.example.hotrotimtro.R;
import com.example.hotrotimtro._SignUp.SignUpActivity;
import com.example.hotrotimtro._TrangChu.TrangChu;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText txtTK;
    TextInputEditText txtMK;

    Button btnDN;

    ImageView imgvBackMain;
    TextView txtDK;

    CheckBox rememberMe;
    //
    private ProgressDialog progressDialog;
    ///
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /////////////////////////////////////////////THỨC
        anhXa();
        //
        progressDialog = new ProgressDialog(this);

        btnDN.setOnClickListener(view -> onClickButtonDN());
        /////////////////////////////////////////////HƯNG
        imgvBackMain.setOnClickListener(view -> {
            //onBackPressed();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        txtDK.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
    public void onClickButtonDN()
    {
        String strEmail = Objects.requireNonNull(txtTK.getText()).toString().trim();
        String strPass = Objects.requireNonNull(txtMK.getText()).toString().trim();
        auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.signInWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Intent main = new Intent(LoginActivity.this, TrangChu.class);
                        startActivity(main);
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"DANG NHAP THANH CONG!!",Toast.LENGTH_SHORT).show();
                        finishAffinity();
                    } else {
                        Toast.makeText(LoginActivity.this,"DANG NHAP THAT BAI!!",Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });
    }

    public void anhXa()
    {
        txtTK = findViewById(R.id.txtTK);
        txtMK = findViewById(R.id.txtMK);
        btnDN = findViewById(R.id.btnDN);

        imgvBackMain = findViewById(R.id.imgvBackMain);
        txtDK = findViewById(R.id.txtDK);
        rememberMe = findViewById(R.id.rememberMe);
    }
}