package com.wenming.weiswift.app.timeline.data.viewholder.retweet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.wenming.weiswift.R;
import com.wenming.weiswift.app.common.entity.Status;
import com.wenming.weiswift.app.timeline.adapter.TimeLineImageAdapter;
import com.wenming.weiswift.app.timeline.data.viewholder.base.BaseTimeLineContract;
import com.wenming.weiswift.app.timeline.data.viewholder.base.BaseTimeLineViewHolder;
import com.wenming.weiswift.app.weibodetail.activity.RetweetPicTextCommentDetailSwipeActivity;
import com.wenming.weiswift.widget.emojitextview.EmojiTextView;
import com.wenming.weiswift.widget.emojitextview.WeiBoContentTextUtil;

public class RetweetViewHolder extends BaseTimeLineViewHolder implements RetweetContract.View {
    private LinearLayout mStatusContainerLl;
    private LinearLayout mRetweetContentLl;
    private EmojiTextView mRetweetContentTv;
    private EmojiTextView mOriginContentTv;
    private RecyclerView mOriginImgRlv;
    private TimeLineImageAdapter mImgAdapter;
    private RetweetContract.Presenter mPresenter;

    public RetweetViewHolder(Context context, View v) {
        super(context, v);
    }

    @Override
    protected void prepareView() {
        super.prepareView();
        mStatusContainerLl = (LinearLayout) findViewById(R.id.timeline_retweet_pictext_container_ll);
        mRetweetContentTv = (EmojiTextView) findViewById(R.id.timeline_retweet_text_tv);
        mRetweetContentLl = (LinearLayout) findViewById(R.id.timeline_retweet_content_ll);
        mOriginContentTv = (EmojiTextView) findViewById(R.id.timeline_retweet_content_text_tv);
        mOriginImgRlv = (RecyclerView) findViewById(R.id.timeline_retweet_content_img_rlv);
    }

    @Override
    protected void initView() {
        super.initView();
        initImgList();
    }

    private void initImgList() {
    }

    @Override
    protected void initListener() {
        super.initListener();
        //微博背景的点击事件
        mStatusContainerLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.goToRetweetDetailActivity();
            }
        });
    }

    @Override
    public void setPresenter(BaseTimeLineContract.Presenter presenter) {
        mPresenter = (RetweetContract.Presenter) presenter;
    }

    @Override
    public void setOriginText(String text) {
        mOriginContentTv.setText(WeiBoContentTextUtil.getWeiBoContent(text, mContext, mOriginContentTv));
    }

    @Override
    public void setDeleteOriginText() {
        if (mContext == null) {
            return;
        }
        mOriginContentTv.setText(WeiBoContentTextUtil.getWeiBoContent(mContext.getString(R.string.timeline_retweet_delete_origin), mContext, mOriginContentTv));
    }

    @Override
    public void setRetweetText(String text) {
        mRetweetContentTv.setText(WeiBoContentTextUtil.getWeiBoContent(text, mContext, mRetweetContentTv));
    }

    @Override
    public void goToRetweetDetailActivity(Status status) {
        Intent intent = new Intent(mContext, RetweetPicTextCommentDetailSwipeActivity.class);
        intent.putExtra("weiboitem", status);
        mContext.startActivity(intent);
    }

    @Override
    public void setImgListVisible(boolean visible) {
        if (visible) {
            mOriginImgRlv.setVisibility(View.VISIBLE);
        } else {
            mOriginImgRlv.setVisibility(View.GONE);
        }
    }

    @Override
    public void setImgListContent(Status status) {
        GridLayoutManager gridLayoutManager = initGridLayoutManager(status.bmiddle_pic_urls);
        mImgAdapter = new TimeLineImageAdapter(status, mContext);
        mImgAdapter.setData(status);
        mOriginImgRlv.setHasFixedSize(true);
        mOriginImgRlv.setAdapter(mImgAdapter);
        mOriginImgRlv.setLayoutManager(gridLayoutManager);
        mOriginImgRlv.setNestedScrollingEnabled(false);
    }

    @Override
    public boolean isImgListVisble() {
        if (mOriginImgRlv.getVisibility() == View.GONE || mOriginImgRlv.getVisibility() == View.INVISIBLE) {
            return false;
        } else {
            return true;
        }
    }
}