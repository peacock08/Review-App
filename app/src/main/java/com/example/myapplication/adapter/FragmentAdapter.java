package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.fragment.FragmentInfo;
import com.example.myapplication.fragment.FragmentList;
import com.example.myapplication.fragment.FragmentSearch;

public class FragmentAdapter extends FragmentPagerAdapter {
    int pageNumber;
    public FragmentAdapter(@NonNull FragmentManager fm, int pageNumber) {
        super(fm, pageNumber);
        this.pageNumber = pageNumber;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentList();
            case 1:
                return new FragmentInfo();
            case 2:
                return new FragmentSearch();
        }
        return null;
    }

    @Override
    public int getCount() {
        return pageNumber;
    }
}
