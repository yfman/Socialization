package com.snnu.yefan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snnu.yefan.socialization.R;

/**
 * Created by zero on 2016/7/9.
 */
public class MainFragment extends Fragment {

    private static MainFragment fm;

    public static Fragment instance(){

        if(fm==null){
            fm = new MainFragment();
        }

        return fm;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main,container,false);

        return view;
    }


}
