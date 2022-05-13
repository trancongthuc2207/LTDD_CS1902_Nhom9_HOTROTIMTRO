package com.example.hotrotimtro._Fragment_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Pojo.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class XemTinActivity extends AppCompatActivity {

    // DATABASE
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReferenceUser, databaseReferenceNhaTro, databaseReferenceNhaTroThich, databaseReferenceNhaTroDaLuu;

    ProgressDialog progressDialog;

    // REFKEY
    List<String> refKey;

    // CONSTRUCTOR ACTIVITY
    boolean daThich;
    int luotThich;
    Map<String, Object> mapLuotThich;

    Intent i;
    TextView txtHeartCount, txtDeMuc, txtGia, txtNameUser, txtThoiGian, txtRating, txtDiaChi, txtDienTich, txtMoTa;
    ImageView imgvBack, imgvNhaTro, imgvAvt;
    ImageButton imgbtnHeart, imgbtnAdd, imgbtnShare, imgbtnBanDo;
    Button btnLienHe;

    // LOCATION
    private static final int REQUEST_CODE = 100;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Vui lòng chờ trong giây lát");
        progressDialog.show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_tin);

        // BEFORE SCREEN
        anhXa();
        i = getIntent();

        // DATABASE
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReferenceUser = database.getReference().child("Users");
        databaseReferenceNhaTro = database.getReference().child("DanhSachNhaTro");
        databaseReferenceNhaTroThich = database.getReference().child("DanhSachNhaTroDaThich");
        databaseReferenceNhaTroDaLuu = database.getReference().child("DanhSachNhaTroDaLuu");

        // LOCATION
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // --------------------------------------------- GET & SET DATA
        mapLuotThich = new HashMap<>();
        refKey = new ArrayList<>();
        getData();

        Glide.with(this).load(i.getStringExtra("imageUrl")).into(imgvNhaTro);
        txtHeartCount.setText(i.getStringExtra("luotThich"));
        txtDeMuc.setText(i.getStringExtra("deMuc"));
        txtGia.setText(i.getStringExtra("gia"));
        txtThoiGian.setText(i.getStringExtra("thoiGian"));
        txtDiaChi.setText(i.getStringExtra("diaChi"));
        txtDienTich.setText(i.getStringExtra("dienTich"));
        txtMoTa.setText(i.getStringExtra("moTa"));

        // ---------------------------------------------

        progressDialog.dismiss();

        // QUAY LẠI
        imgvBack.setOnClickListener(view -> onBackPressed());

        // YÊU THÍCH
        imgbtnHeart.setOnClickListener(view -> onHeartPressed());

        // LƯU TIN
        imgbtnAdd.setOnClickListener(view -> onAddPressed());

        // CHIA SẺ
        imgbtnShare.setOnClickListener(view -> onSharePressed());

        // BẢN ĐỒ
        imgbtnBanDo.setOnClickListener(view -> onBanDoPressed());

        // LIÊN HỆ
        btnLienHe.setOnClickListener(view -> onLienHePressed());
    }

    private void anhXa() {
        txtHeartCount = findViewById(R.id.txtHeartCount);
        txtDeMuc = findViewById(R.id.txtDeMuc);
        txtGia = findViewById(R.id.txtGia);
        txtNameUser = findViewById(R.id.txtName);
        txtThoiGian = findViewById(R.id.txtThoiGian);
        txtRating = findViewById(R.id.txtRating);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtDienTich = findViewById(R.id.txtDienTich);
        txtMoTa = findViewById(R.id.txtMoTa);
        imgvNhaTro = findViewById(R.id.imgvNhaTro);
        imgvAvt = findViewById(R.id.imgvAvt);
        imgvBack = findViewById(R.id.imgvBack);
        imgbtnHeart = findViewById(R.id.imgbtnHeart);
        imgbtnAdd = findViewById(R.id.imgbtnAddList);
        imgbtnShare = findViewById(R.id.imgbtnShare);
        imgbtnBanDo = findViewById(R.id.imgbtnBanDo);
        btnLienHe = findViewById(R.id.btnLienHe);
    }

    private void getData() {
        databaseReferenceNhaTro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                refKey.clear();
                for (DataSnapshot snapshotChild1 : snapshot1.getChildren()) {
                    for (DataSnapshot snapshotChild2 : snapshotChild1.getChildren()) {
                        if (Objects.equals(snapshotChild2.getKey(), i.getStringExtra("maNT"))) {
                            refKey.add(snapshotChild1.getKey());
                            refKey.add(snapshotChild2.getKey());

                            // ---------------------------------------------
                            databaseReferenceUser.child(Objects.requireNonNull(snapshotChild1.getKey())).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    Glide.with(getApplicationContext()).load(Objects.requireNonNull(snapshot2.getValue(User.class)).getImageUrlAvt()).into(imgvAvt);
                                    txtNameUser.setText(Objects.requireNonNull(snapshot2.getValue(User.class)).getHoTen());

                                    // ---------------------------------------------
                                    databaseReferenceNhaTroThich.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                                        @SuppressLint("UseCompatLoadingForDrawables")
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot3) {
                                            for (DataSnapshot snapshotChild3 : snapshot3.getChildren()) {
                                                if (Objects.equals(snapshotChild3.getKey(), refKey.get(1))) {
                                                    imgbtnHeart.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart));
                                                    txtHeartCount.setTextColor(getResources().getColor(R.color.second_color));
                                                    daThich = true;
                                                    break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error3) {
                                            System.out.println("Database Error");
                                        }
                                    });
                                    // ---------------------------------------------
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error2) {
                                    System.out.println("Database Error");
                                }
                            });
                            // ---------------------------------------------
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error1) {
                System.out.println("Database Error");
            }
        });

        databaseReferenceNhaTroDaLuu.addValueEventListener(new ValueEventListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        if (Objects.requireNonNull(dataSnapshot2.getKey()).equals(i.getStringExtra("maNT"))) {
                            imgbtnAdd.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_add_list_focused));
                            imgbtnAdd.setEnabled(false);
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onHeartPressed() {
        luotThich = Integer.parseInt(txtHeartCount.getText().toString());
        if (refKey != null) {
            if (daThich) {
                databaseReferenceNhaTroThich.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(refKey.get(1)).removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mapLuotThich.put("luotThich", luotThich - 1);
                        databaseReferenceNhaTro.child(refKey.get(0)).child(refKey.get(1)).updateChildren(mapLuotThich).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                imgbtnHeart.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_border));
                                txtHeartCount.setTextColor(getResources().getColor(R.color.white));
                                txtHeartCount.setText(String.valueOf(luotThich - 1));
                                daThich = false;
                            }
                        });
                    }
                });
            } else {
                databaseReferenceNhaTroThich.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(refKey.get(1)).setValue(refKey.get(1)).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mapLuotThich.put("luotThich", luotThich + 1);
                        databaseReferenceNhaTro.child(refKey.get(0)).child(refKey.get(1)).updateChildren(mapLuotThich).addOnCompleteListener(task12 -> {
                            if (task12.isSuccessful()) {
                                imgbtnHeart.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart));
                                txtHeartCount.setTextColor(getResources().getColor(R.color.second_color));
                                txtHeartCount.setText(String.valueOf(luotThich + 1));
                                daThich = true;
                                Toast.makeText(getApplicationContext(), "Đã thích.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }
    }

    private void onAddPressed() {
        if (i.getStringExtra("isThue").equals("1")) {
            Toast.makeText(getApplicationContext(), "Trọ này đã được thuê.", Toast.LENGTH_SHORT).show();
        } else if (i.getStringExtra("isThue").equals("0")) {
            databaseReferenceNhaTroDaLuu.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(i.getStringExtra("maNT")).setValue(i.getStringExtra("maNT")).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Đã lưu tin.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void onSharePressed() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String head = i.getStringExtra("deMuc");
        intent.putExtra(Intent.EXTRA_TEXT, head);
        startActivity(Intent.createChooser(intent, "Chia sẻ bằng"));
    }

    private void onBanDoPressed() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        String myLatitude = String.valueOf(addresses.get(0).getLatitude());
                        String myLongitude = String.valueOf(addresses.get(0).getLongitude());
                        String myLocation = myLatitude.concat(",".concat(myLongitude));

                        try {
                            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/".concat(myLocation.concat("/".concat(i.getStringExtra("diaChi")))));
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setPackage("com.google.android.apps.maps");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(XemTinActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onBanDoPressed();
            } else {
                Toast.makeText(getApplicationContext(), "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void onLienHePressed() {
        databaseReferenceUser.child(refKey.get(0)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:".concat(Objects.requireNonNull(snapshot.getValue(User.class)).getSdt())));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database Error");
            }
        });
    }
}