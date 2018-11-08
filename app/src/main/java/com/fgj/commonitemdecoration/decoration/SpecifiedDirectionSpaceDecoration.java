package com.fgj.commonitemdecoration.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 为符合条件的item的某个方向添加一段默认的透明间隔，默认为所有item
 * Created by FGJ on 2018/10/9.
 */
public class SpecifiedDirectionSpaceDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;
    private final Direction mDirection;
    private final DecorationPositionListener mDecorationPositionListener;

    public SpecifiedDirectionSpaceDecoration(int space, Direction direction) {
        this(space, direction, null);
    }

    public SpecifiedDirectionSpaceDecoration(int space, Direction direction, DecorationPositionListener decorationPositionListener) {
        mSpace = space;
        mDirection = direction;
        mDecorationPositionListener = decorationPositionListener;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mSpace <= 0 || (mDecorationPositionListener != null
                && !mDecorationPositionListener.isNeedDecoration(parent.getChildAdapterPosition(view)))) {
            return;
        }

        switch (mDirection) {
            case LEFT:
                outRect.left = mSpace;
                break;
            case TOP:
                outRect.top = mSpace;
                break;
            case RIGHT:
                outRect.right = mSpace;
                break;
            case BOTTOM:
                outRect.bottom = mSpace;
                break;
            case HORIZONTAL:
                outRect.left = outRect.right = mSpace;
                break;
            case VERTICAL:
                outRect.top = outRect.bottom = mSpace;
                break;
        }
    }

}
