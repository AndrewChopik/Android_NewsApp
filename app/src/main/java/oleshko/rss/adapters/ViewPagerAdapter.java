package oleshko.rss.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import oleshko.rss.fragments.RssNewsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final static int listSize = 3;
    private static final int CONST_TODAY = 0;
    public static final int CONST_YESTERDAY = 1;
    public static final int CONST_TOTAL = 2;

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return listSize;
    }

    @Override
    public Fragment getItem(int position) {
        return RssNewsFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case CONST_TODAY:
                return "Сегодня";
            case CONST_YESTERDAY:
                return "Вчера";
            case CONST_TOTAL:
                return "На этой неделе";
            default:
                return super.getPageTitle(position);
        }
    }
}

