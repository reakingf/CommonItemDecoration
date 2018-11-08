package com.fgj.commonitemdecoration.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fgj.commonitemdecoration.R;

import static com.fgj.commonitemdecoration.decoration.ItemDecorationUtil.isLastItem;

/**
 * 给线性布局item的底部（垂直布局）或右边（水平布局）添加分割线
 * Created by FGJ on 2018/7/25.
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private boolean mIsSkipLastItem;
    private int mDividerStart;
    private int mDividerMarginLeft;
    private int mDividerMarginRight;
    private int mDividerMarginTop;
    private int mDividerMarginBottom;

    /**
     * Don't instantiate this class by its constructor.
     * Instead, you should use {@link ItemDecorationUtil} or {@link Builder} to generate a instance.
     */
    private LinearItemDecoration(Drawable divider, boolean isSkipLastItem,
                                 int dividerStart, int dividerMarginTop, int dividerMarginBottom,
                                 int dividerMarginLeft, int dividerMarginRight) {
        if (divider != null) {
            mDivider = divider;
        } else {
            mDivider = DecorationUtil.getDrawable(R.drawable.shape_seperator);
        }
        mIsSkipLastItem = isSkipLastItem;
        mDividerStart = dividerStart;
        mDividerMarginTop = dividerMarginTop;
        mDividerMarginBottom = dividerMarginBottom;
        mDividerMarginLeft = dividerMarginLeft;
        mDividerMarginRight = dividerMarginRight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (isVerticalOrientation(parent)) {
            drawVertical(c, parent, state);
        } else {
            drawHorizontal(c, parent, state);
        }
    }

    private boolean isVerticalOrientation(RecyclerView recyclerView) {
        return ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL;
    }

    /**
     * 绘制垂直
     **/
    public void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            int position = parent.getChildAdapterPosition(child);

            if (mDividerStart > 0 && position < mDividerStart) continue;

            if (mIsSkipLastItem && isLastItem(parent, state, child)) continue;

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left + mDividerMarginLeft, top, right - mDividerMarginRight, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 绘制横向
     **/
    public void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            int position = parent.getChildAdapterPosition(child);

            if (mDividerStart > 0 && position < mDividerStart) continue;

            if (mIsSkipLastItem && isLastItem(parent, state, child)) continue;

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top + mDividerMarginTop, right, bottom - mDividerMarginBottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (mIsSkipLastItem && isLastItem(parent, state, view)) return;

        int position = parent.getChildAdapterPosition(view);

        if (mDividerStart > 0 && position < mDividerStart) return;

        if (isVerticalOrientation(parent)) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

    public static final class Builder {

        private Drawable divider;
        private boolean isSkipLastItem;
        private int dividerStart;
        private int dividerMarginTop;
        private int dividerMarginBottom;
        private int dividerMarginLeft;
        private int dividerMarginRight;

        public Builder setDrawable(Drawable drawable) {
            divider = drawable;
            return this;
        }

        public Builder isSkipLastItem(boolean skipLastItem) {
            isSkipLastItem = skipLastItem;
            return this;
        }

        public Builder setDividerStart(int dividerStart) {
            this.dividerStart = dividerStart;
            return this;
        }

        public Builder setDividerMargin(int dividerMargin) {
            dividerMarginTop = dividerMarginBottom = dividerMarginLeft = dividerMarginRight = dividerMargin;
            return this;
        }

        public Builder setDividerMarginVertical(int verticalMargin) {
            dividerMarginTop = dividerMarginBottom = verticalMargin;
            return this;
        }

        public Builder setDividerMarginHorizontal(int horizontalMargin) {
            this.dividerMarginLeft = this.dividerMarginRight = horizontalMargin;
            return this;
        }

        public Builder setDividerMarginTop(int dividerMarginTop) {
            this.dividerMarginTop = dividerMarginTop;
            return this;
        }

        public Builder setDividerMarginBottom(int dividerMarginBottom) {
            this.dividerMarginBottom = dividerMarginBottom;
            return this;
        }

        public Builder setDividerMarginLeft(int dividerMarginLeft) {
            this.dividerMarginLeft = dividerMarginLeft;
            return this;
        }

        public Builder setDivideMarginRight(int dividerMarginRight) {
            this.dividerMarginRight = dividerMarginRight;
            return this;
        }

        public LinearItemDecoration build() {
            return new LinearItemDecoration(divider, isSkipLastItem, dividerStart, dividerMarginTop,
                    dividerMarginBottom, dividerMarginLeft, dividerMarginRight);
        }

    }

}