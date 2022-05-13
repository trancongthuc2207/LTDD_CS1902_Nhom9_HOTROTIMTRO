package com.example.hotrotimtro._Fragment_Object;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Fragment_Activity.SuaHoSoActivity;
import com.example.hotrotimtro._Login.LoginActivity;
import com.example.hotrotimtro._Pojo.User;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link XemThem_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XemThem_Fragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    ShapeableImageView imgvAvt;
    TextView txtHoTen, txtCMND, txtNgheNghiep, txtThanhPho, txtDiaChi, txtSDT, txtGioiTinh;

    User user;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public XemThem_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment XemThem_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static XemThem_Fragment newInstance(String param1, String param2) {
        XemThem_Fragment fragment = new XemThem_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_xem_them, container, false);

        anhXa(view);

        // --------------------------------------------- GET & SET DATA
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Users");
        databaseReference.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                assert user != null;
                if (!user.getImageUrlAvt().equals("")) {
                    Glide.with(requireActivity()).load(user.getImageUrlAvt()).into(imgvAvt);
                }
                txtHoTen.setText(user.getHoTen());
                txtCMND.setText(user.getCmnd());
                txtNgheNghiep.setText(user.getNgheNghiep());
                txtThanhPho.setText(user.getDiaChi().substring(user.getDiaChi().lastIndexOf(", ") + 1).trim());
                txtDiaChi.setText(user.getDiaChi());
                txtSDT.setText(user.getSdt());
                txtGioiTinh.setText(user.getGioiTinh());
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database Error");
            }
        });

        // ĐỔI MẬT KHẨU
        view.findViewById(R.id.imgvDoiMK).setOnClickListener(view1 -> {
            EditText edtMail = new EditText(view1.getContext());
            edtMail.setHint("Nhập vào địa chỉ email của bạn.");
            final AlertDialog.Builder passResetDialog = new AlertDialog.Builder(view1.getContext());
            passResetDialog.setTitle("XÁC NHẬN ĐỔI MẬT KHẨU");
            passResetDialog.setView(edtMail);
            passResetDialog.setNegativeButton("HỦY BỎ", null);
            passResetDialog.setPositiveButton("ĐỒNG Ý", (dialogInterface, i) -> {
                String email = edtMail.getText().toString();
                mAuth.sendPasswordResetEmail(email).addOnSuccessListener(unused -> Toast.makeText(getActivity(), "Biểu mẫu đã gửi, kiểm tra hòm thư email của bạn.", Toast.LENGTH_LONG).show()).addOnFailureListener(e -> Toast.makeText(getActivity(), "Đã có lỗi xảy ra, hãy kiểm tra lại.", Toast.LENGTH_SHORT).show());
            });
            passResetDialog.create().show();
        });

        // LOGOUT
        view.findViewById(R.id.imgvLogout).setOnClickListener(view1 -> {
            mAuth.signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // EDIT PROFILE
        view.findViewById(R.id.imgvEditProfile).setOnClickListener(view1 -> {
            Intent i = new Intent(getActivity(), SuaHoSoActivity.class);
            i.putExtra("imageUrlAvt", user.getImageUrlAvt());
            i.putExtra("hoTen", user.getHoTen());
            i.putExtra("sdt", user.getSdt());
            i.putExtra("cmnd", user.getCmnd());
            i.putExtra("diaChi", user.getDiaChi());
            i.putExtra("ngheNghiep", user.getNgheNghiep());
            i.putExtra("gioiTinh", user.getGioiTinh());
            i.putExtra("isActiveAccount", String.valueOf(user.isActiveAccount()));
            startActivity(i);
        });

        return view;
    }

    private void anhXa(View view) {
        imgvAvt = view.findViewById(R.id.imgvAvt);
        txtHoTen = view.findViewById(R.id.txtHoTen);
        txtCMND = view.findViewById(R.id.txtCMND);
        txtNgheNghiep = view.findViewById(R.id.txtNgheNghiep);
        txtThanhPho = view.findViewById(R.id.txtThanhPho);
        txtDiaChi = view.findViewById(R.id.txtDiaChi);
        txtSDT = view.findViewById(R.id.txtSDT);
        txtGioiTinh = view.findViewById(R.id.txtGioiTinh);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}