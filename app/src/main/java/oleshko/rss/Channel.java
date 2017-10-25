package oleshko.rss;

import java.util.List;

/**
 * Created by Oleg Savik on 20.06.2017.
 */

public class Channel
{
    private String title;

    private String description;

    private String link;

    private String lastBuildDate;

    private List<Item> item;

    private String generator;

    private String language;

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

    public String getLastBuildDate ()
    {
        return lastBuildDate;
    }

    public void setLastBuildDate (String lastBuildDate)
    {
        this.lastBuildDate = lastBuildDate;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public String getGenerator ()
    {
        return generator;
    }

    public void setGenerator (String generator)
    {
        this.generator = generator;
    }

    public String getLanguage ()
    {
        return language;
    }

    public void setLanguage (String language)
    {
        this.language = language;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [title = "+title+", description = "+description+", link = "+link+", lastBuildDate = "+lastBuildDate+", item = "+item+", generator = "+generator+", language = "+language+"]";
    }

}


