package com.ditclear.app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ditclear.app.databinding.ActivityMainBinding;
import com.ditclear.app.list.ListActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = new MainViewModel();
        mBinding.setVm(mViewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_list){
            startActivity(new Intent(this, ListActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
