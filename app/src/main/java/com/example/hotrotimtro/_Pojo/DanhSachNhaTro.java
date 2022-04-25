package com.example.hotrotimtro._Pojo;


import java.util.ArrayList;
import java.util.List;

public class DanhSachNhaTro {
    List<NhaTro> dsNhaTro = new ArrayList<>();

    ///Them nha tro
    public void addNhaTro(NhaTro nhaTro){
        this.dsNhaTro.add(nhaTro);
    }

    //Xoa nha tro theo ma~
    public  void removeNhaTro(String maNT){
        for(NhaTro nt : this.dsNhaTro)
        {
            if(nt.getMaNT().equals(maNT))
                this.dsNhaTro.remove(nt);
        }

    }

    ///
    public DanhSachNhaTro(){super();};

    public List<NhaTro> getDsNhaTro() {
        return dsNhaTro;
    }

    public void setDsNhaTro(List<NhaTro> dsNhaTro) {
        this.dsNhaTro = dsNhaTro;
    }
}
