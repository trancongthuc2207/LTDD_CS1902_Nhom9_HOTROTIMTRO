package com.example.hotrotimtro._Fragment_Activity;

import androidx.annotation.NonNull;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hotrotimtro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SuaTinActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReferenceNhaTro, databaseReferenceNhaTroLuu;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    Intent galleryIntent;
    Uri imageUri;
    String imageUrl;

    Intent i;
    ImageView imgvNhaTro;
    Button btnUploadImage, btnLuu, btnHuy;
    ProgressBar progressBarUpload;
    EditText edtTieuDe, edtDienTich, edtGiaThue, edtDiaChi, edtMoTa;
    AutoCompleteTextView autotxtTrangThai, autotxtThanhPho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_tin);

        //ADJUST RESIZE :3
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        anhXa();
        i = getIntent();
        progressDialog = new ProgressDialog(this);

        // HỦY BỎ
        btnHuy.setOnClickListener(view -> onBackPressed());

        // --------------------------------------------- SET DATA & DROPDOWN
        imageUrl = i.getStringExtra("imageUrl");
        if (!imageUrl.equals("")) {
            Glide.with(this).load(imageUrl).into(imgvNhaTro);
        }
        //
        String[] dsTrangThai = getResources().getStringArray(R.array.trang_thai);
        ArrayAdapter<String> arrayAdapterTT = new ArrayAdapter<>(this, R.layout.items_text_for_drop_down_op1, dsTrangThai);
        if (i.getStringExtra("trangThai").equals("1")) {
            autotxtTrangThai.setText(arrayAdapterTT.getItem(1));
        } else {
            autotxtTrangThai.setText(arrayAdapterTT.getItem(0));
        }
        autotxtTrangThai.setAdapter(arrayAdapterTT);
        //
        edtTieuDe.setText(i.getStringExtra("deMuc"));
        edtDienTich.setText(i.getStringExtra("dienTich"));
        edtGiaThue.setText(i.getStringExtra("gia"));

        String forEdtDiaChi = i.getStringExtra("diaChi");
        edtDiaChi.setText(forEdtDiaChi.substring(0, forEdtDiaChi.lastIndexOf(", ")).trim());
        //
        String[] dsThanhPho = getResources().getStringArray(R.array.thanh_pho);
        ArrayAdapter<String> arrayAdapterTP = new ArrayAdapter<>(this, R.layout.items_text_for_drop_down_op1, dsThanhPho);
        String forAuTXTThanhPho = i.getStringExtra("diaChi");
        autotxtThanhPho.setText(forAuTXTThanhPho.substring(forAuTXTThanhPho.lastIndexOf(", ") + 1).trim());
        autotxtThanhPho.setAdapter(arrayAdapterTP);
        //
        edtMoTa.setText(i.getStringExtra("moTa"));
        // ---------------------------------------------

        // UPLOAD IMAGE
        storageReference = FirebaseStorage.getInstance().getReference();
        imgvNhaTro.setOnClickListener(view -> {
            galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, 2);
        });
        btnUploadImage.setOnClickListener(view -> {
            if (imageUri != null) {
                uploadToFireBase(imageUri);
            } else {
                Toast.makeText(SuaTinActivity.this, "Bạn chưa chọn ảnh.", Toast.LENGTH_SHORT).show();
            }
        });

        // SỬA TIN
        btnLuu.setOnClickListener(view -> {
            if (imageUri != null && imageUrl.equals(i.getStringExtra("imageUrl"))) {
                Toast.makeText(SuaTinActivity.this, "Bạn chưa đăng tải ảnh.", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Vui lòng chờ trong giây lát");
                progressDialog.show();

                // SET DATA
                String maNT = i.getStringExtra("maNT");
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

                int luotThich = Integer.parseInt(i.getStringExtra("luotThich"));

                int trangThai;
                if (autotxtTrangThai.getText().toString().equals(arrayAdapterTT.getItem(1))) {
                    trangThai = 1;
                } else {
                    trangThai = 0;
                }

                Map<String, Object> mapNhaTro = new HashMap<>();
                mapNhaTro.put("deMuc", deMuc);
                mapNhaTro.put("diaChi", diaChi);
                mapNhaTro.put("dienTich", dienTich);
                mapNhaTro.put("gia", gia);
                mapNhaTro.put("imageUrl", listImageUrl);
                mapNhaTro.put("isThue", trangThai);
                mapNhaTro.put("luotThich", luotThich);
                mapNhaTro.put("moTa", moTa);
                mapNhaTro.put("thoiGian", thoiGian);

                // databaseReference
                mAuth = FirebaseAuth.getInstance();
                database = FirebaseDatabase.getInstance();
                databaseReferenceNhaTro = database.getReference().child("DanhSachNhaTro");
                databaseReferenceNhaTroLuu = database.getReference().child("DanhSachNhaTroDaLuu");

                assert maNT != null;
                databaseReferenceNhaTro.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(maNT).updateChildren(mapNhaTro).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        progressBarUpload.setProgress(0);
                        Toast.makeText(SuaTinActivity.this, "Sửa tin thành công.", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SuaTinActivity.this, "Sửa tin thất bại.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void anhXa() {
        imgvNhaTro = findViewById(R.id.imgvNhaTro);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        progressBarUpload = findViewById(R.id.progressBarUpload);
        edtTieuDe = findViewById(R.id.edtTieuDe);
        edtDienTich = findViewById(R.id.edtDienTich);
        edtGiaThue = findViewById(R.id.edtGiaThue);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtMoTa = findViewById(R.id.edtMoTa);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);
        autotxtTrangThai = findViewById(R.id.autotxtTrangThai);
        autotxtThanhPho = findViewById(R.id.autotxtThanhPho);
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
            Toast.makeText(SuaTinActivity.this, "Đăng tải thành công.", Toast.LENGTH_SHORT).show();
        })).addOnProgressListener(snapshot -> {
            double progress = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
            progressBarUpload.setProgress((int) progress);
        }).addOnFailureListener(e -> {
            progressBarUpload.setProgress(0);
            Toast.makeText(SuaTinActivity.this, "Đăng tải thất bại.", Toast.LENGTH_SHORT).show();
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void capNhatDaThue(String maNT) {
        databaseReferenceNhaTroLuu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        if (Objects.requireNonNull(dataSnapshot2.getKey()).equals(maNT)) {
                            dataSnapshot2.getRef().removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database Error");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}