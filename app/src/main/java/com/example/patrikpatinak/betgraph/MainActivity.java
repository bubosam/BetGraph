package com.example.patrikpatinak.betgraph;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;



public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView imageView,tabBg;
    private CollapsingToolbarLayout collapsingToolbar;
    private TabPagerAdapter tabPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton fab;
    ViewPager pager;
    TabLayout tabLayout;
    private TextView moc;
    private Context context;
    public static final String MY_PREFS_NAME = "MyPrefsFile";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context= getApplicationContext();



        imageView= (ImageView) findViewById(R.id.backdrop);
        tabBg= (ImageView) findViewById(R.id.tabBg);
        collapsingToolbar=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        moc=(TextView) findViewById(R.id.moc);


        setToolbar();
        setImage();

        mViewPager= (ViewPager) findViewById(R.id.viewpager);
        mTabLayout= (TabLayout) findViewById(R.id.tab_layout);
        tabPagerAdapter=new TabPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(tabPagerAdapter);
        mTabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    new TicketsFragment();
                    Glide.with(MainActivity.this).load(R.drawable.nkdroid_splash).into(imageView);
                }

                else if (position==1){
                    new HazzardFragment();

                }
                else if (position==2){

                    new GraphsFragment();

                }

                imageView.invalidate();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.FAB);


        pager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout= (TabLayout) findViewById(R.id.tab_layout);


        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    fab.show();
                }
                if (tab.getPosition() == 1) {
                    fab.hide();
                }
                if(tab.getPosition() == 2){
                    fab.hide();
                }
                pager.setCurrentItem(tab.getPosition());
            }

          @Override
            public void onTabUnselected(TabLayout.Tab tab) {
              fab.hide(new FloatingActionButton.OnVisibilityChangedListener() { // Hide FAB
                  @Override
                  public void onHidden(FloatingActionButton fab) {
                     // fab.show(); // After FAB is hidden show it again
                  }
              });
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });










    }

    private void setToolbar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }
    }

    private void setImage() {
        Glide.with(this).load(R.drawable.nkdroid_splash).into(imageView);

    }

    class TabPagerAdapter extends FragmentStatePagerAdapter {

        public TabPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                TicketsFragment ticketsFragment = new TicketsFragment();

                return ticketsFragment;
            }
            if(position == 1){
                HazzardFragment hazzardFragment = new HazzardFragment();

                return  hazzardFragment;
            }
            if(position == 2){

                GraphsFragment graphsFragment = new GraphsFragment();

                return  graphsFragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0) return "Tickets" ;
            if(position == 1) return "Hazzard";
            if(position == 2) return "Graphs";

            return null;
        }
    }
}
