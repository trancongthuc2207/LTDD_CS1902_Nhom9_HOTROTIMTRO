package com.example.hotrotimtro._Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Fragment_Activity.XemTinActivity;
import com.example.hotrotimtro._Pojo.NhaTro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class DSTinUserRecycler extends RecyclerView.Adapter<DSTinUserRecycler.DSTinUserViewHolder> {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("DanhSachNhaTro");
    DatabaseReference databaseReferenceDaThich = database.getReference().child("DanhSachNhaTroDaThich");
    DatabaseReference databaseReferenceDaLuu = database.getReference().child("DanhSachNhaTroDaLuu");

    String maNT;
    private Context context;
    private List<NhaTro> listNhaTro;

    public DSTinUserRecycler(Context context, List<NhaTro> listNhaTro) {
        this.context = context;
        this.listNhaTro = listNhaTro;
    }

    public void setListNhaTro(List<NhaTro> listNhaTro) {
        this.listNhaTro = listNhaTro;
    }

    @NonNull
    public DSTinUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_ds_tin_user_recycler, parent, false);
        return new DSTinUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DSTinUserViewHolder viewHolder, int position) {
        List<String> image1Url = listNhaTro.get(position).getImageUrl();
        Glide.with(context).load(image1Url.get(0)).into(viewHolder.imgvNhaTro);

        viewHolder.txtDeMuc.setText(listNhaTro.get(position).getDeMuc());
        viewHolder.txtDiaChi.setText(listNhaTro.get(position).getDiaChi());
        viewHolder.txtDienTich.setText(String.format("%s m²", listNhaTro.get(position).getDienTich()));
        viewHolder.txtGia.setText(String.format("%s triệu/tháng", listNhaTro.get(position).getGia()));

        viewHolder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(context, XemTinActivity.class);
            i.putExtra("maNT", listNhaTro.get(viewHolder.getAdapterPosition()).getMaNT());
            i.putExtra("deMuc", listNhaTro.get(viewHolder.getAdapterPosition()).getDeMuc());
            i.putExtra("moTa", listNhaTro.get(viewHolder.getAdapterPosition()).getMoTa());
            i.putExtra("diaChi", listNhaTro.get(viewHolder.getAdapterPosition()).getDiaChi());
            i.putExtra("thoiGian", listNhaTro.get(viewHolder.getAdapterPosition()).getThoiGian());
            i.putExtra("imageUrl", listNhaTro.get(viewHolder.getAdapterPosition()).getImageUrl().get(0));
            i.putExtra("dienTich", String.valueOf(listNhaTro.get(viewHolder.getAdapterPosition()).getDienTich()));
            i.putExtra("gia", String.valueOf(listNhaTro.get(viewHolder.getAdapterPosition()).getGia()));
            i.putExtra("luotThich", String.valueOf(listNhaTro.get(viewHolder.getAdapterPosition()).getLuotThich()));
            i.putExtra("isThue", String.valueOf(listNhaTro.get(viewHolder.getAdapterPosition()).getIsThue()));
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return listNhaTro.size();
    }

    public static class DSTinUserViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        ImageButton imgbtnMore;
        ImageView imgvNhaTro;
        TextView txtDeMuc, txtDiaChi, txtDienTich, txtGia;
        LinearLayout itemsDSTinUser;

        public DSTinUserViewHolder(@NonNull View itemView) {
            super(itemView);
            itemsDSTinUser = itemView.findViewById(R.id.itemsDSTinUser);

            imgbtnMore = itemView.findViewById(R.id.imgbtnMore);
            imgbtnMore.setOnCreateContextMenuListener(this);

            imgvNhaTro = itemView.findViewById(R.id.imgvNhaTro);
            txtDeMuc = itemView.findViewById(R.id.txtDeMuc);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            txtDienTich = itemView.findViewById(R.id.txtDienTich);
            txtGia = itemView.findViewById(R.id.txtGia);
        }

        @Override
        public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(), 101, 0, "Chỉnh sửa");
            menu.add(getAdapterPosition(), 102, 1, "Xóa tin");
        }
    }

    public void xoaTin(int position) {
        maNT = listNhaTro.get(position).getMaNT();
        databaseReferenceDaThich.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                for (DataSnapshot dataSnapshot1 : snapshot1.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        if (Objects.requireNonNull(dataSnapshot2.getKey()).equals(maNT)) {
                            databaseReferenceDaThich.child(Objects.requireNonNull(dataSnapshot1.getKey())).child(dataSnapshot2.getKey()).removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database Error");
            }
        });
        databaseReferenceDaLuu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                for (DataSnapshot dataSnapshot3 : snapshot2.getChildren()) {
                    for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                        if (Objects.requireNonNull(dataSnapshot4.getKey()).equals(maNT)) {
                            databaseReferenceDaLuu.child(Objects.requireNonNull(dataSnapshot3.getKey())).child(dataSnapshot4.getKey()).removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database Error");
            }
        });
        databaseReference.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                .child(maNT)
                .removeValue()
                .addOnSuccessListener(unused -> Toast.makeText(context, "Đã xóa.", Toast.LENGTH_SHORT).show());
    }
}