package com.xwj.material.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xwj.material.R;
import com.xwj.material.utils.picassoutil.RoundedTransformation;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 评论适配器
 * Created by user on 2015/5/12.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    private Context mContext;
    private int mItemsCount = 0;
    private int mLastAnimatedPosition = -1;
    private int mAvatarSize;

    private boolean mAnimationsLocked = false;
    private boolean mDelayEnterAnimation = true;

    public CommentsAdapter(Context context) {
        this.mContext = context;
        mAvatarSize = context.getResources().getDimensionPixelSize(R.dimen.btn_fab_size);
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        switch (position % 3) {
            case 0:
                holder.tvComment.setText("Are you ok!");
                break;
            case 1:
                holder.tvComment.setText("I'm fine!");
                break;
            case 2:
                holder.tvComment.setText("Thank you!");
                break;
        }
        Picasso.with(mContext)
                .load(R.drawable.ic_launcher)
                .centerCrop()
                .resize(mAvatarSize, mAvatarSize)
                .transform(new RoundedTransformation())
                .into(holder.ivUserAvatar);
    }

    private void runEnterAnimation(View view, int position) {
        if (mAnimationsLocked) return;

        if (position > mLastAnimatedPosition) {
            mLastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(mDelayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mAnimationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return mItemsCount;
    }

    public void updateItems() {
        mItemsCount = 10;
        notifyDataSetChanged();
    }

    public void addItem() {
        mItemsCount++;
        notifyItemInserted(mItemsCount - 1);
    }

    public void setAnimationsLocked(boolean animationsLocked) {
        this.mAnimationsLocked = animationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.mDelayEnterAnimation = delayEnterAnimation;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ivUserAvatar)
        ImageView ivUserAvatar;
        @InjectView(R.id.tvComment)
        TextView tvComment;

        public CommentViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
