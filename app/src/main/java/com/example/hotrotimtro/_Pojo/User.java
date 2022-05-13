package com.example.hotrotimtro._Pojo;

import androidx.annotation.NonNull;

public class User {

    String hoTen;
    String gioiTinh;
    String cmnd;
    String sdt;
    String diaChi;
    String ngheNghiep;
    String imageUrlAvt;
    boolean activeAccount;

    public User() {super();}

    public User(String hoTen, String gioiTinh, String cmnd, String sdt, String diaChi, String ngheNghiep, String imageUrlAvt, boolean activeAccount) {
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.cmnd = cmnd;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.ngheNghiep = ngheNghiep;
        this.imageUrlAvt = imageUrlAvt;
        this.activeAccount = activeAccount;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgheNghiep() {
        return ngheNghiep;
    }

    public void setNgheNghiep(String ngheNghiep) {
        this.ngheNghiep = ngheNghiep;
    }

    public String getImageUrlAvt() {
        return imageUrlAvt;
    }

    public void setImageUrlAvt(String imageUrlAvt) {
        this.imageUrlAvt = imageUrlAvt;
    }

    public boolean isActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        this.activeAccount = activeAccount;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "hoTen='" + hoTen + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", cmnd='" + cmnd + '\'' +
                ", sdt='" + sdt + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", ngheNghiep='" + ngheNghiep + '\'' +
                ", imageUrlAvt='" + imageUrlAvt + '\'' +
                ", activeAccount=" + activeAccount +
                '}';
    }
}