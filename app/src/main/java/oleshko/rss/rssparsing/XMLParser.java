package oleshko.rss.rssparsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class XMLParser extends DefaultHandler {
    private static final String sEmptyString = "";
    private static final String sItem = "item";
    private static final String sTitle = "title";
    private static final String sMedia = "media";
    private static final String sDescription = "description";
    private static final String sLink = "link";
    private static final String sAtomLink = "atom:link";
    private static final String sUrl = "url";
    private static final String sImage = "image";
    private static final String sPublishDate = "pubdate";
    private static final String sContent = "encoded";
    private static final String sCreator = "creator";
    private static final String sLanguage = "language";

    private boolean mElementOn = false;
    private boolean mParsingTitle = false;
    private boolean mParsingDescription = false;
    private boolean mParsingLink = false;
    private boolean mParsingContent = false;
    private boolean mParsingCreator = false;
    private boolean mParsingLanguage = false;

    private String mElementValue = null;
    private String mTitle = sEmptyString;
    private String mLink;
    private String mImage;
    private String mDate;
    private String mDescription;
    private String mContent;
    private String mCreator;
    private String mLanguage;
    private RssItem mRssItem;
    private final ArrayList<RssItem> mRssItems;

    XMLParser() {
        super();
        mRssItems = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        mElementOn = true;
        switch (localName.toLowerCase()) {
            case sItem:
                mRssItem = new RssItem();
                break;
            case sTitle:
                if (!qName.contains(sMedia)) {
                    mParsingTitle = true;
                    mTitle = sEmptyString;
                }
                break;
            case sDescription:
                mParsingDescription = true;
                mDescription = sEmptyString;
                break;
            case sLink:
                if (!qName.equals(sAtomLink)) {
                    mParsingLink = true;
                    mLink = sEmptyString;
                }
                break;
            case sContent:
                mParsingContent = true;
                mContent = sEmptyString;
                break;
            case sCreator:
                mParsingCreator = true;
                mCreator = sEmptyString;
                break;
            case sLanguage:
                mParsingLanguage = true;
                mLanguage = sEmptyString;
        }
        if (attributes != null) {
            String url = attributes.getValue(sUrl);
            if (url != null && !url.isEmpty()) {
                mImage = url;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        mElementOn = false;
        if (mRssItem != null) {
            switch (localName.toLowerCase()) {
                case sItem:
                    mRssItem = new RssItem();
                    mRssItem.setTitle(mTitle.trim());
                    mRssItem.setLink(mLink);
                    try {
                        mRssItem.setPublishDate(getPublishDate(mDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    mRssItem.setDescription(mDescription);
                    mRssItem.setContent(getContent(mContent));
                    mRssItem.setCreator(mCreator);
                    mRssItem.setLanguage(getLanguage(mLanguage));
                    mRssItem.setImage(getImageSourceFromDescription(mContent));
                    mRssItems.add(mRssItem);
                    mLink = sEmptyString;
                    mImage = null;
                    mDate = sEmptyString;
                    break;
                case sTitle:
                    if (!qName.contains(sMedia)) {
                        mParsingTitle = false;
                        mElementValue = sEmptyString;
                        mTitle = removeNewLine(mTitle);
                    }
                    break;
                case sLink:
                    if (!mElementValue.isEmpty()) {
                        mParsingLink = false;
                        mElementValue = sEmptyString;
                        mLink = removeNewLine(mLink);
                    }
                    break;
                case sImage:
                case sUrl:
                    if (mElementValue != null && !mElementValue.isEmpty()) {
                        mImage = mElementValue;
                    }
                    break;
                case sPublishDate:
                    mDate = mElementValue;
                    break;
                case sDescription:
                    mParsingDescription = false;
                    mElementValue = sEmptyString;
                    break;
                case sContent:
                    if (!qName.contains(sContent)) {
                        mParsingContent = false;
                        mElementValue = sEmptyString;
                    }
                case sCreator:
                    mParsingCreator = false;
                    mElementValue = sEmptyString;
                case sLanguage:
                    mParsingLanguage = false;
                    mElementValue = sEmptyString;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String buff = new String(ch, start, length);
        if (mElementOn) {
            if (buff.length() > 2) {
                mElementValue = buff;
                mElementOn = false;
            }
        }
        if (mParsingTitle) {
            mTitle = mTitle + buff;
        }
        if (mParsingDescription) {
            mDescription = mDescription + buff;
        }
        if (mParsingLink) {
            mLink = mLink + buff;
        }
        if (mParsingContent) {
            mContent = mContent + buff;
        }
        if (mParsingCreator) {
            mCreator = mCreator + buff;
        }
        if (mParsingLanguage) {
            mLanguage = mLanguage + buff;
        }
    }


    private String getImageSourceFromDescription(String content) {
        if (content.contains("img") && content.contains("src=\"")) {
            Document doc = Jsoup.parse(content);
            Element element = doc.select("img").first();
            if (element != null) {
                return element.attr("src");
            }
        }
        return content;
    }

    private String getLanguage(String language) {
        String[] parts = language.split("\n");
        String lng = parts[0];
        return lng;
    }

    private String getPublishDate(String date) throws ParseException {
        String[] parts = date.split(",");
        String normDate = parts[1];
        String[] s = normDate.split(" ");
        String a = s[1] + " " + s[2] + " " + s[3];
        SimpleDateFormat parser = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        Date str = parser.parse(a);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
        String formattedDate = formatter.format(str);
        return formattedDate;
    }

    private String getContent(String content) {
        String[] parts = content.split("\n", 2);
        String part1 = parts[1];
        String[] parts1 = part1.split("\n", 2);
        String part2 = parts1[1];
        return part2;
    }

    private String removeNewLine(String s) {
        if (s == null) {
            return sEmptyString;
        }
        return s.replace("\n", "");
    }

    ArrayList<RssItem> getItems() {
        return mRssItems;
    }
}
