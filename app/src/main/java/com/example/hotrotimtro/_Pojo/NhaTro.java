package com.example.hotrotimtro._Pojo;

import androidx.annotation.NonNull;

import java.util.List;

public class NhaTro {
    String maNT;
    String deMuc;
    String moTa;
    String diaChi;
    String thoiGian;
    List<String> imageUrl;
    double dienTich;
    double gia;
    int luotThich;
    int isThue;

    public NhaTro(){super();}

    public NhaTro(String maNT, String deMuc, String moTa, String diaChi, String thoiGian, List<String> imageUrl, double dienTich, double gia, int luotThich, int isThue) {
        this.maNT = maNT;
        this.deMuc = deMuc;
        this.moTa = moTa;
        this.diaChi = diaChi;
        this.thoiGian = thoiGian;
        this.imageUrl = imageUrl;
        this.dienTich = dienTich;
        this.gia = gia;
        this.luotThich = luotThich;
        this.isThue = isThue;
    }

    public String getMaNT() {
        return maNT;
    }

    public void setMaNT(String maNT) {
        this.maNT = maNT;
    }

    public String getDeMuc() {
        return deMuc;
    }

    public void setDeMuc(String deMuc) {
        this.deMuc = deMuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) { this.imageUrl = imageUrl; }

    public double getDienTich() {
        return dienTich;
    }

    public void setDienTich(double dienTich) {
        this.dienTich = dienTich;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getLuotThich() {
        return luotThich;
    }

    public void setLuotThich(int luotThich) {
        this.luotThich = luotThich;
    }

    public int getIsThue() {
        return isThue;
    }

    public void setIsThue(int isThue) {
        this.isThue = isThue;
    }

    @NonNull
    @Override
    public String toString() {
        return "NhaTro{" +
                "maNT='" + maNT + '\'' +
                ", deMuc='" + deMuc + '\'' +
                ", moTa='" + moTa + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", thoiGian='" + thoiGian + '\'' +
                ", imageUrl=" + imageUrl +
                ", dienTich=" + dienTich +
                ", gia=" + gia +
                ", luotThich=" + luotThich +
                ", isThue=" + isThue +
                '}';
    }
}
