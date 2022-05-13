package com.example.hotrotimtro._Fragment_Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hotrotimtro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SuaHoSoActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReferenceUser;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    Intent galleryIntent;
    Uri imageUri;
    String imageUrl;

    Intent i;
    ImageView imgvAvt;
    Button btnUploadImage, btnLuu, btnHuy;
    ProgressBar progressBarUpload;
    EditText edtHoTen, edtSDT, edtCMND, edtDiaChi, edtNgheNghiep;
    AutoCompleteTextView autotxtGioiTinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_ho_so);

        //ADJUST RESIZE :3
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        anhXa();
        i = getIntent();
        progressDialog = new ProgressDialog(this);

        // HỦY BỎ
        btnHuy.setOnClickListener(view -> onBackPressed());

        // ---------------------------------------------
        imageUrl = i.getStringExtra("imageUrlAvt");
        String hoTen = i.getStringExtra("hoTen");
        String sdt = i.getStringExtra("sdt");
        String cmnd = i.getStringExtra("cmnd");
        String diaChi = i.getStringExtra("diaChi");
        String ngheNghiep = i.getStringExtra("ngheNghiep");
        String gioiTinh = i.getStringExtra("gioiTinh");
        // ---------------------------------------------
        if (!imageUrl.equals("")) {
            Glide.with(this).load(imageUrl).into(imgvAvt);
        }
        edtHoTen.setText(hoTen);
        edtSDT.setText(sdt);
        edtCMND.setText(cmnd);
        edtDiaChi.setText(diaChi);
        edtNgheNghiep.setText(ngheNghiep);
        String[] dsGioiTinh = getResources().getStringArray(R.array.gioi_tinh);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.items_text_for_drop_down_op1, dsGioiTinh);
        autotxtGioiTinh.setText(gioiTinh);
        autotxtGioiTinh.setAdapter(arrayAdapter);
        // ---------------------------------------------

        // UPLOAD IMAGE
        storageReference = FirebaseStorage.getInstance().getReference();
        imgvAvt.setOnClickListener(view -> {
            galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, 2);
        });
        btnUploadImage.setOnClickListener(view -> {
            if (imageUri != null) {
                uploadToFireBase(imageUri);
            } else {
                Toast.makeText(SuaHoSoActivity.this, "Bạn chưa chọn ảnh.", Toast.LENGTH_SHORT).show();
            }
        });

        // SỬA TIN
        btnLuu.setOnClickListener(view -> {
            if (imageUri != null && imageUrl.equals(i.getStringExtra("imageUrlAvt"))) {
                Toast.makeText(SuaHoSoActivity.this, "Bạn chưa đăng tải ảnh hồ sơ.", Toast.LENGTH_SHORT).show();
            } else {
                // SET DATA
                String hoTen1 = edtHoTen.getText().toString().trim();
                String sdt1 = edtSDT.getText().toString();
                String cmnd1 = edtCMND.getText().toString();
                String diaChi1 = edtDiaChi.getText().toString().trim();
                String ngheNghiep1 = edtNgheNghiep.getText().toString().trim();
                String gioiTinh1 = autotxtGioiTinh.getText().toString();

                if (diaChi1.equals("Chưa cập nhật") || ngheNghiep1.equals("Chưa cập nhật") || gioiTinh1.equals("Chưa cập nhật")) {
                    Toast.makeText(getApplicationContext(), "Vui lòng cập nhật đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Vui lòng chờ trong giây lát");
                    progressDialog.show();

                    Map<String, Object> mapUser = new HashMap<>();
                    mapUser.put("imageUrlAvt", imageUrl);
                    mapUser.put("hoTen", hoTen1);
                    mapUser.put("sdt", sdt1);
                    mapUser.put("cmnd", cmnd1);
                    mapUser.put("diaChi", diaChi1);
                    mapUser.put("ngheNghiep", ngheNghiep1);
                    mapUser.put("gioiTinh", gioiTinh1);
                    mapUser.put("activeAccount", true);

                    // databaseReference
                    mAuth = FirebaseAuth.getInstance();
                    database = FirebaseDatabase.getInstance();
                    databaseReferenceUser = database.getReference().child("Users");
                    databaseReferenceUser.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).updateChildren(mapUser).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            progressBarUpload.setProgress(0);
                            Toast.makeText(SuaHoSoActivity.this, "Cập nhật hồ sơ thành công.", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SuaHoSoActivity.this, "Cập nhật sơ thất bại.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void anhXa() {
        imgvAvt = findViewById(R.id.imgvAvt);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        progressBarUpload = findViewById(R.id.progressBarUpload);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtSDT = findViewById(R.id.edtSDT);
        edtCMND = findViewById(R.id.edtCMND);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtNgheNghiep = findViewById(R.id.edtNgheNghiep);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);
        autotxtGioiTinh = findViewById(R.id.autotxtGioiTinh);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imgvAvt.setImageURI(imageUri);
        }
    }

    private void uploadToFireBase(Uri uri) {
        progressBarUpload.setVisibility(View.VISIBLE);
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri1 -> {
            imageUrl = uri1.toString();
            Toast.makeText(SuaHoSoActivity.this, "Đăng tải ảnh hồ sơ thành công.", Toast.LENGTH_SHORT).show();
        })).addOnProgressListener(snapshot -> {
            double progress = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
            progressBarUpload.setProgress((int) progress);
        }).addOnFailureListener(e -> {
            progressBarUpload.setProgress(0);
            Toast.makeText(SuaHoSoActivity.this, "Đăng tải ảnh hồ sơ thất bại.", Toast.LENGTH_SHORT).show();
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