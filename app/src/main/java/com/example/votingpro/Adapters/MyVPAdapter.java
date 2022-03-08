package com.example.votingpro.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyVPAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragList = new ArrayList<>();
    private ArrayList<String> caNameList = new ArrayList<>();


    public MyVPAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    public void setFragPage(Fragment fragment, String categoryTitle)
    {
        fragList.add(fragment);
        caNameList.add(categoryTitle);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return caNameList.get(position);
    }
}
