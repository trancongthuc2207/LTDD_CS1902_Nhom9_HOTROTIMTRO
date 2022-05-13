package com.example.hotrotimtro._Login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText txtTK;
    TextInputEditText txtMK;

    Button btnDN;

    ImageView imgvBackMain;
    TextView txtDK, txtQuenMK;

    CheckBox rememberMe;

    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhXa();
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        btnDN.setOnClickListener(view -> onClickButtonDN());

        imgvBackMain.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        txtQuenMK.setOnClickListener(view -> {
            EditText edtMail = new EditText(view.getContext());
            edtMail.setHint("Nhập vào địa chỉ email của bạn.");
            final AlertDialog.Builder passResetDialog = new AlertDialog.Builder(view.getContext());
            passResetDialog.setTitle("LẤY LẠI MẬT KHẨU");
            passResetDialog.setView(edtMail);
            passResetDialog.setNegativeButton("HỦY BỎ", null);
            passResetDialog.setPositiveButton("ĐỒNG Ý", (dialogInterface, i) -> {
                String email = edtMail.getText().toString();
                mAuth.sendPasswordResetEmail(email).addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), "Kiểm tra hòm thư email của bạn.", Toast.LENGTH_LONG).show()).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra, hãy kiểm tra lại.", Toast.LENGTH_SHORT).show());
            });
            passResetDialog.create().show();
        });

        txtDK.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    public void onClickButtonDN() {
        String strEmail = Objects.requireNonNull(txtTK.getText()).toString().trim();
        String strPass = Objects.requireNonNull(txtMK.getText()).toString().trim();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang đăng nhập");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent trangChu = new Intent(LoginActivity.this, TrangChu.class);
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công.", Toast.LENGTH_LONG).show();
                        trangChu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(trangChu);
                        finishAffinity();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void anhXa() {
        txtTK = findViewById(R.id.txtTK);
        txtMK = findViewById(R.id.txtMK);
        btnDN = findViewById(R.id.btnDN);
        imgvBackMain = findViewById(R.id.imgvBack);
        rememberMe = findViewById(R.id.rememberMe);
        txtQuenMK = findViewById(R.id.txtQuenMK);
        txtDK = findViewById(R.id.txtDK);
    }
}