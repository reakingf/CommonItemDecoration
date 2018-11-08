package com.fgj.commonitemdecoration.decoration;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by FGJ on 2018/7/25.
 */
public class ItemDecorationUtil {

    private ItemDecorationUtil() {

    }

    /**
     * 普通分割线：1px的灰色分割线
     */
    public static void normalDivider(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new LinearItemDecoration.Builder().build());
    }

    /**
     * 普通分割线 - 带有margin
     */
    public static void normalDivider(RecyclerView recyclerView, int margin) {
        recyclerView.addItemDecoration(new LinearItemDecoration.Builder().setDividerMargin(margin).build());
    }

    /**
     * 普通分割线 - 带有margin
     **/
    public static void normalDivider(RecyclerView recyclerView, int marginLeft, int marginRight) {
        recyclerView.addItemDecoration(new LinearItemDecoration.Builder()
                .setDividerMarginLeft(marginLeft)
                .setDivideMarginRight(marginRight)
                .build());
    }

    /**
     * 普通分割线，且最后一个Item不添加分割线
     */
    public static void dividerAndSkipLast(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new LinearItemDecoration.Builder().isSkipLastItem(true).build());
    }

    /**
     * 普通分割线，且从指定位置开始画
     */
    public static void dividerWithStartPosition(RecyclerView recyclerView, int startPosition) {
        recyclerView.addItemDecoration(new LinearItemDecoration.Builder().setDividerStart(startPosition).build());
    }

    /**
     * 分割线 - 自定义Drawable
     */
    public static void customDivider(RecyclerView recyclerView, Drawable drawable) {
        recyclerView.addItemDecoration(new LinearItemDecoration.Builder()
                .setDrawable(drawable)
                .build());
    }

    static boolean isLastItem(RecyclerView recyclerView, int itemPosition) {
//        if (recyclerView.getAdapter() instanceof HeaderAndFooterRecyclerViewAdapter) {
//            return itemPosition == ((HeaderAndFooterRecyclerViewAdapter) recyclerView.getAdapter())
//                    .getInnerAdapter().getItemCount() - 1;
//        }
        return itemPosition == recyclerView.getAdapter().getItemCount() - 1;
    }

    static boolean isLastItem(RecyclerView parent, RecyclerView.State state, View child) {
//        int headerCount = 0;
//        int footCount = 0;
//        if (parent.getAdapter() instanceof HeaderAndFooterRecyclerViewAdapter) {
//            headerCount = ((HeaderAndFooterRecyclerViewAdapter) parent.getAdapter()).getHeaderViewsCount();
//            footCount = ((HeaderAndFooterRecyclerViewAdapter) parent.getAdapter()).getFooterViewsCount();
//        }
//        return parent.getChildAdapterPosition(child) == state.getItemCount() - headerCount - footCount - 1;
        return parent.getChildAdapterPosition(child) == state.getItemCount() - 1;
    }

    public static void leftSpaceDecoration(RecyclerView recyclerView, int space) {
        recyclerView.addItemDecoration(new SpecifiedDirectionSpaceDecoration(space, Direction.LEFT));
    }

    public static void leftSpaceDecoration(RecyclerView recyclerView, int space,
                                           DecorationPositionListener decorationPositionListener) {
        recyclerView.addItemDecoration(new SpecifiedDirectionSpaceDecoration(space, Direction.LEFT, decorationPositionListener));
    }

    public static void topSpaceDecoration(RecyclerView recyclerView, int space) {
        recyclerView.addItemDecoration(new SpecifiedDirectionSpaceDecoration(space, Direction.TOP));
    }

    public static void topSpaceDecoration(RecyclerView recyclerView, int space,
                                          DecorationPositionListener decorationPositionListener) {
        recyclerView.addItemDecoration(new SpecifiedDirectionSpaceDecoration(space, Direction.TOP, decorationPositionListener));
    }

    public static void rightSpaceDecoration(RecyclerView recyclerView, int space) {
        recyclerView.addItemDecoration(new SpecifiedDirectionSpaceDecoration(space, Direction.RIGHT));
    }

    public static void rightSpaceDecoration(RecyclerView recyclerView, int space,
                                            DecorationPositionListener decorationPositionListener) {
        recyclerView.addItemDecoration(new SpecifiedDirectionSpaceDecoration(space, Direction.RIGHT, decorationPositionListener));
    }

    public static void bottomSpaceDecoration(RecyclerView recyclerView, int space) {
        recyclerView.addItemDecoration(new SpecifiedDirectionSpaceDecoration(space, Direction.BOTTOM));
    }

    public static void bottomSpaceDecoration(RecyclerView recyclerView, int space,
                                             DecorationPositionListener decorationPositionListener) {
        recyclerView.addItemDecoration(new SpecifiedDirectionSpaceDecoration(space, Direction.BOTTOM, decorationPositionListener));
    }

    public static void bottomSpaceAndSkipLast(RecyclerView recyclerView, int space) {
        recyclerView.addItemDecoration(new SpecifiedDirectionSpaceDecoration(space, Direction.BOTTOM,
                itemPosition -> !isLastItem(recyclerView, itemPosition)));
    }

    /**
     * 给每个item加透明间隔
     */
    public static void spaceDecoration(RecyclerView recyclerView, int space) {
        recyclerView.addItemDecoration(new LinearPowerfulSpacesDecoration.Builder()
                .setItemSpace(space)
                .setListEdgeSpace(space)
                .build());
    }

    /**
     * 给每个item底部加透明间隔，且最后一个不加底部
     */
    public static void spaceAndSkipLastBottom(RecyclerView recyclerView, int space) {
        recyclerView.addItemDecoration(new LinearPowerfulSpacesDecoration.Builder()
                .setItemSpace(space)
                .setListTopSpace(space)
                .setListHorizontalSpace(space)
                .build());
    }

    /**
     * 给每个item水平方向加透明间隔
     */
    public static void horizontalSpaceDecoration(RecyclerView recyclerView, int space) {
        recyclerView.addItemDecoration(new LinearPowerfulSpacesDecoration.Builder()
                .setItemSpace(space)
                .setListHorizontalSpace(space)
                .build());
    }

    /**
     * 给每个item垂直方向加透明间隔
     */
    public static void verticalSpaceDecoration(RecyclerView recyclerView, int space, boolean isIgnoreLastBottom) {
        LinearPowerfulSpacesDecoration.Builder builder = new LinearPowerfulSpacesDecoration.Builder()
                .setItemSpace(space)
                .setListTopSpace(space);
        if (!isIgnoreLastBottom) {
            builder = builder.setListBottomSpace(space);
        }
        recyclerView.addItemDecoration(builder.build());
    }

    /**
     * 给每个item加带有分割线的透明间隔
     */
    public static void spaceDecorationWith2Divider(RecyclerView recyclerView, int space, boolean isIgnoreLastBottom) {
        LinearPowerfulSpacesDecoration.Builder builder = new LinearPowerfulSpacesDecoration.Builder()
                .setItemSpace(space)
                .setListHorizontalSpace(space)
                .setListTopSpace(space)
                .setNeedDoubleDivider(true);
        if (!isIgnoreLastBottom) {
            builder = builder.setListBottomSpace(space);
        }
        recyclerView.addItemDecoration(builder.build());
    }

}