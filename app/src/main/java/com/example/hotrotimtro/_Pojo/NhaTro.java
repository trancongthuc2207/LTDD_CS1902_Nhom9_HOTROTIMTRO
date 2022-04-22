package com.example.hotrotimtro._Pojo;

public class NhaTro {
    String MaNT = "";
    String moTa = "";
    double dienTich = 0.0;
    int isThue = 0;
    boolean isGac = false;
    double gia  = 0.0;
    String diaChi = "";

    public NhaTro(){super();};

    public NhaTro(String maNT, String moTa, double dienTich, int isThue, boolean isGac, double gia, String diaChi) {
        MaNT = maNT;
        this.moTa = moTa;
        this.dienTich = dienTich;
        this.isThue = isThue;
        this.isGac = isGac;
        this.gia = gia;
        this.diaChi = diaChi;
    }

    public String getMaNT() {
        return MaNT;
    }

    public void setMaNT(String maNT) {
        MaNT = maNT;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getDienTich() {
        return dienTich;
    }

    public void setDienTich(double dienTich) {
        this.dienTich = dienTich;
    }

    public int getIsThue() {
        return isThue;
    }

    public void setIsThue(int isThue) {
        this.isThue = isThue;
    }

    public boolean isGac() {
        return isGac;
    }

    public void setGac(boolean gac) {
        isGac = gac;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
