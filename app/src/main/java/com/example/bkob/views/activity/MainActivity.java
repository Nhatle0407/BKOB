package com.example.bkob.views.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bkob.R;
import com.example.bkob.databinding.ActivityMainBinding;
import com.example.bkob.views.fragments.AccountFragment;
import com.example.bkob.views.fragments.AddBookFragment;
import com.example.bkob.views.fragments.HomeFragment;
import com.example.bkob.views.fragments.NotificationFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        setUpNavBar();
    }

    private void setUpNavBar() {
        binding.bottomNavigation.setItemSelected(R.id.home, true);
        replaceFragment(new HomeFragment());
        binding.bottomNavigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.add_book:
                        fragment = new AddBookFragment();
                        break;
                    case R.id.notification:
                        fragment = new NotificationFragment();
                        break;
                    case R.id.account:
                        fragment = new AccountFragment();
                        break;
                }
                if(fragment != null){
                    replaceFragment(fragment);
                }
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFragments, fragment)
                .addToBackStack(null)
                .commit();
    }
}