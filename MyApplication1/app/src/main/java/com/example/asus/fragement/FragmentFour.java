package com.example.asus.fragement;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.asus.login.LoginActivity;
import com.example.asus.myapplication.MainActivity;
import com.example.asus.myapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentFour extends Fragment {
    private Button button_exit;
    private CircleImageView circleImageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_four,container,false);
        /*1.头像设置*/
        circleImageView=view.findViewById(R.id.imageView_head);


        /*2.功能*/



        /*3.用户注销*/
        button_exit=view.findViewById(R.id.button_exit);
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                sharedPreferences.edit()
                        .putString("islogin","no")
                        .apply();
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
