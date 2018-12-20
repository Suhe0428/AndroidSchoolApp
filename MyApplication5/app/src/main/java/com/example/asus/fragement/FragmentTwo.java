package com.example.asus.fragement;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asus.community.Community;
import com.example.asus.community.CommunityAdapter;
import com.example.asus.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentTwo extends Fragment {
    public List<Community> communityList;
    public ListView listView_community;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_two,null);
        communityList=new ArrayList<>();
        listView_community=view.findViewById(R.id.listView_community);
        initCommunity();
        listView_community.setAdapter(new CommunityAdapter(getActivity(),communityList));
        return view;
    }

    public void initCommunity(){
        Community community=new Community("华为",R.drawable.hw,"5G!",R.drawable.net,false,0);
        communityList.add(community);
        Community community1=new Community("华为",R.drawable.hw,"5G!",R.drawable.net,false,0);
        communityList.add(community1);
    }
}