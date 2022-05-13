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

import java.util.List;

public class DSTinDaLuuRecycler extends RecyclerView.Adapter<DSTinDaLuuRecycler.DSTinDaLuuViewHolder> {

    public interface OnItemClickListener {
        void onClearPressed(NhaTro nhaTro, int position);
    }

    private Context context;
    private List<NhaTro> listNhaTro;
    private final OnItemClickListener onItemClickListener;

    public DSTinDaLuuRecycler(Context context, List<NhaTro> listNhaTro, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listNhaTro = listNhaTro;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    public DSTinDaLuuRecycler.DSTinDaLuuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_ds_tin_da_luu_recycler, parent, false);
        return new DSTinDaLuuRecycler.DSTinDaLuuViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull DSTinDaLuuRecycler.DSTinDaLuuViewHolder viewHolder, int position) {

        viewHolder.bind(listNhaTro.get(position), position, onItemClickListener);

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

    public static class DSTinDaLuuViewHolder extends RecyclerView.ViewHolder {

        ImageButton imgbtnClear;
        ImageView imgvNhaTro;
        TextView txtDeMuc, txtDiaChi, txtDienTich, txtGia;
        LinearLayout itemsDSTin;

        public DSTinDaLuuViewHolder(@NonNull View itemView) {
            super(itemView);
            itemsDSTin = itemView.findViewById(R.id.itemsDSTin);
            imgvNhaTro = itemView.findViewById(R.id.imgvNhaTro);
            txtDeMuc = itemView.findViewById(R.id.txtDeMuc);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            txtDienTich = itemView.findViewById(R.id.txtDienTich);
            txtGia = itemView.findViewById(R.id.txtGia);
            imgbtnClear = itemView.findViewById(R.id.imgbtnClear);
        }

        public void bind(final NhaTro nhaTro, final int position, final OnItemClickListener onItemClickListener) {
            imgbtnClear.setOnClickListener(v -> onItemClickListener.onClearPressed(nhaTro, position));
        }
    }
}