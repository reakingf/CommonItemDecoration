package com.fgj.commonitemdecoration.demo;

/**
 * Created by FGJ on 2018/8/27.
 */
public enum DecorationEnum {
    BottomSpaceDecoration(0, "BottomSpaceDecoration"),
    LastBottomSpaceDecoration(1, "LastBottomSpaceDecoration"),
    RightItemDecoration(2, "RightItemDecoration"),
    GridItemDecoration(3, "GridItemDecoration"),
    LinearItemDivider(4, "LinearItemDivider"),
    SpacesDecoration(5, "SpacesDecoration"),
    SpacesWith2DividerDecoration(6, "SpacesWith2DividerDecoration"),
    FirstItemTopDivider(7, "FirstItemTopDivider");

    public int index;
    public String name;

    DecorationEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }

}
