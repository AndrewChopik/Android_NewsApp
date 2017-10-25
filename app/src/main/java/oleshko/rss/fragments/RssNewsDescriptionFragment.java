package oleshko.rss.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.ArrayList;

import oleshko.rss.R;
import oleshko.rss.managers.RssNewsManager;
import oleshko.rss.rssparsing.RssItem;

import static oleshko.rss.fragments.RssNewsFragment.EXTRA_MAIN_PAGE_NUMBER;

public class RssNewsDescriptionFragment extends Fragment {

    public static final String EXTRA_POSITION = "item_position";

    private static final int TODAY_LIST_POSITION=0;
    private static final int YESTERDAY_LIST_POSITION=1;
    private static final int OTHER_LIST_POSITION=2;

    private TextView mTitleContent;
    private TextView mDateContent;
    private WebView mContent;
    private ArrayList<RssItem> arrayList;
    private int mPageNumber;
    private int mMainPageNumber;


    public static RssNewsDescriptionFragment newInstance(int position, int pageNumber) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_POSITION, position);
        args.putInt(EXTRA_MAIN_PAGE_NUMBER, pageNumber);
        RssNewsDescriptionFragment fragment = new RssNewsDescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RssNewsDescriptionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPageNumber = getArguments().getInt(EXTRA_POSITION);
        mMainPageNumber = getArguments().getInt(EXTRA_MAIN_PAGE_NUMBER);

        switch (mMainPageNumber) {
            case (TODAY_LIST_POSITION):
                arrayList = RssNewsManager.getInstance().getTodayList();
                break;
            case (YESTERDAY_LIST_POSITION):
                arrayList = RssNewsManager.getInstance().getYesterdayList();
                break;
            case (OTHER_LIST_POSITION):
                arrayList = RssNewsManager.getInstance().getOtherList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rss_news_description, null);

        mDateContent = (TextView) view.findViewById(R.id.pubdate_news_content);
        mDateContent.setText(arrayList.get(mPageNumber).getPublishDate());

        mTitleContent = (TextView) view.findViewById(R.id.title_content);
        mTitleContent.setText(arrayList.get(mPageNumber).getTitle());

        mContent = (WebView) view.findViewById(R.id.content_news_content);
        mContent.setWebViewClient(new WebViewClient());
        mContent.setWebChromeClient(new WebChromeClient());
        mContent.getSettings().setJavaScriptEnabled(true);
        mContent.loadData("<style>img{display: inline;height: auto;max-width: 100%;}</style>" + "<style>iframe{display: inline;height: auto;max-width: 100%;}</style>" + arrayList.get(mPageNumber).getContent(), "text/html; charset=utf-8", "UTF-8");
        mContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        return view;
    }
}
