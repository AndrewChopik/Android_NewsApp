package oleshko.rss;

import android.app.Application;
import android.os.Bundle;

import oleshko.rss.managers.RssNewsManager;

/**
 * Created by Oleg Savik on 22.06.2017.
 */

public class App extends Application {

    private static App mInstance;
    public static synchronized App getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

}