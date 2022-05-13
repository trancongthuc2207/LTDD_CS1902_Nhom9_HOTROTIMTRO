package com.example.hotrotimtro._SignUp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotrotimtro.MainActivity;
import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Login.LoginActivity;

import com.example.hotrotimtro._Pojo.User;
import com.google.android.gms.common.util.Strings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText txtTK;
    TextInputEditText txtMK;
    TextInputEditText txtNhapLaiMK;

    Button btnDK;

    TextInputEditText txtName;
    TextInputEditText txtSDT;

    ImageView imgvBack;
    TextView txtDN;

    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /////////////////////////////////////////////THỨC
        anhXa();
        progressDialog = new ProgressDialog(this);
        btnDK.setOnClickListener(view -> onClickButtonDK());

        /////////////////////////////////////////////HƯNG
        imgvBack.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        txtDN.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    public void onClickButtonDK() {
        String strHoTen = Objects.requireNonNull(txtName.getText()).toString().trim();
        String strSDT = Objects.requireNonNull(txtSDT.getText()).toString().trim();
        String strEmail = Objects.requireNonNull(txtTK.getText()).toString().trim();
        String strPass = Objects.requireNonNull(txtMK.getText()).toString().trim();
        String strPassRepeat = Objects.requireNonNull(txtNhapLaiMK.getText()).toString().trim();

        if (checkFullInfor()) {
            if (!txtTK.getText().toString().contains("@") || !txtTK.getText().toString().contains(".")) {
                Toast.makeText(this, "Yêu cầu email của bạn bắt buộc phải có ký tự '@' và ký tự '.' ", Toast.LENGTH_SHORT).show();
            } else if (!strPass.equals(strPassRepeat)) {
                Toast.makeText(SignUpActivity.this, "Hai mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Đang đăng ký");
                progressDialog.show();
                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        User user = new User(strHoTen, "Chưa cập nhật", "Chưa cập nhật", strSDT, "Chưa cập nhật", "Chưa cập nhật", "", false);
                        FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "Đăng ký thành công.", Toast.LENGTH_LONG).show();
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "Đăng ký thất bại.", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "Đăng ký thất bại.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

    }

    public void anhXa() {
        txtTK = findViewById(R.id.txtTK);
        txtMK = findViewById(R.id.txtMK);
        txtNhapLaiMK = findViewById(R.id.txtNhapLaiMK);
        btnDK = findViewById(R.id.btnDK);
        txtName = findViewById(R.id.txtName);
        txtSDT = findViewById(R.id.txtSDT);
        imgvBack = findViewById(R.id.imgvBack);
        txtDN = findViewById(R.id.txtDN);
    }

    private boolean checkFullInfor() {
        if (Strings.isEmptyOrWhitespace(Objects.requireNonNull(txtName.getText()).toString()) || Strings.isEmptyOrWhitespace(Objects.requireNonNull(txtTK.getText()).toString()) || Strings.isEmptyOrWhitespace(Objects.requireNonNull(txtMK.getText()).toString()) || Strings.isEmptyOrWhitespace(Objects.requireNonNull(txtNhapLaiMK.getText()).toString()) || Strings.isEmptyOrWhitespace(Objects.requireNonNull(txtSDT.getText()).toString())) {
            if (Strings.isEmptyOrWhitespace(txtName.getText().toString())) {
                Toast.makeText(this, "Mời bạn nhập họ tên.", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (Strings.isEmptyOrWhitespace(Objects.requireNonNull(txtSDT.getText()).toString())) {
                Toast.makeText(this, "Mời bạn nhập số điện thoại.", Toast.LENGTH_SHORT).show();
                return false;
            } else if (txtSDT.getText().toString().length() != 10) {
                Toast.makeText(this, "Yêu cầu số điện thoại của bạn bắt buộc phải có 10 số.", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (Strings.isEmptyOrWhitespace(Objects.requireNonNull(txtTK.getText()).toString())) {
                Toast.makeText(this, "Mời bạn nhập email.", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!txtTK.getText().toString().contains("@") || !txtTK.getText().toString().contains(".")) {
                Toast.makeText(this, "Yêu cầu email của bạn bắt buộc phải có ký tự '@' và ký tự '.' ", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (Strings.isEmptyOrWhitespace(Objects.requireNonNull(txtMK.getText()).toString()) || Strings.isEmptyOrWhitespace(Objects.requireNonNull(txtNhapLaiMK.getText()).toString())) {
                Toast.makeText(this, "Mời bạn nhập mật khẩu hoặc mật khẩu nhập lại", Toast.LENGTH_SHORT).show();
                return false;
            } else if (txtMK.getText().toString().length() <= 6 || txtNhapLaiMK.getText().toString().length() <= 6) {
                Toast.makeText(this, "Yêu cầu mật khẩu hoặc mật khẩu nhập lại của bạn bắt buộc phải có 6 ký tự.", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!txtMK.getText().toString().equals(txtNhapLaiMK.getText().toString())) {
                Toast.makeText(this, "Yêu cầu mật khẩu và mật khẩu nhập lại của bạn bắt buộc phải giống nhau.", Toast.LENGTH_SHORT).show();
                return false;
            }
            return false;
        }
        return true;
    }
}