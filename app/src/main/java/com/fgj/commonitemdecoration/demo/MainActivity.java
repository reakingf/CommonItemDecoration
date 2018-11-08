package com.fgj.commonitemdecoration.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fgj.commonitemdecoration.R;
import com.fgj.commonitemdecoration.decoration.FirstItemTopDivider;
import com.fgj.commonitemdecoration.decoration.GridSpaceDecoration;
import com.fgj.commonitemdecoration.decoration.ItemDecorationUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by FGJ on 2018/7/25.
 */
public class MainActivity extends Activity {

    private static final String KEY_TAPE = "KEY_TAPE";

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        initDecoration();

        DecorationAdapter adapter = new DecorationAdapter(this, new ArrayList<>(Arrays.asList(DecorationEnum.values())));
        adapter.setItemClickListener(position -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(KEY_TAPE, adapter.getItem(position).index);
            startActivity(intent);
            finish();
        });

        recyclerView.setAdapter(adapter);
    }

    private void initDecoration() {
        int type = getIntent().getIntExtra(KEY_TAPE, DecorationEnum.SpacesDecoration.index);

        if (type == DecorationEnum.BottomSpaceDecoration.index) {
            ItemDecorationUtil.bottomSpaceDecoration(recyclerView, getResources().getDimensionPixelSize(R.dimen.dp_10));
        } else if (type == DecorationEnum.LastBottomSpaceDecoration.index) {
            ItemDecorationUtil.bottomSpaceDecoration(recyclerView, getResources().getDimensionPixelSize(R.dimen.dp_10),
                    itemPosition -> itemPosition == recyclerView.getAdapter().getItemCount() - 1);
        } else if (type == DecorationEnum.RightItemDecoration.index) {
            ItemDecorationUtil.rightSpaceDecoration(recyclerView, getResources().getDimensionPixelSize(R.dimen.dp_10));
        } else if (type == DecorationEnum.GridItemDecoration.index) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addItemDecoration(new GridSpaceDecoration(getResources().getDimensionPixelSize(R.dimen.dp_10)));
        } else if (type == DecorationEnum.LinearItemDivider.index) {
            ItemDecorationUtil.normalDivider(recyclerView);
        } else if (type == DecorationEnum.SpacesDecoration.index) {
            ItemDecorationUtil.spaceDecoration(recyclerView, getResources().getDimensionPixelSize(R.dimen.dp_10));
        } else if (type == DecorationEnum.SpacesWith2DividerDecoration.index) {
            ItemDecorationUtil.spaceDecorationWith2Divider(recyclerView, getResources().getDimensionPixelSize(R.dimen.dp_10), false);
        } else if (type == DecorationEnum.FirstItemTopDivider.index) {
            findViewById(R.id.tv_title).setBackgroundResource(R.color.white);
            recyclerView.addItemDecoration(FirstItemTopDivider.of(0));
        }
    }

}
