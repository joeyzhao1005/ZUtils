package com.kit.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.kit.app.ActivityManager;

public class BaseActionBarActivity extends ActionBarActivity implements BaseV4Fragment.OnFragmentInteractionListener,IDoActivityInit{


//    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mContext = this;

        getExtra();
        initWidget();
        loadData();
        initWidgetWithData();

        ActivityManager.getInstance().pushActivity(this);


    }

    /**
     * 获得上一个Activity传过来的值
     * */
    public boolean getExtra() {

        return true;
    }

    /**
     * 初始化界面
     * */
    public boolean initWidget() {
        return true;
    }

    /**
     * 去网络或者本地加载数据
     * */
    public boolean loadData() {
        return true;
    }


    public boolean initWidgetWithData(){
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//
//        return super.onOptionsItemSelected(item);
//    }
//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ActivityManager.getInstance().popActivity(this);
    }
}
