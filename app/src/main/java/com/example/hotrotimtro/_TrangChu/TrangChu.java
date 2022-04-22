package com.example.hotrotimtro._TrangChu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Fragment_Object.ViewPagerAdapter;
import com.example.hotrotimtro._Pojo.DanhSachNhaTro;
import com.example.hotrotimtro._Pojo.KhachHang;
import com.example.hotrotimtro._Pojo.NhaTro;
import com.example.hotrotimtro._Service.Service;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TrangChu extends AppCompatActivity {

    private BottomNavigationView bot_nav;
    private ViewPager  viewPager;

    //test


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);

        anhXa();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        bot_nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_trangchu:
                    Toast.makeText(this, "ĐÂY LÀ TRANG CHỦ", Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.action_chothue:
                    Toast.makeText(this, "ĐÂY LÀ TRANG CHO THUÊ", Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.action_lichsuthue:
                    Toast.makeText(this, "ĐÂY LÀ LỊCH SỬ THUÊ", Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.action_taikhoan:
                    Toast.makeText(this, "ĐÂY LÀ TÀI KHOẢN", Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(3);
                    break;
            }
            return true;
        });



    }



    public void anhXa(){
        bot_nav = findViewById(R.id._taskbar);
        viewPager = findViewById(R.id.view_pager);
    }
}
