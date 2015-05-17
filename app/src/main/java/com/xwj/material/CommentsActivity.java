package com.xwj.material;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xwj.material.adapters.CommentsAdapter;
import com.xwj.material.utils.PhoneUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 评论界面
 * Created by user on 2015/5/12.
 */
public class CommentsActivity extends ActionBarActivity {
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";
    @InjectView(R.id.toolbar)
    Toolbar mToolbarTl;
    @InjectView(R.id.contentRoot)
    LinearLayout mContentRootLl;
    @InjectView(R.id.rvComments)
    RecyclerView mCommentsRv;
    @InjectView(R.id.llAddComment)
    LinearLayout mAddCommentLl;
    @InjectView(R.id.et_comment_content)
    EditText mCommentContentEt;

    private CommentsAdapter mCommentsAdapter;
    private int mDrawingStartLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.inject(this);
        setupToolbar();
        setupComments();

        mDrawingStartLocation = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION, 0);
        if (savedInstanceState == null) {
            mContentRootLl.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    mContentRootLl.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();
                    return true;
                }
            });
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbarTl);
        mToolbarTl.setNavigationIcon(R.drawable.ic_menu_white);
    }

    private void setupComments() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mCommentsRv.setLayoutManager(linearLayoutManager);
        mCommentsRv.setHasFixedSize(true);

        mCommentsAdapter = new CommentsAdapter(this);
        mCommentsRv.setAdapter(mCommentsAdapter);
        mCommentsRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mCommentsAdapter.setAnimationsLocked(true);
                }
            }
        });
    }

    /**
     * lv开始动画
     */
    private void startIntroAnimation() {
        mContentRootLl.setScaleY(0.1f);
        mContentRootLl.setPivotY(mDrawingStartLocation);
        mAddCommentLl.setTranslationY(100);

        mContentRootLl.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animateContent();
                    }
                })
                .start();
    }

    private void animateContent() {
        mCommentsAdapter.updateItems();
        mAddCommentLl.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem inboxMenuItem = menu.findItem(R.id.action_inbox);
        inboxMenuItem.setActionView(R.layout.menu_item_view);
        return true;
    }

    @OnClick(R.id.btnSendComment)
    public void onSendCommentClick() {
        mCommentContentEt.setText("");
        mCommentsAdapter.addItem();
        mCommentsAdapter.setAnimationsLocked(false);
        mCommentsAdapter.setDelayEnterAnimation(false);
        mCommentsRv.smoothScrollBy(0, mCommentsRv.getChildAt(0).getHeight() * mCommentsAdapter.getItemCount());
    }

    /**
     * 退出动画监听
     */
    @Override
    public void onBackPressed() {
        mContentRootLl.animate().translationY(PhoneUtils.getScreenHeight(this))
                .setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                CommentsActivity.super.onBackPressed();
                overridePendingTransition(0, 0);
            }
        });
    }
}
