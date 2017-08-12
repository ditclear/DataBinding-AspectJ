package com.ditclear.app.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.view.MenuItem;
import android.view.View;

import com.ditclear.app.R;
import com.ditclear.app.aop.annotation.SingleClick;
import com.ditclear.app.databinding.ActivityListBinding;
import com.github.markzhai.recyclerview.SingleTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面描述：列表页点击
 *
 * Created by ditclear on 2017/8/12.
 */

public class ListActivity extends AppCompatActivity implements ItemClickListener<DummyItem> {

    private ActivityListBinding mBinding;
    private List<DummyItem> mItems;
    private SingleTypeAdapter<DummyItem> mAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List-AspectJ");

        mBinding.recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SingleTypeAdapter<>(this, R.layout.list_item);
        mAdapter.setPresenter(this);

        mBinding.setAdapter(mAdapter);

        initFakeData();

    }

    private void initFakeData() {
        mItems = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            mItems.add(new DummyItem(String.format("Item %d", i)));
        }
        mAdapter.set(mItems);
    }

    @Override
    @SingleClick
    public void onItemClick(View view, DummyItem dummyItem) {
        dummyItem.setContent(String.format("%s click",dummyItem.getContent()));
        mAdapter.notifyItemChanged(mItems.indexOf(dummyItem));
    }
}
