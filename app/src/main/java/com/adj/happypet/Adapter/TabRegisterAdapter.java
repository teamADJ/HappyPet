package com.adj.happypet.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.adj.happypet.Owner.RegisterOwnerFragment;
import com.adj.happypet.RegisterUserFragment;

public class TabRegisterAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabsRegister;
    public TabRegisterAdapter(@NonNull FragmentManager fm, int NoofTabsRegister) {
        super(fm);
        this.mNumOfTabsRegister = NoofTabsRegister;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                RegisterUserFragment registerUserFragment = new RegisterUserFragment();
                return registerUserFragment;


            case 1:
                RegisterOwnerFragment registerOwnerFragment = new RegisterOwnerFragment();
                return registerOwnerFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabsRegister;
    }
}
