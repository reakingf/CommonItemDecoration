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
 * 用于线性布局的多功能透明间隔ItemDecoration：
 * 1. 给item添加透明间隔
 * 2. 支持在透明间隔的顶部和底部分别添加分割线，即分割线-间隔-分割线 {@link #mNeedDoubleDivider}
 * 3. 支持自定义分割线样式 {@link #mDividerDrawable}
 * 4. 支持指定某些特定条件下是否需要decoration {@link DecorationPositionListener}
 * 5. 可以通过控制边缘变量{@link #mListLeftSpace 等}决定列表边缘是否需要间隔
 * Orientation: Vertical
 * ------------------------                  Orientation: Horizontal
 * 丨      listTopSpace    丨       -------------------------------------------
 * 丨      ---------       丨      丨              listTopSpace               丨
 * 丨     丨 item1 丨      丨      丨list  ---------  item  ---------   list  丨
 * 丨      ---------       丨      丨Left 丨 item1 丨 Space丨 item2 丨  Right 丨
 * 丨       itemSpace      丨      丨Space ---------        ---------   Space 丨
 * 丨list  ---------  list 丨      丨            listBottomSpace              丨
 * 丨Left 丨 item2 丨 Right丨       -------------------------------------------
 * 丨Space ---------  Space丨
 * 丨       itemSpace      丨
 * 丨      ---------       丨
 * 丨     丨 item3 丨      丨
 * 丨      ---------       丨
 * 丨   listBottomSpace    丨
 * ------------------------
 * Created by FGJ on 2018/7/25.
 */
@SuppressWarnings({"WeakerAccess", "JavaDoc"})
public class LinearPowerfulSpacesDecoration extends RecyclerView.ItemDecoration {

    private int mListLeftSpace;     // 垂直布局为每个item的左边距，水平布局为第一个item的左边距
    private int mListTopSpace;      // 垂直布局为第一个item的上边距，水平布局为每个item的上边距
    private int mListRightSpace;    // 垂直布局为每个item的右边距，水平布局为最后一个item的右边距
    private int mListBottomSpace;   // 垂直布局为最后一个item的下边距，水平布局为每个item的下边距
    private int mItemSpace;         // 垂直布局为每个item（不包括最后一个）的下边距，水平布局为每个item（不包括最后一个）的右边距
    private boolean mNeedDoubleDivider; // 是否需要分割线
    private Drawable mDividerDrawable;
    private DecorationPositionListener mDecorationPositionListener;

    /**
     * Don't instantiate this class by its constructor.
     * Instead, you should use {@link ItemDecorationUtil} or {@link Builder} to generate a instance.
     */
    private LinearPowerfulSpacesDecoration(int listLeftSpace, int listTopSpace, int listRightSpace,
                                           int listBottomSpace, int itemSpace,
                                           boolean needDoubleDivider, Drawable dividerDrawable,
                                           DecorationPositionListener decorationPositionListener) {
        mListLeftSpace = listLeftSpace;
        mListTopSpace = listTopSpace;
        mListRightSpace = listRightSpace;
        mListBottomSpace = listBottomSpace;
        mItemSpace = itemSpace;
        mNeedDoubleDivider = needDoubleDivider;
        if (dividerDrawable != null) {
            mDividerDrawable = dividerDrawable;
        } else {
            mDividerDrawable = DecorationUtil.getDrawable(R.drawable.shape_seperator);
        }
        mDecorationPositionListener = decorationPositionListener;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (!mNeedDoubleDivider) return;
        if (isHorizontalOrientation(parent)) {
            onHorizontalDraw(c, parent, state);
            return;
        }
        onVerticalDraw(c, parent, state);
    }

    private void onHorizontalDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (isHeaderOrFooter(parent, state, child)) continue;

            if (mDecorationPositionListener != null
                    && !mDecorationPositionListener.isNeedDecoration(parent.getChildAdapterPosition(child)))
                continue;

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left;
            int right;

            if (mListRightSpace > 0 && isLastItem(parent, state, child)) {
                //只画右边的左分割线
                left = child.getRight();
                right = left + mDividerDrawable.getIntrinsicWidth();
                mDividerDrawable.setBounds(left, top + mListTopSpace, right, bottom - mListBottomSpace);
                mDividerDrawable.draw(c);
                break;
            }

            if (mListTopSpace > 0 && isFirstItem(child, parent)) {
                right = child.getLeft();
                left = right - mDividerDrawable.getIntrinsicWidth();
                mDividerDrawable.setBounds(left, top + mListTopSpace, right, bottom - mListBottomSpace);
                mDividerDrawable.draw(c);
            }

            left = child.getRight();
            right = left + mDividerDrawable.getIntrinsicWidth();
            mDividerDrawable.setBounds(left, top + mListTopSpace, right, bottom - mListBottomSpace);
            mDividerDrawable.draw(c);
            left += mItemSpace + params.leftMargin + params.rightMargin;
            right = left + mDividerDrawable.getIntrinsicHeight();
            mDividerDrawable.setBounds(left, top + mListTopSpace, right, bottom - mListBottomSpace);
            mDividerDrawable.draw(c);
        }
    }

    private void onVerticalDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (isHeaderOrFooter(parent, state, child)) continue;

            if (mDecorationPositionListener != null
                    && !mDecorationPositionListener.isNeedDecoration(parent.getChildAdapterPosition(child)))
                continue;

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top;
            int bottom;

            if (mListBottomSpace > 0 && isLastItem(parent, state, child)) {
                //只画底部的上分割线
                top = child.getBottom();
                bottom = top + mDividerDrawable.getIntrinsicHeight();
                mDividerDrawable.setBounds(left + mListLeftSpace, top, right - mListRightSpace, bottom);
                mDividerDrawable.draw(c);
                break;
            }

            if (mListTopSpace > 0 && isFirstItem(child, parent)) {
                bottom = child.getTop();
                top = bottom - mDividerDrawable.getIntrinsicHeight();
                mDividerDrawable.setBounds(left + mListLeftSpace, top, right - mListRightSpace, bottom);
                mDividerDrawable.draw(c);
            }

            top = child.getBottom();
            bottom = top + mDividerDrawable.getIntrinsicHeight();
            mDividerDrawable.setBounds(left + mListLeftSpace, top, right - mListRightSpace, bottom);
            mDividerDrawable.draw(c);
            top += mItemSpace + params.topMargin + params.bottomMargin;
            bottom = top + mDividerDrawable.getIntrinsicHeight();
            mDividerDrawable.setBounds(left + mListLeftSpace, top, right - mListRightSpace, bottom);
            mDividerDrawable.draw(c);
        }
    }

    private boolean isHeaderOrFooter(RecyclerView parent, RecyclerView.State state, View child) {
//        if (parent.getAdapter() instanceof HeaderAndFooterRecyclerViewAdapter) {
//            int headerCount = ((HeaderAndFooterRecyclerViewAdapter) parent.getAdapter()).getHeaderViewsCount();
//            int footCount = ((HeaderAndFooterRecyclerViewAdapter) parent.getAdapter()).getFooterViewsCount();
//            int position = parent.getChildAdapterPosition(child);
//            return position < headerCount || position > state.getItemCount() - headerCount - footCount - 1;
//        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mDecorationPositionListener != null
                && !mDecorationPositionListener.isNeedDecoration(parent.getChildAdapterPosition(view))) {
            return;
        }

        // 头部和底部不需要加
        if (isHeaderOrFooter(parent, state, view)) return;

        if (isHorizontalOrientation(parent)) {
            getHorizontalOffset(outRect, view, parent, state);
        } else {
            getVerticalOffset(outRect, view, parent, state);
        }
    }

    private boolean isHorizontalOrientation(RecyclerView recyclerView) {
        return ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation() == LinearLayoutManager.HORIZONTAL;
    }

    private void getHorizontalOffset(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = mListTopSpace;
        outRect.bottom = mListBottomSpace;
        if (isFirstItem(view, parent)) {
            outRect.left = mListLeftSpace;
        }
        if (isLastItem(parent, state, view)) {
            outRect.right = mListRightSpace;
        } else {
            outRect.right = mItemSpace;
        }

        if (!mNeedDoubleDivider) return;

        if (mListRightSpace > 0 && isLastItem(parent, state, view)) {
            //最后一个item不加右分割线
            outRect.right += mDividerDrawable.getIntrinsicHeight();
            return;
        }

        if (mListLeftSpace > 0 && isFirstItem(view, parent)) {
            //只加第一个item的顶部间隔，且第一个item不加左分割线
            outRect.left += mDividerDrawable.getIntrinsicHeight();
        }

        if (mItemSpace > 0) {
            outRect.right += 2 * mDividerDrawable.getIntrinsicHeight();
        }
    }

    private void getVerticalOffset(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mListLeftSpace;
        outRect.right = mListRightSpace;
        if (isFirstItem(view, parent)) {
            outRect.top = mListTopSpace;
        }
        if (isLastItem(parent, state, view)) {
            outRect.bottom = mListBottomSpace;
        } else {
            outRect.bottom = mItemSpace;
        }

        if (!mNeedDoubleDivider) return;

        if (mListBottomSpace > 0 && isLastItem(parent, state, view)) {
            //最后一个item不加下分割线
            outRect.bottom += mDividerDrawable.getIntrinsicHeight();
            return;
        }

        if (mListTopSpace > 0 && isFirstItem(view, parent)) {
            //只加第一个item的顶部间隔，且第一个item不加上分割线
            outRect.top += mDividerDrawable.getIntrinsicHeight();
        }

        if (mItemSpace > 0) {
            outRect.bottom += 2 * mDividerDrawable.getIntrinsicHeight();
        }
    }

    private boolean isFirstItem(View child, RecyclerView parent) {
        return parent.getChildAdapterPosition(child) == 0;
    }

    public static final class Builder {

        private int listLeftSpace;
        private int listTopSpace;
        private int listRightSpace;
        private int listBottomSpace;
        private int itemSpace;
        private boolean isNeedDoubleDivider;//是否需要分割线
        private Drawable dividerDrawable;
        private DecorationPositionListener decorationPositionListener;

        public Builder setListLeftSpace(int listLeftSpace) {
            this.listLeftSpace = listLeftSpace;
            return this;
        }

        public Builder setListTopSpace(int listTopSpace) {
            this.listTopSpace = listTopSpace;
            return this;
        }

        public Builder setListRightSpace(int listRightSpace) {
            this.listRightSpace = listRightSpace;
            return this;
        }

        public Builder setListBottomSpace(int listBottomSpace) {
            this.listBottomSpace = listBottomSpace;
            return this;
        }

        public Builder setItemSpace(int itemSpace) {
            this.itemSpace = itemSpace;
            return this;
        }

        public Builder setListHorizontalSpace(int listHorizontalSpace) {
            this.listLeftSpace = this.listRightSpace = listHorizontalSpace;
            return this;
        }

        public Builder setListVerticalSpace(int listVerticalSpace) {
            this.listTopSpace = this.listBottomSpace = listVerticalSpace;
            return this;
        }

        public Builder setListEdgeSpace(int listEdgeSpace) {
            listLeftSpace = listTopSpace = listRightSpace = listBottomSpace = listEdgeSpace;
            return this;
        }

        public Builder setNeedDoubleDivider(boolean needDoubleDivider) {
            this.isNeedDoubleDivider = needDoubleDivider;
            return this;
        }

        public Builder setDividerDrawable(Drawable dividerDrawable) {
            this.dividerDrawable = dividerDrawable;
            return this;
        }

        public Builder setDecorationPositionListener(DecorationPositionListener decorationPositionListener) {
            this.decorationPositionListener = decorationPositionListener;
            return this;
        }

        public LinearPowerfulSpacesDecoration build() {
            return new LinearPowerfulSpacesDecoration(listLeftSpace, listTopSpace, listRightSpace,
                    listBottomSpace, itemSpace, isNeedDoubleDivider, dividerDrawable, decorationPositionListener);
        }
    }

}