package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.adj.happypet.Owner.RegisterOwnerFragment;

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
//                RegisterCoba registerCoba = new RegisterCoba();
//                return  registerCoba;

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
