package oleshko.rss.managers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import oleshko.rss.RssService;
import oleshko.rss.rssparsing.RssConverterFactory;
import oleshko.rss.rssparsing.RssFeed;
import oleshko.rss.rssparsing.RssItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Oleg Savik on 21.06.2017.
 */
public class RssNewsManager {
    private static RssNewsManager mInstance;
    public static final String BASE_URL = "https://hi-news.ru/";

    private ArrayList<RssItem> mRssItems;
    private Retrofit retrofit;


    public static synchronized RssNewsManager getInstance() {

        if (mInstance == null) {
            mInstance = new RssNewsManager();
        }

        return mInstance;

    }

    public void init(final LoadRssNewsListener listener) {
        listener.start();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(RssConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        RssService service = retrofit.create(RssService.class);
        service.getRss()
                .enqueue(new Callback<RssFeed>() {

                    @Override
                    public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {

                        mRssItems = new ArrayList<>(response.body().getItems());

                        listener.success();

                    }

                    @Override
                    public void onFailure(Call<RssFeed> call, Throwable t) {
                        listener.failure();
                    }
                });

    }

    public String getTodayDate() {

        Date dateToday = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        String today = simpleDateFormat.format(dateToday).toString();

        return today;
    }

    public String getYesterdayDate() {

        Date todayDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        Long time = todayDate.getTime();

        long anotherDate = -1;
        time = time + (60 * 60 * 24 * 1000 * anotherDate);
        todayDate = new Date(time);
        String yesterday = simpleDateFormat.format(todayDate).toString();

        return yesterday;
    }

    public ArrayList<RssItem> getTodayList() {
        ArrayList<RssItem> todayList = new ArrayList<>();

        String today = getTodayDate();
        for (int i = 0; i < mRssItems.size(); i++) {
            if (today.equals(mRssItems.get(i).getPublishDate())) {
                todayList.add(mRssItems.get(i));
            }
        }

        return todayList;
    }

    public ArrayList<RssItem> getYesterdayList() {
        ArrayList<RssItem> yesterdayList = new ArrayList<>();

        String yesterday = getYesterdayDate();
        for (int i = 0; i < mRssItems.size(); i++) {
            if (yesterday.equals(mRssItems.get(i).getPublishDate())) {
                yesterdayList.add(mRssItems.get(i));
            }
        }

        return yesterdayList;
    }

    public ArrayList<RssItem> getOtherList() {
        ArrayList<RssItem> otherList = new ArrayList<>();

        String today = getTodayDate();
        String yesterday = getYesterdayDate();

        for (int i = 0; i < mRssItems.size(); i++) {
            if (!today.equals(mRssItems.get(i).getPublishDate()) && !yesterday.equals(mRssItems.get(i).getPublishDate())) {
                otherList.add(mRssItems.get(i));
            }
        }

        return otherList;
    }

    public interface LoadRssNewsListener {
        void start();

        void success();

        void failure();
    }
}
