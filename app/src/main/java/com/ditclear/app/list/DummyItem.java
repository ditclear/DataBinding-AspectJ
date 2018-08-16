package com.ditclear.app.list;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

/**
 * 页面描述：假数据
 *
 * Created by ditclear on 2017/8/12.
 */

public class DummyItem extends BaseObservable{

    public DummyItem(String content) {

        this.content.set(content);
    }

    public ObservableField<String> content =new ObservableField<String>();





}
