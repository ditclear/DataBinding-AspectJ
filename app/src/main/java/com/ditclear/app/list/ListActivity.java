package com.ditclear.app.list;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
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

import io.ditclear.bindingadapter.ItemClickPresenter;
import io.ditclear.bindingadapter.SingleTypeAdapter;

/**
 * 页面描述：列表页点击
 *
 * Created by ditclear on 2017/8/12.
 */

public class ListActivity extends AppCompatActivity implements ItemClickPresenter<DummyItem> {

    private ActivityListBinding mBinding;
    private ObservableArrayList<DummyItem> mItems = new ObservableArrayList<DummyItem>();
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
        mAdapter = new SingleTypeAdapter<>(this, R.layout.list_item,mItems);

        mAdapter.setItemPresenter(this);
        mBinding.setAdapter(mAdapter);

        initFakeData();

    }

    private void initFakeData() {
        for (int i = 0; i < 15; i++) {
            mItems.add(new DummyItem(String.format("Item %d", i)));
        }
    }

    @Override
    @SingleClick
    public void onItemClick(View view, DummyItem dummyItem) {
        dummyItem.content.set(String.format("%s click",dummyItem.content.get()));
    }
}
