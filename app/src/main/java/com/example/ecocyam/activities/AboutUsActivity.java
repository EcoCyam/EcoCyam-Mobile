package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.ecocyam.R;
import com.example.ecocyam.fragment.AboutUsFragment;
import com.example.ecocyam.fragment.ExploreFragment;
import com.example.ecocyam.fragment.StoreFragment;
import com.example.ecocyam.fragment.ViewPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class AboutUsActivity extends AppCompatActivity {

    /* default */private TabLayout tabLayout;
    /* default */private AppBarLayout appBarLayout;
    /* default */private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //Instanciation A propos
        tabLayout = findViewById(R.id.tabLayoutAboutUs);
        appBarLayout = findViewById(R.id.appBarAboutUs);
        viewPager = findViewById(R.id.viewPagerAboutUs);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),1);
        //ajouts fragments
        adapter.addFragment(new AboutUsFragment(),"About EcoCYam");
        adapter.addFragment(new ExploreFragment(),"About us");
        adapter.addFragment(new StoreFragment(),"Contact us");
        //adapter setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //fin instanciation a propos
    }


}
