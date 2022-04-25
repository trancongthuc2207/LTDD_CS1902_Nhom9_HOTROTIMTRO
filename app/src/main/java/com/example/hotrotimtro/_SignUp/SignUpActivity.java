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
import com.example.hotrotimtro._TrangChu.TrangChu;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText txtTK;
    TextInputEditText txtMK;
    TextInputEditText txtNhapLaiMK;

    Button btnDK;

    ImageView imgvBackMain;
    TextView txtDN;
    //
    private ProgressDialog progressDialog;
    ///
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        /////////////////////////////////////////////THỨC
        anhXa();
        //
        progressDialog = new ProgressDialog(this);

        btnDK.setOnClickListener(view -> onClickButtonDK());
        /////////////////////////////////////////////HƯNG
        imgvBackMain.setOnClickListener(view -> {
            //onBackPressed();
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
        String strEmail = Objects.requireNonNull(txtTK.getText()).toString().trim();
        String strPass = Objects.requireNonNull(txtMK.getText()).toString().trim(); // Pass hon 6
        //
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.createUserWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(SignUpActivity.this, "Successed", Toast.LENGTH_SHORT).show();
                        //Intent main = new Intent(Login.this, MainActivity.class);
                        //startActivity(main);
                        Intent trangchu = new Intent(SignUpActivity.this, TrangChu.class);
                        startActivity(trangchu);
                        //FirebaseUser user = auth.getCurrentUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    finishAffinity();
                });
    }

    public void anhXa()
    {
        txtTK = findViewById(R.id.txtTK);
        txtMK = findViewById(R.id.txtMK);
        txtNhapLaiMK = findViewById(R.id.txtNhapLaiMK);
        btnDK = findViewById(R.id.btnDK);

        imgvBackMain = findViewById(R.id.imgvBackMain);
        txtDN = findViewById(R.id.txtDN);
    }
}