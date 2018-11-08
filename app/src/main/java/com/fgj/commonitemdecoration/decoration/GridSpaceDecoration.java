package com.fgj.commonitemdecoration.decoration;

import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * 网格透明间隔
 * http://blog.csdn.net/yanggz888/article/details/54379208
 * Author: zipon on 2017/8/1.
 * Email: 605370697@qq.com
 */
public class GridSpaceDecoration extends RecyclerView.ItemDecoration {

    private static final int VERTICAL = OrientationHelper.VERTICAL;

    private int orientation = -1;
    private int spanCount = -1;
    private int spacing;
    private int halfSpacing;
    private int columns;

    public GridSpaceDecoration(@DimenRes int spacingDimen, int columns) {
        spacing = DecorationUtil.getDimensionPixelSize(spacingDimen);
        this.columns = columns;
        halfSpacing = spacing / 2;
    }

    public GridSpaceDecoration(int spacingPx) {
        spacing = spacingPx;
        halfSpacing = spacing / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);

        if (orientation == -1) {
            orientation = getOrientation(parent);
        }
        if (spanCount == -1) {
            spanCount = getTotalSpan(parent);
        }
        int childCount = parent.getLayoutManager().getItemCount();
        int childIndex = parent.getChildAdapterPosition(view);
        int itemSpanSize = getItemSpanSize(parent, childIndex);
        int spanIndex = getItemSpanIndex(parent, childIndex);
        if (spanCount < 1) {
            return;
        }

        setSpacings(outRect, parent, childCount, childIndex, itemSpanSize, spanIndex);
    }

    private void setSpacings(Rect outRect, RecyclerView parent, int childCount, int childIndex, int itemSpanSize, int spanIndex) {

        outRect.top = halfSpacing;
        outRect.bottom = halfSpacing;
        outRect.left = halfSpacing;
        outRect.right = halfSpacing;

        if (isTopEdge(parent, childCount, childIndex, itemSpanSize, spanIndex)) {
            outRect.top = spacing;
        }
        if (isLeftEdge(parent, childCount, childIndex, itemSpanSize, spanIndex)) {
            outRect.left = spacing;
        }
        if (isRightEdge(parent, childCount, childIndex, itemSpanSize, spanIndex)) {
            outRect.right = spacing;
        }
        if (isBottomEdge(parent, childCount, childIndex, itemSpanSize, spanIndex)) {
            outRect.bottom = spacing;
        }
    }

    @SuppressWarnings("all")
    private int getTotalSpan(RecyclerView parent) {

        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
        if (mgr instanceof GridLayoutManager) {
            return ((GridLayoutManager) mgr).getSpanCount();
        } else if (mgr instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) mgr).getSpanCount();
        } else if (mgr instanceof LinearLayoutManager) {
            return 1;
        }

        return -1;
    }

    @SuppressWarnings("all")
    private int getItemSpanSize(RecyclerView parent, int childIndex) {

        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
        if (mgr instanceof GridLayoutManager) {
            return ((GridLayoutManager) mgr).getSpanSizeLookup().getSpanSize(childIndex);
        } else if (mgr instanceof StaggeredGridLayoutManager) {
            return 1;
        } else if (mgr instanceof LinearLayoutManager) {
            return 1;
        }

        return -1;
    }

    @SuppressWarnings("all")
    private int getItemSpanIndex(RecyclerView parent, int childIndex) {

        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
        if (mgr instanceof GridLayoutManager) {
            return ((GridLayoutManager) mgr).getSpanSizeLookup().getSpanIndex(childIndex, spanCount);
        } else if (mgr instanceof StaggeredGridLayoutManager) {
            return childIndex % spanCount;
        } else if (mgr instanceof LinearLayoutManager) {
            return 0;
        }

        return -1;
    }

    @SuppressWarnings("all")
    private int getOrientation(RecyclerView parent) {

        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
        if (mgr instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) mgr).getOrientation();
        } else if (mgr instanceof GridLayoutManager) {
            return ((GridLayoutManager) mgr).getOrientation();
        } else if (mgr instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) mgr).getOrientation();
        }

        return VERTICAL;
    }

    private boolean isLeftEdge(RecyclerView parent, int childCount, int childIndex, int itemSpanSize, int spanIndex) {

        if (orientation == VERTICAL) {
            return spanIndex == 0;
        } else {
            return (childIndex == 0) || isFirstItemEdgeValid((childIndex < spanCount), parent, childIndex);
        }
    }

    private boolean isRightEdge(RecyclerView parent, int childCount, int childIndex, int itemSpanSize, int spanIndex) {

        if (orientation == VERTICAL) {
            return (spanIndex + itemSpanSize) == spanCount;
        } else {
            return isLastItemEdgeValid((childIndex >= childCount - spanCount), parent, childCount, childIndex, spanIndex);
        }
    }

    private boolean isTopEdge(RecyclerView parent, int childCount, int childIndex, int itemSpanSize, int spanIndex) {

        if (orientation == VERTICAL) {
            return (childIndex == 0) || isFirstItemEdgeValid((childIndex < spanCount), parent, childIndex);
        } else {
            return spanIndex == 0;
        }
    }

    private boolean isBottomEdge(RecyclerView parent, int childCount, int childIndex, int itemSpanSize, int spanIndex) {
        if (orientation == VERTICAL) {
            return isLastItemEdgeValid((childIndex >= childCount - spanCount), parent, childCount, childIndex, spanIndex);
        } else {
            return (spanIndex + itemSpanSize) == spanCount;
        }
    }

    private boolean isFirstItemEdgeValid(boolean isOneOfFirstItems, RecyclerView parent, int childIndex) {

        int totalSpanArea = 0;
        if (isOneOfFirstItems) {
            for (int i = childIndex; i >= 0; i--) {
                totalSpanArea = totalSpanArea + getItemSpanSize(parent, i);
            }
        }
        return isOneOfFirstItems && totalSpanArea <= spanCount;
    }

    private boolean isLastItemEdgeValid(boolean isOneOfLastItems, RecyclerView parent, int childCount, int childIndex, int spanIndex) {

        int totalSpanRemaining = 0;
        if (isOneOfLastItems) {

            for (int i = childIndex; i < childCount; i++) {
                totalSpanRemaining = totalSpanRemaining + getItemSpanSize(parent, i);
            }
        }
        return isOneOfLastItems && (totalSpanRemaining <= spanCount - spanIndex);
    }
}

