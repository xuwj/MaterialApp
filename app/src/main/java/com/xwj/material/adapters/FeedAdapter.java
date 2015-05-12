package com.xwj.material.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.xwj.material.R;
import com.xwj.material.utils.PhoneUtils;
import com.xwj.material.views.SquaredImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 2015/5/12.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.CellFeedViewHolder>  implements View.OnClickListener{
    private static final int ANIMATED_ITEMS_COUNT = 2;
    private Context mContext;
    private int mLastAnimatedPosition = -1;
    private int mItemsCount = 0;

    private OnFeedItemClickListener onFeedItemClickListener;

    public FeedAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public CellFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_feed, parent, false);
        return new CellFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CellFeedViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        if (position % 2 == 0) {
            holder.feedCenterSiv.setImageResource(R.drawable.img_feed_center_1);
            holder.feedBottomIv.setImageResource(R.drawable.img_feed_bottom_1);
        } else {
            holder.feedCenterSiv.setImageResource(R.drawable.img_feed_center_2);
            holder.feedBottomIv.setImageResource(R.drawable.img_feed_bottom_2);
        }
        holder.feedBottomIv.setOnClickListener(this);
        holder.feedBottomIv.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mItemsCount;
    }


    public void setUpdateItems() {
        mItemsCount = 10;
        notifyDataSetChanged();
    }

    /**
     * 进入动画
     */
    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }
        if (position > mLastAnimatedPosition - 1) {
            mLastAnimatedPosition = position;
            // ViewHelper.setTranslationY(view, PhoneUtils.getScreenSize(mContext)[1]);
            view.setTranslationY(PhoneUtils.getScreenSize(mContext)[1]);
            view.animate().translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_feed_bottom) {
            if (onFeedItemClickListener != null) {
                onFeedItemClickListener.onCommentsClick(v, (Integer) v.getTag());
            }
        }
    }

    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
        this.onFeedItemClickListener = onFeedItemClickListener;
    }

    public static class CellFeedViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_feed_center)
        SquaredImageView feedCenterSiv;
        @InjectView(R.id.iv_feed_bottom)
        ImageView feedBottomIv;

        public CellFeedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnFeedItemClickListener {
        public void onCommentsClick(View v, int position);
    }
}
