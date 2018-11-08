package com.fgj.commonitemdecoration.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fgj.commonitemdecoration.R;

/**
 * 简易悬浮Decoration
 * Created by FGJ on 2018/8/30.
 */
public class SimpleStickyDecoration extends RecyclerView.ItemDecoration {

    private Paint mGroupTextPaint;
    private Paint mGroupBackgroundPaint;

    private int mGroupHeight = DecorationUtil.getDimensionPixelSize(R.dimen.dp_38);
    private int mGroupBackgroundColor = DecorationUtil.getColor(R.color.gray_f3f);

    private int mTextPaddingLeft = DecorationUtil.getDimensionPixelSize(R.dimen.dp_14); // 分组文字左边距
    private int mTextSize = DecorationUtil.getDimensionPixelSize(R.dimen.font_14);
    private int mTextColor = DecorationUtil.getColor(R.color.black_212);
    private float mTextYAxis;

    private GroupListener mGroupListener;

    public SimpleStickyDecoration(@NonNull GroupListener groupListener) {
        init(groupListener);
    }

    private SimpleStickyDecoration(int groupHeight, int groupBackground, int textPaddingLeft,
                                   int textSize, int textColor, @NonNull GroupListener groupListener) {
        if (groupHeight > 0) {
            mGroupHeight = groupHeight;
        }
        if (groupBackground > 0) {
            mGroupBackgroundColor = groupBackground;
        }
        if (textPaddingLeft >= 0) {
            mTextPaddingLeft = textPaddingLeft;
        }
        if (textSize > 0) {
            mTextSize = textSize;
        }
        if (textColor > 0) {
            mTextColor = textColor;
        }

        init(groupListener);
    }

    private void init(GroupListener groupListener) {
        mGroupListener = groupListener;

        mGroupBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGroupBackgroundPaint.setColor(mGroupBackgroundColor);

        mGroupTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGroupTextPaint.setTextSize(mTextSize);
        mGroupTextPaint.setTextAlign(Paint.Align.LEFT);
        mGroupTextPaint.setColor(mTextColor);

        Paint.FontMetrics fontMetrics = mGroupTextPaint.getFontMetrics();
        float total = -fontMetrics.ascent + fontMetrics.descent;
        mTextYAxis = total / 2 - fontMetrics.descent;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        String curGroupName = mGroupListener.getGroupName(position);
        if (DecorationUtil.isEmptyString(curGroupName)) return;
        if (isNeedNewGroup(position, curGroupName)) {
            outRect.top = mGroupHeight;
        }
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        super.onDrawOver(canvas, recyclerView, state);
        int childCount = recyclerView.getChildCount();
        int left = recyclerView.getLeft() + recyclerView.getPaddingLeft();
        int right = recyclerView.getRight() - recyclerView.getPaddingRight();

        String firstVisibleGroupName = null;
        int translateTop = 0;   // 首个可见分组的偏移量
        for (int i = 0; i < childCount; i++) {
            View childView = recyclerView.getChildAt(i);
            int position = recyclerView.getChildAdapterPosition(childView);
            String curHeaderName = mGroupListener.getGroupName(position);
            if (i == 0) {
                firstVisibleGroupName = curHeaderName;
            }
            if (curHeaderName == null) continue;

            int viewTop = childView.getTop() + recyclerView.getPaddingTop();

            if (isNeedNewGroup(position, curHeaderName)) {
                canvas.drawRect(left, viewTop - mGroupHeight, right, viewTop, mGroupBackgroundPaint);
                canvas.drawText(curHeaderName, left + mTextPaddingLeft,
                        viewTop - mGroupHeight / 2 + mTextYAxis, mGroupTextPaint);
                if (mGroupHeight < viewTop && viewTop <= 2 * mGroupHeight) {
                    // 2个分组碰撞，计算首个分组需要偏移的量
                    translateTop = viewTop - 2 * mGroupHeight;
                }
            }
        }

        if (firstVisibleGroupName == null) return;

        canvas.save();
        canvas.translate(0, translateTop);
        // 屏幕上首个可见分组渐渐滑出屏幕
        canvas.drawRect(left, 0, right, mGroupHeight, mGroupBackgroundPaint);
        canvas.drawText(firstVisibleGroupName, left + mTextPaddingLeft, mGroupHeight / 2 + mTextYAxis, mGroupTextPaint);
        canvas.restore();
    }

    public int getGroupHeight() {
        return mGroupHeight;
    }

    /**
     * 如果当前位置为0，或者与上一个item组别名不同的，都需要添加新的组别
     */
    private boolean isNeedNewGroup(int pos, String curGroupName) {
        return pos == 0 || !curGroupName.equals(mGroupListener.getGroupName(pos - 1));
    }

    public static class Builder {

        private int groupHeight = -1;
        private int groupBackground = -1;
        private int textPaddingLeft = -1;
        private int textSize = -1;
        private int textColor = -1;

        private GroupListener groupListener;

        public Builder(@NonNull GroupListener groupListener) {
            this.groupListener = groupListener;
        }

        public Builder setTextPaddingLeft(int textPaddingLeft) {
            this.textPaddingLeft = textPaddingLeft;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setGroupHeight(int groupHeight) {
            this.groupHeight = groupHeight;
            return this;
        }

        public Builder setGroupBackground(int groupBackground) {
            this.groupBackground = groupBackground;
            return this;
        }

        public SimpleStickyDecoration build() {
            return new SimpleStickyDecoration(groupHeight, groupBackground, textPaddingLeft,
                    textSize, textColor, groupListener);
        }

    }

    public interface GroupListener {

        String getGroupName(int position);

    }

}

