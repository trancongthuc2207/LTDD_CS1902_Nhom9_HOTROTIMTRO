package com.example.hotrotimtro._TrangChu;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Fragment_Object.ChoThue_Fragment;
import com.example.hotrotimtro._Fragment_Object.LichSuThue_Fragment;
import com.example.hotrotimtro._Fragment_Object.TaiKhoan_Fragment;
import com.example.hotrotimtro._Fragment_Object.TrangChu_Fragment;

public class TrangChu extends AppCompatActivity {

    MeowBottomNavigation navBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);

        anhXa();

        navBottom.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        navBottom.add(new MeowBottomNavigation.Model(2, R.drawable.ic_person));
        navBottom.add(new MeowBottomNavigation.Model(3, R.drawable.ic_shopping_cart));
        navBottom.add(new MeowBottomNavigation.Model(4, R.drawable.ic_settings));

        navBottom.setOnShowListener(item -> {
            Fragment fragment;

            if (item.getId() == 1) {
                fragment = new TrangChu_Fragment();
            } else if (item.getId() == 2) {
                fragment = new ChoThue_Fragment();
            } else if (item.getId() == 3) {
                fragment = new LichSuThue_Fragment();
            } else {
                fragment = new TaiKhoan_Fragment();
            }

            loadFragment(fragment);
        });

        navBottom.show(1, true);

        //FragmentAdapter
        navBottom.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "Bạn chọn " + getFragmentName(item.getId()), Toast.LENGTH_SHORT).show();
            }
        });

        navBottom.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "Bạn chọn lại " + getFragmentName(item.getId()), Toast.LENGTH_SHORT).show();
            }
        });

        navBottom.setCount(3, "10");
    }

    private void anhXa() {
        navBottom = findViewById(R.id.navBottom);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navHostFragment, fragment, null)
                .commit();
    }

    private String getFragmentName(int id) {
        String fragmentName = "";
        switch (id) {
            case 1:
                fragmentName = "Trang Chủ";
                break;
            case 2:
                fragmentName = "Cho Thuê";
                break;
            case 3:
                fragmentName = "Lịch Sử Thuê";
                break;
            case 4:
                fragmentName = "Tài Khoản";
                break;
        }
        return fragmentName;
    }
}
