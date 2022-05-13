package com.example.hotrotimtro._Service;

import com.example.hotrotimtro._Pojo.NhaTro;

import java.util.Comparator;

public class NhaTroYeuThichComparatorDESC implements Comparator<NhaTro> {
    @Override
    public int compare(NhaTro nhaTro1, NhaTro nhaTro2) {
        return Integer.compare(nhaTro2.getLuotThich(), nhaTro1.getLuotThich());
    }
}
