package com.example.hotrotimtro._Fragment_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Adapter.DSTinRecycler;
import com.example.hotrotimtro._Adapter.SearchNoDataRecycler;
import com.example.hotrotimtro._Pojo.NhaTro;
import com.example.hotrotimtro._Service.GiaComparatorASC;
import com.example.hotrotimtro._Service.GiaComparatorDESC;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceNhaTroDaLuu;
    ProgressDialog progressDialog;

    private String textListener = "";
    private SearchNoDataRecycler searchNoDataRecycler;

    private RecyclerView recyclerViewDSTin;
    private DSTinRecycler dSTinRecycler;

    NhaTro nhaTro;
    List<NhaTro> listNhaTro;

    ArrayAdapter<String> arrayAdapterDD, arrayAdapterGia;
    AutoCompleteTextView autotxtDiaDiem, autotxtGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Vui lòng chờ trong giây lát");
        progressDialog.show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //ADJUST RESIZE :3
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        SearchView searchView = findViewById(R.id.searchDSTin);

        // FindViewById
        autotxtDiaDiem = findViewById(R.id.autotxtDiaDiem);
        autotxtGia = findViewById(R.id.autotxtGia);
        recyclerViewDSTin = findViewById(R.id.recyclerViewDSTin);

        listNhaTro = new ArrayList<>();

        // databaseReference
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("DanhSachNhaTro");
        databaseReferenceNhaTroDaLuu = database.getReference().child("DanhSachNhaTroDaLuu");

        // recyclerView DANH SACH TIN
        dSTinRecycler = new DSTinRecycler(getApplicationContext(), listNhaTro, nhaTro -> {
            if (nhaTro.getIsThue() == 1) {
                Toast.makeText(getApplicationContext(), "Trọ này đã được thuê!", Toast.LENGTH_SHORT).show();
            } else if (nhaTro.getIsThue() == 0) {
                databaseReferenceNhaTroDaLuu.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(nhaTro.getMaNT()).setValue(nhaTro.getMaNT()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Đã lưu tin.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        recyclerViewDSTin.setHasFixedSize(true);
        recyclerViewDSTin.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewDSTin.setAdapter(dSTinRecycler);

        // GET DATA
        getData();

        // DROPDOWN
        String[] dsDiaDiem = getResources().getStringArray(R.array.dia_diem);
        arrayAdapterDD = new ArrayAdapter<>(this, R.layout.items_text_for_drop_down_op2, dsDiaDiem);
        autotxtDiaDiem.setText(arrayAdapterDD.getItem(0));
        autotxtDiaDiem.setAdapter(arrayAdapterDD);

        // DROPDOWN
        String[] dsGia = getResources().getStringArray(R.array.gia);
        arrayAdapterGia = new ArrayAdapter<>(this, R.layout.items_text_for_drop_down_op2, dsGia);
        autotxtGia.setText(arrayAdapterGia.getItem(0));
        autotxtGia.setAdapter(arrayAdapterGia);

        // searchView
        searchNoDataRecycler = new SearchNoDataRecycler();
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                textListener = newText;
                return true;
            }
        });

        autotxtDiaDiem.setOnItemClickListener((adapterView, view, i, l) -> filterList(textListener));
        autotxtGia.setOnItemClickListener((adapterView, view, i, l) -> filterList(textListener));

        // BACK
        findViewById(R.id.imgbtnBack).setOnClickListener(view -> onBackPressed());
    }

    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listNhaTro.clear();
                for (DataSnapshot snapshotChild1 : snapshot.getChildren()) {
                    for (DataSnapshot snapshotChild2 : snapshotChild1.getChildren()) {
                        nhaTro = snapshotChild2.getValue(NhaTro.class);
                        listNhaTro.add(nhaTro);
                    }
                }
                dSTinRecycler.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database Error");
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filterList(String newText) {
        List<NhaTro> filteredList = new ArrayList<>();
        for (NhaTro nhaTro : listNhaTro) {
            if (newText.equals("")) {
                if (autotxtDiaDiem.getText().toString().equals(arrayAdapterDD.getItem(0))) {
                    filteredList.add(nhaTro);
                } else if (nhaTro.getDiaChi().substring(nhaTro.getDiaChi().lastIndexOf(", ")).trim().contains(autotxtDiaDiem.getText().toString())) {
                    filteredList.add(nhaTro);
                }
            } else if (nhaTro.getDeMuc().toLowerCase().contains(newText.toLowerCase())) {
                if (autotxtDiaDiem.getText().toString().equals(arrayAdapterDD.getItem(0))) {
                    filteredList.add(nhaTro);
                } else if (nhaTro.getDiaChi().substring(nhaTro.getDiaChi().lastIndexOf(", ")).trim().contains(autotxtDiaDiem.getText().toString())) {
                    filteredList.add(nhaTro);
                }
            }
        }

        if (autotxtGia.getText().toString().equals(arrayAdapterGia.getItem(1))) {
            Collections.sort(filteredList, new GiaComparatorDESC());
        } else if (autotxtGia.getText().toString().equals(arrayAdapterGia.getItem(2))) {
            Collections.sort(filteredList, new GiaComparatorASC());
        }

        if (filteredList.isEmpty()) {
            recyclerViewDSTin.setAdapter(searchNoDataRecycler);
            searchNoDataRecycler.notifyDataSetChanged();
        } else {
            recyclerViewDSTin.setAdapter(dSTinRecycler);
            dSTinRecycler.setListNhaTro(filteredList);
            dSTinRecycler.notifyDataSetChanged();
        }
    }
}