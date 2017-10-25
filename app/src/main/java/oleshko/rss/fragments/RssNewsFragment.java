package oleshko.rss.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import oleshko.rss.R;
import oleshko.rss.adapters.RssNewsRecyclerViewAdapter;
import oleshko.rss.managers.RssNewsManager;
import oleshko.rss.rssparsing.RssItem;

public class RssNewsFragment extends Fragment {
    public static final String EXTRA_POSITION = "item_position";
    public static final String EXTRA_MAIN_PAGE_NUMBER = "page_number";

    private RecyclerView mRecyclerView;
    private RssNewsRecyclerViewAdapter mRssNewsRecyclerViewAdapter;

    private static final int TODAY_PAGE_TYPE = 0;
    private static final int YESTERDAY_PAGE_TYPE = 1;
    private static final int TOTAL_PAGE_TYPE = 2;

    private ArrayList<RssItem> mArrayList;
    private int mPageNumber;

    public static RssNewsFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_POSITION, position);
        RssNewsFragment fragment = new RssNewsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public RssNewsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPageNumber = getArguments().getInt(EXTRA_POSITION);
    }

    public int getmPageNumber() {
        return mPageNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        switch (mPageNumber) {
            case TODAY_PAGE_TYPE:
                mArrayList = RssNewsManager.getInstance().getTodayList();
                break;
            case YESTERDAY_PAGE_TYPE:
                mArrayList = RssNewsManager.getInstance().getYesterdayList();
                break;
            case TOTAL_PAGE_TYPE:
                mArrayList = RssNewsManager.getInstance().getOtherList();
                break;
        }
        mRssNewsRecyclerViewAdapter = new RssNewsRecyclerViewAdapter(mArrayList, getContext(), mPageNumber);
        mRecyclerView.setAdapter(mRssNewsRecyclerViewAdapter);

        return view;
    }
}
