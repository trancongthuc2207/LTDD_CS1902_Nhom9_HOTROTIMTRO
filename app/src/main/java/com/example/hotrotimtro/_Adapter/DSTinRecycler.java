package com.example.hotrotimtro._Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class DSTinRecycler extends RecyclerView.Adapter<DSTinRecycler.DSTinViewHolder> {

    public interface OnItemClickListener {
        void onAddPressed(NhaTro nhaTro);
    }

    private Context context;
    private List<NhaTro> listNhaTro;
    private final OnItemClickListener onItemClickListener;

    public DSTinRecycler(Context context, List<NhaTro> listNhaTro, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listNhaTro = listNhaTro;
        this.onItemClickListener = onItemClickListener;
    }

    public void setListNhaTro(List<NhaTro> listNhaTro) {
        this.listNhaTro = listNhaTro;
    }

    @NonNull
    public DSTinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_ds_tin_recycler, parent, false);
        return new DSTinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DSTinViewHolder viewHolder, int position) {

        viewHolder.bind(context, viewHolder, listNhaTro.get(position), onItemClickListener);

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

    public static class DSTinViewHolder extends RecyclerView.ViewHolder {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceNhaTroDaLuu = database.getReference().child("DanhSachNhaTroDaLuu");

        ImageView imgvNhaTro;
        TextView txtDeMuc, txtDiaChi, txtDienTich, txtGia;
        ImageButton imgbtnAddList;
        LinearLayout itemsDSTin;

        public DSTinViewHolder(@NonNull View itemView) {
            super(itemView);
            itemsDSTin = itemView.findViewById(R.id.itemsDSTin);
            imgvNhaTro = itemView.findViewById(R.id.imgvNhaTro);
            txtDeMuc = itemView.findViewById(R.id.txtDeMuc);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            txtDienTich = itemView.findViewById(R.id.txtDienTich);
            txtGia = itemView.findViewById(R.id.txtGia);
            imgbtnAddList = itemView.findViewById(R.id.imgbtnAddList);
        }

        public void bind(Context context, @NonNull DSTinViewHolder viewHolder, final NhaTro nhaTro, final OnItemClickListener onItemClickListener) {
            databaseReferenceNhaTroDaLuu.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (Objects.equals(dataSnapshot.getKey(), nhaTro.getMaNT())) {
                            viewHolder.imgbtnAddList.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_list_focused));
                            viewHolder.imgbtnAddList.setEnabled(false);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("Database Error");
                }
            });

            imgbtnAddList.setOnClickListener(v -> onItemClickListener.onAddPressed(nhaTro));
        }
    }
}