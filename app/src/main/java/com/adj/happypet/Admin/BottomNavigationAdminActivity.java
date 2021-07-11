package com.adj.happypet.Admin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.adj.happypet.Owner.HomeOwnerFragment;
import com.adj.happypet.Owner.InboxOwnerFragment;
import com.adj.happypet.ProfileFragment;
import com.adj.happypet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationAdminActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationViewAdmin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_admin);
        bottomNavigationViewAdmin = findViewById(R.id.bottomNavigationAdmin);
        bottomNavigationViewAdmin.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeAdminFragment.newInstance("", ""));
    }

    public void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_admin, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.navigation_home_admin:
                            openFragment(HomeAdminFragment.newInstance("", ""));
                            return true;
                        case R.id.inbox_admin:
                            openFragment(InboxAdminFragment.newInstance("", ""));
                            return true;
                        case R.id.profile_admin:
                            openFragment(ProfileAdminFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };

}
