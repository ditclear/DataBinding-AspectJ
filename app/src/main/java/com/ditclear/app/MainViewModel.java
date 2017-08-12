package com.ditclear.app;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import com.ditclear.app.aop.annotation.SingleClick;

/**
 * 页面描述：viewmodel
 *
 * Created by ditclear on 2017/8/12.
 */

public class MainViewModel extends BaseObservable {

    public ObservableField<String > normalText=new ObservableField<>("");

    public ObservableField<String > hookText=new ObservableField<>("");


    /**
     * 普通的点击事件
     * @param view view
     */
    public void onNormalClick(View view) {

        normalText.set(String.format("%s click\n",normalText.get()));
    }

    /**
     * 防止多次点击
     * @param view view
     */
    @SingleClick
    public void onHookClick(View view) {
        hookText.set(String.format("%s click\n",hookText.get()));
    }

    //清空
    public void onClearClick(View view) {

        normalText.set("");
        hookText.set("");
    }
}
