package com.ditclear.app

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.ditclear.app.databinding.ActivityMainKtBinding
import com.ditclear.app.list.ListActivity

class MainActivityKT : AppCompatActivity() {

    lateinit var mBinding: ActivityMainKtBinding

    private lateinit var mViewModel: MainViewModelKT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_kt)
        mViewModel = MainViewModelKT()
        mBinding.vm = mViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_list) {
            startActivity(Intent(this, ListActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
