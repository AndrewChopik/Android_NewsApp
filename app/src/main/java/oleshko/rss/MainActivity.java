package oleshko.rss;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewSwitcher;


import oleshko.rss.adapters.ViewPagerAdapter;
import oleshko.rss.managers.RssNewsManager;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ViewSwitcher mViewSwitcher;
    private Toolbar mToolbar;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewSwitcher = (ViewSwitcher) findViewById(R.id.switcher);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_activity_main);

        mViewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setOffscreenPageLimit(3);
        RssNewsManager.getInstance()
                .init(new RssNewsManager.LoadRssNewsListener() {
                          @Override
                          public void start() {
                              startProgress();
                          }

                          @Override
                          public void success() {
                              mViewPager.setAdapter(viewPagerAdapter);
                              stopProgress();
                          }

                          @Override
                          public void failure() {
                              stopProgress();
                          }
                      }
                );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.refresh, menu);

        return true;
    }

    public void onRefreshClick(MenuItem menuItem) {

        RssNewsManager.getInstance().init(new RssNewsManager.LoadRssNewsListener() {
            @Override
            public void start() {

                startProgress();
            }

            @Override
            public void success() {
                viewPagerAdapter.notifyDataSetChanged();

                stopProgress();
            }

            @Override
            public void failure() {

                startProgress();
            }
        });
    }

    private void startProgress() {
        mViewSwitcher.setDisplayedChild(0);
    }

    private void stopProgress() {
        mViewSwitcher.setDisplayedChild(1);
    }

}