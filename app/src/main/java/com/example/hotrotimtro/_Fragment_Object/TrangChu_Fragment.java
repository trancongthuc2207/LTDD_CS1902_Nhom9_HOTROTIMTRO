package com.example.hotrotimtro._Fragment_Object;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Pojo.DanhSachNhaTro;
import com.example.hotrotimtro._Pojo.KhachHang;
import com.example.hotrotimtro._Pojo.NhaTro;
import com.example.hotrotimtro._Service.Service;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrangChu_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrangChu_Fragment extends Fragment {

    //
    private Button btn_Them;

    //
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    //


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrangChu_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrangChu_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrangChu_Fragment newInstance(String param1, String param2) {
        TrangChu_Fragment fragment = new TrangChu_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu_,
                container, false);
        btn_Them = (Button) view.findViewById(R.id.btn_them);

        //
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //

        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_themOnCLick();
                Log.e("Email", user.getEmail());
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    //////////// BUTTON CLICK
    public void btn_themOnCLick()
    {

        NhaTro nt = new NhaTro("1","Gac",20,1,true,20000,"tayninh");
        DanhSachNhaTro ds = new DanhSachNhaTro();
        ds.addNhaTro(nt);
        KhachHang kh = new KhachHang("Thuc","","","1234567","","",true,ds);
        Service sv = new Service();

        sv.dangKyAccount(kh);
    }
}