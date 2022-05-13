package com.example.hotrotimtro._Fragment_Object;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Adapter.DSTinUserRecycler;
import com.example.hotrotimtro._Adapter.SearchNoDataRecycler;
import com.example.hotrotimtro._Fragment_Activity.DangTinActivity;
import com.example.hotrotimtro._Fragment_Activity.SuaTinActivity;
import com.example.hotrotimtro._Pojo.NhaTro;
import com.example.hotrotimtro._Pojo.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DangTin_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DangTin_Fragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference, databaseReferenceUser;
    ProgressDialog progressDialog;

    private RecyclerView recyclerViewDSTinUser;
    private DSTinUserRecycler dSTinUserRecycler;

    User user;
    NhaTro nhaTro;
    List<NhaTro> listNhaTroUser;

    private SearchNoDataRecycler searchNoDataRecycler;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public DangTin_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangTin_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DangTin_Fragment newInstance(String param1, String param2) {
        DangTin_Fragment fragment = new DangTin_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Vui lòng chờ trong giây lát");
        progressDialog.show();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dang_tin, container, false);

        // FindViewById
        SearchView searchView = view.findViewById(R.id.searchDangTin);
        recyclerViewDSTinUser = view.findViewById(R.id.recyclerViewDSTinDaLuu);

        listNhaTroUser = new ArrayList<>();

        // databaseReference
        user = new User();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("DanhSachNhaTro");
        databaseReferenceUser = database.getReference().child("Users");

        // recyclerView DANH SACH TIN USER
        dSTinUserRecycler = new DSTinUserRecycler(getActivity(), listNhaTroUser);
        recyclerViewDSTinUser.setHasFixedSize(true);
        recyclerViewDSTinUser.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewDSTinUser.setAdapter(dSTinUserRecycler);

        // GET DATA
        getData();

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
                List<NhaTro> filteredList = new ArrayList<>();
                for (NhaTro nhaTro : listNhaTroUser) {
                    if (nhaTro.getDeMuc().toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(nhaTro);
                    }
                }
                if (filteredList.isEmpty()) {
                    recyclerViewDSTinUser.setAdapter(searchNoDataRecycler);
                    searchNoDataRecycler.notifyDataSetChanged();
                } else {
                    recyclerViewDSTinUser.setAdapter(dSTinUserRecycler);
                    dSTinUserRecycler.setListNhaTro(filteredList);
                    dSTinUserRecycler.notifyDataSetChanged();
                }
                return true;
            }
        });

        // ĐĂNG TIN
        view.findViewById(R.id.imgbtnDangTin).setOnClickListener(view1 -> {
            if (user.isActiveAccount()) {
                Intent i = new Intent(getActivity(), DangTinActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getActivity(), "Cập nhật thông tin hồ sơ để dùng chức năng này.", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 101:
                Intent i1 = new Intent(getActivity(), SuaTinActivity.class);
                i1.putExtra("maNT", listNhaTroUser.get(item.getGroupId()).getMaNT());
                i1.putExtra("deMuc", listNhaTroUser.get(item.getGroupId()).getDeMuc());
                i1.putExtra("moTa", listNhaTroUser.get(item.getGroupId()).getMoTa());
                i1.putExtra("diaChi", listNhaTroUser.get(item.getGroupId()).getDiaChi());
                i1.putExtra("imageUrl", listNhaTroUser.get(item.getGroupId()).getImageUrl().get(0));
                i1.putExtra("dienTich", String.valueOf(listNhaTroUser.get(item.getGroupId()).getDienTich()));
                i1.putExtra("gia", String.valueOf(listNhaTroUser.get(item.getGroupId()).getGia()));
                i1.putExtra("luotThich", String.valueOf(listNhaTroUser.get(item.getGroupId()).getLuotThich()));
                i1.putExtra("trangThai", String.valueOf(listNhaTroUser.get(item.getGroupId()).getIsThue()));
                startActivity(i1);
                return true;
            case 102:
                new AlertDialog.Builder(getActivity())
                        .setMessage("Bạn có chắc muốn xóa tin này?")
                        .setCancelable(false)
                        .setNegativeButton("Hủy bỏ", null)
                        .setPositiveButton("Đồng ý", (dialogInterface, i2) -> dSTinUserRecycler.xoaTin(item.getGroupId())).create().show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void getData() {
        databaseReferenceUser.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database Error");
            }
        });

        databaseReference.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listNhaTroUser.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    nhaTro = dataSnapshot.getValue(NhaTro.class);
                    listNhaTroUser.add(nhaTro);
                }
                dSTinUserRecycler.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database Error");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}