package com.example.goodthingscheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.goodthingscheduler.Categories.CategoriesUtil;


public class FragmentT extends Fragment {

    public FragmentT() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        TextView textView = view.findViewById(R.id.titleTV);
        textView.setText(CategoriesUtil.categorySelected);
        return view;
    }
}

