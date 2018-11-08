package com.fgj.commonitemdecoration.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fgj.commonitemdecoration.R;

/**
 * 给首个Item的上方添加一条分割线
 * Created by FGJ on 2018/7/26
 */
@SuppressWarnings("WeakerAccess")
public class FirstItemTopDivider extends RecyclerView.ItemDecoration {

    private Drawable dividerDrawable;
    private int marginLeft;
    private int marginRight;

    public static FirstItemTopDivider of(int margin) {
        return of(margin, margin);
    }

    public static FirstItemTopDivider of(int marginLeft, int marginRight) {
        return of(null, marginLeft, marginRight);
    }

    public static FirstItemTopDivider of(Drawable dividerDrawable, int margin) {
        return of(dividerDrawable, margin, margin);
    }

    public static FirstItemTopDivider of(Drawable dividerDrawable, int marginLeft, int marginRight) {
        return new FirstItemTopDivider(dividerDrawable, marginLeft, marginRight);
    }

    public FirstItemTopDivider(Drawable dividerDrawable, int marginLeft, int marginRight) {
        if (dividerDrawable != null) {
            this.dividerDrawable = dividerDrawable;
        } else {
            this.dividerDrawable = DecorationUtil.getDrawable(R.drawable.shape_seperator);
        }
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top += dividerDrawable.getIntrinsicHeight();
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(child) == 0) {
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int bottom = child.getTop() + params.topMargin;
                int top = bottom - dividerDrawable.getIntrinsicHeight();
                dividerDrawable.setBounds(parent.getPaddingLeft() + marginLeft, top,
                        parent.getWidth() - parent.getPaddingRight() - marginRight, bottom);
                dividerDrawable.draw(c);
                break;
            }
        }
    }
}