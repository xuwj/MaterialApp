package com.xwj.material;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.inject(this);
    }


}
