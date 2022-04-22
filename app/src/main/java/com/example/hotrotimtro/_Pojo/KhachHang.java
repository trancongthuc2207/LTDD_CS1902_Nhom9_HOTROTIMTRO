package com.example.hotrotimtro._Pojo;

public class KhachHang {
    String maKH = "";
    String hoTen = "";
    String gioiTinh = "";
    String cmnd = "";
    String sdt = "";
    String diaChi = "";
    String ngheNghiep = "";

    boolean isActiveAccount = true;
    DanhSachNhaTro listNT = null;

    public KhachHang(String maKH, String hoTen, String gioiTinh, String cmnd, String sdt, String diaChi, String ngheNghiep, boolean isActiveAccount, DanhSachNhaTro listNT) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.cmnd = cmnd;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.ngheNghiep = ngheNghiep;
        this.isActiveAccount = isActiveAccount;
        this.listNT = listNT;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
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

    public boolean isActiveAccount() {
        return isActiveAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        isActiveAccount = activeAccount;
    }

    public DanhSachNhaTro getListNT() {
        return listNT;
    }

    public void setListNT(DanhSachNhaTro listNT) {
        this.listNT = listNT;
    }
}
