package oleshko.rss;

/**
 * Created by Oleg Savik on 20.06.2017.
 */
import oleshko.rss.Channel;

    public class Rss
    {
        private Channel channel;

        private String version;

        public Channel getChannel ()
        {
            return channel;
        }

        public void setVersion (String version)
        {
            this.version = version;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [channel = "+channel+", version = "+version+"]";
        }
    }