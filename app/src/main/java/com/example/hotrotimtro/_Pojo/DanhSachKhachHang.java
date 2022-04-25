package com.example.hotrotimtro._Pojo;

import java.util.ArrayList;
import java.util.List;

public class DanhSachKhachHang {
    List<KhachHang> dsKhachHang = new ArrayList<>();

    ////// CHUC NANG
    public void addKhachHang(KhachHang kh){
        this.dsKhachHang.add(kh);
    }

    public void removeKhachHang(KhachHang kh){
        this.dsKhachHang.remove(kh);
    }

    public DanhSachKhachHang() {super();
    }

    ///// CONSTRUCTION

    public DanhSachKhachHang(List<KhachHang> dsKhachHang) {
        this.dsKhachHang = dsKhachHang;
    }

    public List<KhachHang> getDsKhachHang() {
        return dsKhachHang;
    }

    public void setDsKhachHang(List<KhachHang> dsKhachHang) {
        this.dsKhachHang = dsKhachHang;
    }
}
