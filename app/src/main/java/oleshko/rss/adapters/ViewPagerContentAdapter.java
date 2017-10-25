package oleshko.rss.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import oleshko.rss.fragments.RssNewsDescriptionFragment;
import oleshko.rss.rssparsing.RssItem;

public class ViewPagerContentAdapter extends FragmentPagerAdapter {
    private ArrayList<RssItem> mRssItemArrayList;
    private int mPageNumber;

    public ViewPagerContentAdapter(FragmentManager fragmentManager, ArrayList<RssItem> arrayList, int pageNumber) {
        super(fragmentManager);

        mRssItemArrayList = arrayList;
        mPageNumber = pageNumber;
    }

    @Override
    public int getCount() {
        return mRssItemArrayList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return RssNewsDescriptionFragment.newInstance(position, mPageNumber);
    }
}

