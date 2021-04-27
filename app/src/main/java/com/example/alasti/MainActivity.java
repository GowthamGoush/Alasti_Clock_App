package com.example.alasti;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.alasti.Adapters.SectionsPagerAdapter;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(sectionsPagerAdapter);

        bottomNavigation = findViewById(R.id.bottomNavigationBar);

        bottomNavigation.add(new MeowBottomNavigation.Model(R.string.Alarm_ID, R.drawable.ic_alarm));
        bottomNavigation.add(new MeowBottomNavigation.Model(R.string.Timer_ID, R.drawable.ic_timer));
        bottomNavigation.add(new MeowBottomNavigation.Model(R.string.Stopwatch_ID, R.drawable.ic_stop_watch));

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(MainActivity.this, "clicked item : " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(MainActivity.this, "clicked item : " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                switch (item.getId()) {
                    case R.string.Alarm_ID:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.string.Timer_ID:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.string.Stopwatch_ID:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });

        bottomNavigation.show(R.string.Alarm_ID, false);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        bottomNavigation.show(R.string.Alarm_ID, true);
                        break;
                    case 1:
                        bottomNavigation.show(R.string.Timer_ID, true);
                        break;
                    case 2:
                        bottomNavigation.show(R.string.Stopwatch_ID, true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}