package com.example.hotrotimtro._Service;

import com.example.hotrotimtro._Pojo.NhaTro;

import java.util.Comparator;

public class GiaComparatorASC implements Comparator<NhaTro> {
    @Override
    public int compare(NhaTro nhaTro1, NhaTro nhaTro2) {
        return Double.compare(nhaTro1.getGia(), nhaTro2.getGia());
    }
}
