package com.kit.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.kit.app.ActivityManager;
import com.kit.app.UIHandler;

public class BaseAppCompatActivity extends AppCompatActivity implements BaseV4Fragment.OnFragmentInteractionListener{


    private boolean isShowing =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        getExtra();
        initWidget();
        loadData();
        initWidgetWithExtra();

        UIHandler.prepare();

        ActivityManager.getInstance().pushActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShowing = true;
    }


    public boolean isShowing() {
        return isShowing;
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
    public void initWindow() {
    }

    /**
     * 初始化界面
     * */
    public void initWidget() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShowing = false;
    }




    /**
     * 去网络或者本地加载数据
     * */
    public boolean loadData() {
        return true;
    }


    public void initWidgetWithExtra(){
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
        isShowing = false;

        ActivityManager.getInstance().popActivity(this);
    }
}
