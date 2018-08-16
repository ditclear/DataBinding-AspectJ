package com.ditclear.app

import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View

import com.ditclear.app.aop.annotation.SingleClick

/**
 * 页面描述：viewmodel
 *
 * Created by ditclear on 2017/8/12.
 */

class MainViewModelKT : BaseObservable() {

    val normalText = ObservableField("")

    val hookText = ObservableField("")


    /**
     * 普通的点击事件
     * @param view view
     */
    fun onNormalClick(view: View) {

        normalText.set(String.format("%s click\n", normalText.get()))
    }

    /**
     * 防止多次点击
     * @param view view
     */
    @SingleClick
    fun onHookClick(view: View) {
        hookText.set(String.format("%s click\n", hookText.get()))
    }

    //清空
    fun onClearClick(view: View) {
        normalText.set("")
        hookText.set("")
    }
}
