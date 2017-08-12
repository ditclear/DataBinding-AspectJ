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


    public void onNormalClick(View view) {

        normalText.set(normalText.get()+"click\n");
    }

    @SingleClick
    public void onHookClick(View view) {
        hookText.set(hookText.get()+"click\n");
    }

    public void onClearClick(View view) {

        normalText.set("");
        hookText.set("");
    }
}
