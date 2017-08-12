package com.ditclear.app.list;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * 页面描述：假数据
 *
 * Created by ditclear on 2017/8/12.
 */

public class DummyItem extends BaseObservable{

    public DummyItem(String content) {
        this.content = content;
    }

    private String content;

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
