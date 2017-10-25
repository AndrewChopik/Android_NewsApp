package oleshko.rss;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import oleshko.rss.adapters.ViewPagerContentAdapter;
import oleshko.rss.managers.RssNewsManager;
import oleshko.rss.rssparsing.RssItem;

import static oleshko.rss.fragments.RssNewsFragment.EXTRA_MAIN_PAGE_NUMBER;
import static oleshko.rss.fragments.RssNewsFragment.EXTRA_POSITION;

public class AboutActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ImageView mImageContent;
    private Toolbar mToolbar;
    private ArrayList<RssItem> arrayList = null;
    private int position;
    private int pageNumber;
    private String message;


    public static void start(Context context, int position, int pageNumber) {

        Intent starter = new Intent(context, AboutActivity.class);
        starter.putExtra(EXTRA_MAIN_PAGE_NUMBER, pageNumber);
        starter.putExtra(EXTRA_POSITION, position);
        context.startActivity(starter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutt);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(" ");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mImageContent = (ImageView) findViewById(R.id.image_news_content);

        position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        pageNumber = getIntent().getIntExtra(EXTRA_MAIN_PAGE_NUMBER, 0);

        arrayList = new ArrayList<>();
        switch (pageNumber) {
            case (0):
                arrayList = RssNewsManager.getInstance().getTodayList();
                break;
            case (1):
                arrayList = RssNewsManager.getInstance().getYesterdayList();
                break;
            case (2):
                arrayList = RssNewsManager.getInstance().getOtherList();
                break;
        }

        Glide
                .with(this)
                .load(arrayList.get(position).getImage())
                .centerCrop()
                .into(mImageContent);

        message = arrayList.get(position).getLink();

        ViewPagerContentAdapter contentAdapter = new ViewPagerContentAdapter(getSupportFragmentManager(), arrayList, pageNumber);
        mViewPager.setAdapter(contentAdapter);
        mViewPager.setCurrentItem(position);

        final SimpleTarget target = new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                mImageContent.setImageBitmap(bitmap);
            }
        };


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Glide
                        .with(AboutActivity.this)
                        .load(arrayList.get(position).getImage())
                        .asBitmap()
                        .into(target);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.share, menu);

        return true;
    }

    public void onShareClick(MenuItem menuItem) {
        RssNewsManager.getInstance().init(new RssNewsManager.LoadRssNewsListener() {
            @Override
            public void start() {

            }

            @Override
            public void success() {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, message);

                String chosenTitle = getString(R.string.chooser_title);
                Intent chosenIntent = Intent.createChooser(intent, chosenTitle);

                startActivity(chosenIntent);
            }

            @Override
            public void failure() {

            }
        });
    }
}


