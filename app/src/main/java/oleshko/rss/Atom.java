package oleshko.rss;

/**
 * Created by Oleg Savik on 20.06.2017.
 */

public class Atom
        {
private String rel;

private String type;

private String href;

public String getRel ()
        {
        return rel;
        }

public void setRel (String rel)
        {
        this.rel = rel;
        }

public String getType ()
        {
        return type;
        }

public void setType (String type)
        {
        this.type = type;
        }

public String getHref ()
        {
        return href;
        }

public void setHref (String href)
        {
        this.href = href;
        }

@Override
public String toString()
        {
        return "ClassPojo [rel = "+rel+", type = "+type+", href = "+href+"]";
        }
        }

