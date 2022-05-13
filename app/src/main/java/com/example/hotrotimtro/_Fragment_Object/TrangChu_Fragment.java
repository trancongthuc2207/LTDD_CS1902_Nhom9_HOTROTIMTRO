package com.example.hotrotimtro._Fragment_Object;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Adapter.DSTinRecycler;
import com.example.hotrotimtro._Adapter.DSTinYeuThichRecycler;
import com.example.hotrotimtro._Fragment_Activity.SearchActivity;
import com.example.hotrotimtro._Pojo.NhaTro;
import com.example.hotrotimtro._Service.NhaTroYeuThichComparatorDESC;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.hotrotimtro._Fragment_Object.TrangChu_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrangChu_Fragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceNhaTroDaLuu;
    ProgressDialog progressDialog;

    private DSTinYeuThichRecycler dSTinYeuThichRecycler;

    private DSTinRecycler dSTinRecycler;

    NhaTro nhaTro;
    List<NhaTro> listNhaTro;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public TrangChu_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrangChu_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.hotrotimtro._Fragment_Object.TrangChu_Fragment newInstance(String param1, String param2) {
        com.example.hotrotimtro._Fragment_Object.TrangChu_Fragment fragment = new com.example.hotrotimtro._Fragment_Object.TrangChu_Fragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Vui lòng chờ trong giây lát");
        progressDialog.show();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        // FindViewById
        RecyclerView recyclerViewDSTinThich = view.findViewById(R.id.recyclerViewDSTinThich);
        RecyclerView recyclerViewDSTin = view.findViewById(R.id.recyclerViewDSTin);

        listNhaTro = new ArrayList<>();

        // databaseReference
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("DanhSachNhaTro");
        databaseReferenceNhaTroDaLuu = database.getReference().child("DanhSachNhaTroDaLuu");

        // recyclerView DANH SACH TIN YÊU THÍCH
        dSTinYeuThichRecycler = new DSTinYeuThichRecycler(getActivity(), listNhaTro);
        recyclerViewDSTinThich.setHasFixedSize(true);
        recyclerViewDSTinThich.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewDSTinThich.setAdapter(dSTinYeuThichRecycler);

        // recyclerView DANH SACH TIN
        dSTinRecycler = new DSTinRecycler(getActivity(), listNhaTro, nhaTro -> {
            if (nhaTro.getIsThue() == 1) {
                Toast.makeText(getActivity(), "Trọ này đã được thuê.", Toast.LENGTH_SHORT).show();
            } else if (nhaTro.getIsThue() == 0) {
                databaseReferenceNhaTroDaLuu.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(nhaTro.getMaNT()).setValue(nhaTro.getMaNT()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Đã lưu tin.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        recyclerViewDSTin.setHasFixedSize(true);
        recyclerViewDSTin.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewDSTin.setAdapter(dSTinRecycler);

        // GET DATA
        getData();

        // TÌM KIẾM
        view.findViewById(R.id.btnSearch).setOnClickListener(view1 -> {
            Intent i = new Intent(getActivity(), SearchActivity.class);
            startActivity(i);
        });

        return view;
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
                Collections.sort(listNhaTro, new NhaTroYeuThichComparatorDESC());
                dSTinYeuThichRecycler.notifyDataSetChanged();
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