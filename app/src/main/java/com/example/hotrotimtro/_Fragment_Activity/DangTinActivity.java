package com.example.hotrotimtro._Fragment_Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Pojo.NhaTro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DangTinActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    EditText edtTieuDe, edtDienTich, edtGiaThue, edtDiaChi, edtMoTa;
    AutoCompleteTextView autotxtThanhPho;

    ProgressBar progressBarUpload;

    ImageView imgvNhaTro;
    Intent galleryIntent;
    Uri imageUri;
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_tin);

        //ADJUST RESIZE :3
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        anhXa();
        progressDialog = new ProgressDialog(this);

        // HỦY BỎ
        findViewById(R.id.btnHuy).setOnClickListener(view -> onBackPressed());

        // DROPDOWN
        String[] dsThanhPho = getResources().getStringArray(R.array.thanh_pho);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.items_text_for_drop_down_op1, dsThanhPho);
        autotxtThanhPho.setAdapter(arrayAdapter);

        // UPLOAD IMAGE
        storageReference = FirebaseStorage.getInstance().getReference();
        imgvNhaTro.setOnClickListener(view -> {
            galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, 2);
        });
        findViewById(R.id.btnUploadImage).setOnClickListener(view -> {
            if (imageUri != null) {
                uploadToFireBase(imageUri);
            } else {
                Toast.makeText(DangTinActivity.this, "Bạn chưa chọn ảnh.", Toast.LENGTH_SHORT).show();
            }
        });

        // ĐĂNG TIN
        findViewById(R.id.btnLuu).setOnClickListener(view -> {
            if (imageUri != null && imageUrl.equals("")) {
                Toast.makeText(DangTinActivity.this, "Bạn chưa đăng tải ảnh.", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Vui lòng chờ trong giây lát");
                progressDialog.show();

                // SET DATA
                String deMuc = edtTieuDe.getText().toString().trim();
                double dienTich = Double.parseDouble(edtDienTich.getText().toString());
                double gia = Double.parseDouble(edtGiaThue.getText().toString());
                String diaChi = edtDiaChi.getText().toString().concat(", ".concat(autotxtThanhPho.getText().toString())).trim();
                String moTa = edtMoTa.getText().toString().trim();

                Date date = Calendar.getInstance().getTime();
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String thoiGian = dateFormat.format(date);

                List<String> listImageUrl = new ArrayList<>();
                listImageUrl.add(imageUrl);

                int luotThich = 0;
                int isThue = 0;

                // databaseReference
                mAuth = FirebaseAuth.getInstance();
                databaseReference = FirebaseDatabase.getInstance().getReference("DanhSachNhaTro");
                String pushKey = databaseReference.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).push().getKey();
                NhaTro nhaTro = new NhaTro(pushKey, deMuc, moTa, diaChi, thoiGian, listImageUrl, dienTich, gia, luotThich, isThue);
                assert pushKey != null;
                databaseReference.child(mAuth.getCurrentUser().getUid()).child(pushKey).setValue(nhaTro).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        progressBarUpload.setProgress(0);
                        Toast.makeText(DangTinActivity.this, "Đăng tin thành công.", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(DangTinActivity.this, "Đăng tin thất bại.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void anhXa() {
        imgvNhaTro = findViewById(R.id.imgvNhaTro);
        progressBarUpload = findViewById(R.id.progressBarUpload);
        edtTieuDe = findViewById(R.id.edtTieuDe);
        edtDienTich = findViewById(R.id.edtDienTich);
        edtGiaThue = findViewById(R.id.edtGiaThue);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        autotxtThanhPho = findViewById(R.id.autotxtThanhPho);
        edtMoTa = findViewById(R.id.edtMoTa);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imgvNhaTro.setImageURI(imageUri);
        }
    }

    private void uploadToFireBase(Uri uri) {
        progressBarUpload.setVisibility(View.VISIBLE);
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri1 -> {
            imageUrl = uri1.toString();
            Toast.makeText(DangTinActivity.this, "Đăng tải thành công.", Toast.LENGTH_SHORT).show();
        })).addOnProgressListener(snapshot -> {
            double progress = (100*snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
            progressBarUpload.setProgress((int) progress);
        }).addOnFailureListener(e -> {
            progressBarUpload.setProgress(0);
            Toast.makeText(DangTinActivity.this, "Đăng tải thất bại.", Toast.LENGTH_SHORT).show();
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}