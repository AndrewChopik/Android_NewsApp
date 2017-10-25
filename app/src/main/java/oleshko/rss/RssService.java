package oleshko.rss;

import oleshko.rss.rssparsing.RssFeed;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Oleg Savik on 20.06.2017.
 */

public interface RssService {

    @GET("/feed")
    Call<RssFeed> getRss();
}