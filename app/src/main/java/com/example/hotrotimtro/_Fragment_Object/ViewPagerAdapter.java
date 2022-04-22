package com.example.hotrotimtro._Fragment_Object;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new TrangChu_Fragment();
            case 1:
                return new ChoThue_Fragment();
            case 2:
                return new LichSuThue_Fragment();
            case 3:
                return new TaiKhoan_Fragment();
            default:
                return new TrangChu_Fragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Trang Chủ";
                break;
            case 1:
                title = "Cho Thuê";
                break;
            case 2:
                title = "Lịch sử thuê";
                break;
            case 3:
                title = "Tài khoản";
                break;
        }
        return title;
    }
}
