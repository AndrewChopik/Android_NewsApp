package oleshko.rss;

/**
 * Created by Oleg Savik on 20.06.2017.
 */

public class MyPojo
{
    private Rss rss;

    public Rss getRss ()
    {
        return rss;
    }

    public void setRss (Rss rss)
    {
        this.rss = rss;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rss = "+rss+"]";
    }
}