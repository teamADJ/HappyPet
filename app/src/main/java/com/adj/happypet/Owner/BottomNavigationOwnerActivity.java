package com.adj.happypet.Owner;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.adj.happypet.HomeFragment;
import com.adj.happypet.ProfileFragment;
import com.adj.happypet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationOwnerActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewOwner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_owner);
        bottomNavigationViewOwner = findViewById(R.id.bottomNavigationOwner);
        bottomNavigationViewOwner.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeOwnerFragment.newInstance("", ""));
    }

    public void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_owner, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.navigation_home_owner:
                            openFragment(HomeOwnerFragment.newInstance("", ""));
                            return true;
                        case R.id.inbox_owner:
                            openFragment(InboxOwnerFragment.newInstance("", ""));
                            return true;
                        case R.id.profile_owner:
                            openFragment(ProfileFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };

}
