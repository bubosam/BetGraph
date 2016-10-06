package com.example.patrikpatinak.betgraph;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;



public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView imageView;
    private TabPagerAdapter tabPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton fab;


    TextView moc;
    Context context;
    ImageView tabBg;
    CollapsingToolbarLayout collapsingToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context= getApplicationContext();



        imageView= (ImageView) findViewById(R.id.backdrop);
        tabBg= (ImageView) findViewById(R.id.tabBg);
        collapsingToolbar=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        moc=(TextView) findViewById(R.id.moc);
        fab = (FloatingActionButton) findViewById(R.id.FAB);


        setToolbar();
        setImage();

        mViewPager= (ViewPager) findViewById(R.id.viewpager);
        mTabLayout= (TabLayout) findViewById(R.id.tab_layout);


        tabPagerAdapter=new TabPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(tabPagerAdapter);
        mTabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);







        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                mViewPager.setCurrentItem(tab.getPosition());
            }

          @Override
            public void onTabUnselected(TabLayout.Tab tab) {
              fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                  @Override
                  public void onHidden(FloatingActionButton fab) {

                  }
              });
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void setToolbar()
    {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
        }
    }

    private void setImage()
    {
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
