package com.addon.taxi360;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.addon.taxi360.fragments.NormalBooking;
import com.addon.taxi360.utils.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NormalBooking.OnFragmentInteractionListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;


    @BindView(R.id.viewpager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupViewPager(viewPager);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_normal:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_quick:
                        break;
                }

                return true;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(NormalBooking.newInstance());
        viewPager.setAdapter(pagerAdapter);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
