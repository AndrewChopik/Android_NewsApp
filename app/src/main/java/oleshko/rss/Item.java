package oleshko.rss;

/**
 * Created by Oleg Savik on 20.06.2017.
 */

public class Item
{
    private Guid guid;

    private String pubDate;

    private String title;

    private String[] category;

    private String description;

    private String link;

    private String comments;

    private String creator;



    public void setGuid (Guid guid)
    {
        this.guid = guid;
    }

    public String getPubDate ()
    {
        return pubDate;
    }

    public void setPubDate (String pubDate)
    {
        this.pubDate = pubDate;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String[] getCategory ()
    {
        return category;
    }

    public void setCategory (String[] category)
    {
        this.category = category;
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

    public String getComments ()
    {
        return comments;
    }

    public void setComments (String comments)
    {
        this.comments = comments;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [guid = "+guid+", pubDate = "+pubDate+", title = "+title+", category = "+category+", description = "+description+", link = "+link+", comments = "+comments+"]";
    }
}

