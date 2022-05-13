package com.example.hotrotimtro._Service;

import com.example.hotrotimtro._Pojo.NhaTro;

import java.util.Comparator;

public class GiaComparatorDESC implements Comparator<NhaTro> {
    @Override
    public int compare(NhaTro nhaTro1, NhaTro nhaTro2) {
        return Double.compare(nhaTro2.getGia(), nhaTro1.getGia());
    }
}