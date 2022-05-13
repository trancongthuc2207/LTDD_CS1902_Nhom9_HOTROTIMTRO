package com.example.hotrotimtro._Fragment_Object;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Adapter.DSTinDaLuuRecycler;
import com.example.hotrotimtro._Pojo.NhaTro;
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
 * Use the {@link MucDaLuu_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MucDaLuu_Fragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReferenceNhaTro, databaseReferenceNhaTroLuu;
    ProgressDialog progressDialog;

    private DSTinDaLuuRecycler dSTinDaLuuRecycler;

    NhaTro nhaTro;
    List<NhaTro> listNhaTroDaLuu;
    List<String> refKeyNhaTroDaLuu;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public MucDaLuu_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MucDaLuu_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MucDaLuu_Fragment newInstance(String param1, String param2) {
        MucDaLuu_Fragment fragment = new MucDaLuu_Fragment();
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Vui lòng chờ trong giây lát");
        progressDialog.show();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_muc_da_luu, container, false);

        // FindViewById
        RecyclerView recyclerViewDSTinDaLuu = view.findViewById(R.id.recyclerViewDSTinDaLuu);

        refKeyNhaTroDaLuu = new ArrayList<>();
        listNhaTroDaLuu = new ArrayList<>();

        // databaseReference
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReferenceNhaTro = database.getReference().child("DanhSachNhaTro");
        databaseReferenceNhaTroLuu = database.getReference().child("DanhSachNhaTroDaLuu");

        // recyclerView DANH SACH TIN ĐÃ LƯU
        dSTinDaLuuRecycler = new DSTinDaLuuRecycler(getActivity(), listNhaTroDaLuu, (nhaTro, position) -> new AlertDialog.Builder(getActivity())
                .setMessage("Bạn có chắc muốn loại tin này?")
                .setCancelable(false)
                .setNegativeButton("Hủy bỏ", null)
                .setPositiveButton("Đồng ý", (dialogInterface, i) ->
                        databaseReferenceNhaTroLuu.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .child(nhaTro.getMaNT())
                                .removeValue()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        listNhaTroDaLuu.remove(position);
                                        dSTinDaLuuRecycler.notifyDataSetChanged();
                                    }
                                })).create().show());
        recyclerViewDSTinDaLuu.setHasFixedSize(true);
        recyclerViewDSTinDaLuu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewDSTinDaLuu.setAdapter(dSTinDaLuuRecycler);

        // GET DATA
        getData();

        return view;
    }

    private void getData() {
        databaseReferenceNhaTroLuu.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                refKeyNhaTroDaLuu.clear();
                for (DataSnapshot snapshotChild : snapshot.getChildren()) {
                    refKeyNhaTroDaLuu.add(snapshotChild.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database Error");
            }
        });

        databaseReferenceNhaTro.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listNhaTroDaLuu.clear();
                for (String i : refKeyNhaTroDaLuu) {
                    for (DataSnapshot snapshotChild1 : snapshot.getChildren()) {
                        for (DataSnapshot snapshotChild2 : snapshotChild1.getChildren()) {
                            if (Objects.equals(snapshotChild2.getKey(), i)) {
                                nhaTro = snapshotChild2.getValue(NhaTro.class);
                                listNhaTroDaLuu.add(nhaTro);
                            }
                        }
                    }
                }
                dSTinDaLuuRecycler.notifyDataSetChanged();
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