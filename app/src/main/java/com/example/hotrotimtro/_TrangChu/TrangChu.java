package com.example.hotrotimtro._TrangChu;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Fragment_Object.TrangChu_Fragment;
import com.example.hotrotimtro._Fragment_Object.DangTin_Fragment;
import com.example.hotrotimtro._Fragment_Object.MucDaLuu_Fragment;
import com.example.hotrotimtro._Fragment_Object.XemThem_Fragment;
import com.example.hotrotimtro._Login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TrangChu extends AppCompatActivity {

    FirebaseAuth mAuth;
    MeowBottomNavigation navBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        // ADJUST RESIZE :3
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        anhXa();

        // GET USER CURRENT
        mAuth = FirebaseAuth.getInstance();

        // NAV BOTTOM
        navBottom.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        navBottom.add(new MeowBottomNavigation.Model(2, R.drawable.ic_post));
        navBottom.add(new MeowBottomNavigation.Model(3, R.drawable.ic_list));
        navBottom.add(new MeowBottomNavigation.Model(4, R.drawable.ic_more));

        navBottom.setOnShowListener(item -> {
            Fragment fragment;

            if (item.getId() == 1) {
                fragment = new TrangChu_Fragment();
            } else if (item.getId() == 2) {
                fragment = new DangTin_Fragment();
            } else if (item.getId() == 3) {
                fragment = new MucDaLuu_Fragment();
            } else {
                fragment = new XemThem_Fragment();
            }

            loadFragment(fragment);
        });

        navBottom.show(1, true);

        // FragmentAdapter
        navBottom.setOnClickMenuListener(item -> Toast.makeText(getApplicationContext(), "Bạn chọn " + getFragmentName(item.getId()), Toast.LENGTH_SHORT).show());

        navBottom.setOnReselectListener(item -> Toast.makeText(getApplicationContext(), "Bạn chọn lại " + getFragmentName(item.getId()), Toast.LENGTH_SHORT).show());

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
                fragmentName = "Đăng Tin";
                break;
            case 3:
                fragmentName = "Mục Đã Lưu";
                break;
            case 4:
                fragmentName = "Xem Thêm";
                break;
        }
        return fragmentName;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(TrangChu.this, LoginActivity.class));
        }
    }
}