package oleshko.rss.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import oleshko.rss.AboutActivity;
import oleshko.rss.R;
import oleshko.rss.rssparsing.RssItem;

/**
 * Created by Oleg Savik on 22.06.2017.
 */
public class RssNewsRecyclerViewAdapter extends RecyclerView.Adapter<RssNewsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<RssItem> mList = new ArrayList<>();
    public Context mContext;
    public int mPageNumber;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;
        public TextView mDescriptionTextView;
        public TextView mPubDateTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);

            mTitleTextView = (TextView) v.findViewById(R.id.title_news);
            mDescriptionTextView = (TextView) v.findViewById(R.id.description_news);
            mPubDateTextView = (TextView) v.findViewById(R.id.pubdate_news);
            mImageView = (ImageView) v.findViewById(R.id.image_news);
        }
    }

    public RssNewsRecyclerViewAdapter(ArrayList<RssItem> list, Context context, int pageNumber) {
        mContext = context;
        mList = list;
        mPageNumber = pageNumber;
    }

    @Override
    public RssNewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RssNewsRecyclerViewAdapter.ViewHolder holder, final int position) {
        if (holder != null) {
            final RssItem item = mList.get(position);
            holder.mTitleTextView.setText(item.getTitle());
            holder.mDescriptionTextView.setText(Html.fromHtml(item.getDescription()));
            holder.mPubDateTextView.setText(item.getPublishDate());

            Glide
                    .with(holder.itemView.getContext())
                    .load(item.getImage())
                    .centerCrop()
                    .into(holder.mImageView);
            holder.itemView.setOnClickListener(
                        new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AboutActivity.start(mContext, position, mPageNumber);
                        }
                    }
            );
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
}

