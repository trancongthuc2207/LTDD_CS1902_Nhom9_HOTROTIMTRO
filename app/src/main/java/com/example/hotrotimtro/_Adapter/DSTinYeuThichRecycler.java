package com.example.hotrotimtro._Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DSTinYeuThichRecycler extends RecyclerView.Adapter<DSTinYeuThichRecycler.DSTinYeuThichViewHolder> {

    private Context context;
    private List<NhaTro> listNhaTro;

    public DSTinYeuThichRecycler(Context context, List<NhaTro> listNhaTro) {
        this.context = context;
        this.listNhaTro = listNhaTro;
    }

    @NonNull
    public DSTinYeuThichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_ds_tin_yeu_thich_recycler, parent, false);
        return new DSTinYeuThichViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DSTinYeuThichViewHolder viewHolder, int position) {
        List<String> image1Url = listNhaTro.get(position).getImageUrl();
        Glide.with(context).load(image1Url.get(0)).into(viewHolder.imgvNhaTro);

        String diaChi = listNhaTro.get(position).getDiaChi();
        String thanhPho = diaChi.substring(diaChi.lastIndexOf(",") + 1).trim();
        viewHolder.txtThanhPho.setText(thanhPho);
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

    public static class DSTinYeuThichViewHolder extends RecyclerView.ViewHolder {

        ImageView imgvNhaTro;
        TextView txtThanhPho, txtDienTich, txtGia;
        LinearLayout itemsDSTinYeuThich;

        public DSTinYeuThichViewHolder(@NonNull View itemView) {
            super(itemView);
            itemsDSTinYeuThich = itemView.findViewById(R.id.itemsDSTinYeuThich);
            imgvNhaTro = itemView.findViewById(R.id.imgvNhaTro);
            txtThanhPho = itemView.findViewById(R.id.autotxtThanhPho);
            txtDienTich = itemView.findViewById(R.id.txtDienTich);
            txtGia = itemView.findViewById(R.id.txtGia);

        }
    }
}
