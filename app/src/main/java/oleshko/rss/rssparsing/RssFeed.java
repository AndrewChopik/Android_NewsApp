package oleshko.rss.rssparsing;

import java.util.List;

public class RssFeed {
    private List<RssItem> mItems;

    void setItems(List<RssItem> items) {
        mItems = items;
    }

    public List<RssItem> getItems() {
        return mItems;
    }
}
