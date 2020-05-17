package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.ecocyam.R;
import com.example.ecocyam.fragment.AboutApplicationFragment;
import com.example.ecocyam.fragment.AboutUsFragment;
import com.example.ecocyam.fragment.SendEmailFragment;
import com.example.ecocyam.fragment.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public final class AboutUsActivity extends AppCompatActivity {

    /* default */private TabLayout tabLayout;
    /* default */private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //Instanciation A propos
        tabLayout = findViewById(R.id.tabLayoutAboutUs);
        viewPager = findViewById(R.id.viewPagerAboutUs);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),1);
        //ajouts fragments
        Intent intent = getIntent();
        String emailUser = intent.getStringExtra("email");

        Bundle bundle = new Bundle();
        bundle.putString("email",emailUser);

        SendEmailFragment sendEmailFragment = new SendEmailFragment();
        sendEmailFragment.setArguments(bundle);

        adapter.addFragment(new AboutApplicationFragment(),"About EcoCYam");
        adapter.addFragment(new AboutUsFragment(),"About us");
       // adapter.addFragment(new SendEmailFragment(),"Contact us");
        adapter.addFragment(sendEmailFragment,"Contact us");

        //adapter setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //fin instanciation a propos

    }
}
