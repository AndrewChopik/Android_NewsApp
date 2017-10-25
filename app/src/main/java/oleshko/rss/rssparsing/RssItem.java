package oleshko.rss.rssparsing;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Model for Rss Item
 */
public class RssItem implements Serializable {

    private String mTitle;
    private String mLink;
    private String mImage;
    private String mPublishDate;
    private String mDescription;
    private String mContent;
    private String mCreator;
    private String mLanguage;

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        this.mLanguage = language;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title.replace("&#39;", "'").replace("&#039;", "'");
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        this.mLink = link.trim();
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    public String getPublishDate() {
        return mPublishDate;
    }

    public void setPublishDate(String publishDate) {
        this.mPublishDate = publishDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getCreator() {
        return mCreator;
    }

    public void setCreator(String creator) {
        this.mCreator = creator;
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (mTitle != null) {
            builder.append(mTitle).append("\n");
        }
        if (mLink != null) {
            builder.append(mLink).append("\n");
        }
        if (mImage != null) {
            builder.append(mImage).append("\n");
        }
        if (mDescription != null) {
            builder.append(mDescription);
        }
        if (mContent != null) {
            builder.append(mContent).append("\n");
        }
        if (mCreator != null) {
            builder.append(mCreator).append("\n");
        }
        return builder.toString();
    }
}
