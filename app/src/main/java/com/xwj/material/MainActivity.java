package com.xwj.material;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.xwj.material.adapters.FeedAdapter;
import com.xwj.material.component.Constants;
import com.xwj.material.utils.PhoneUtils;
import com.xwj.material.views.Toaster;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity implements FeedAdapter.OnFeedItemClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.ivLogo)
    ImageView mLogoIv;
    @InjectView(R.id.rvFeed)
    RecyclerView mFeedRv;
    @InjectView(R.id.btnCreate)
    ImageButton mCreateImgBtn;

    private MenuItem mInboxMenuItem;
    private FeedAdapter feedAdapter;
    private boolean pendingIntroAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setupToolbar();
        setupFeed();
        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        }
    }

    /**
     * 初始化toolbar
     */
    private void setupToolbar() {
        mToolbar.setTitle("");  // 需要在setSupportActionBar方法之前调用才能生效
        //mToolbar.setLogo(R.drawable.ic_launcher);
        //  mToolbar.setTitle("My Title");
        //   mToolbar.setSubtitle("Sub title");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white);
    }

    private void setupFeed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mFeedRv.setLayoutManager(linearLayoutManager);
        feedAdapter = new FeedAdapter(this);
        feedAdapter.setOnFeedItemClickListener(this);
        mFeedRv.setAdapter(feedAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mInboxMenuItem = menu.findItem(R.id.action_inbox);
        // mInboxMenuItem.setActionView(R.layout.menu_item_view);   //api 11
        // api 8
        mInboxMenuItem = MenuItemCompat.setActionView(mInboxMenuItem, R.layout.menu_item_view);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startIntroAnimation() {
        mCreateImgBtn.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));

        int actionbarSize = PhoneUtils.dip2px(this, 56);
        mToolbar.setTranslationY(-actionbarSize);
        mLogoIv.setTranslationY(-actionbarSize);
        mInboxMenuItem.getActionView().setTranslationY(-actionbarSize);

        mToolbar.animate()
                .translationY(0)
                .setDuration(Constants.ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        mLogoIv.animate()
                .translationY(0)
                .setDuration(Constants.ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        mInboxMenuItem.getActionView().animate()
                .translationY(0)
                .setDuration(Constants.ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();
                    }
                })
                .start();
    }

    private void startContentAnimation() {
        mCreateImgBtn.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(Constants.ANIM_DURATION_FAB)
                .start();
        feedAdapter.setUpdateItems();
    }

    @Override
    public void onCommentsClick(View v, int position) {
        Toaster.toast("---" + position);
    }
}
