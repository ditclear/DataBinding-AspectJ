package com.ditclear.app.list;

import android.view.View;

import com.github.markzhai.recyclerview.BaseViewAdapter;

/**
 * 页面描述：列表点击事件
 *
 * Created by ditclear on 2017/8/12.
 */

public interface ItemClickListener<T> extends BaseViewAdapter.Presenter {

    void onItemClick(View v,T t);
}
