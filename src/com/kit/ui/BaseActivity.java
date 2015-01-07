package com.kit.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.kit.app.ActivityManager;

public abstract class BaseActivity extends Activity implements IDoActivityInit {

	public Context mContext;

	protected void onResume() {
		super.onResume();
	}

	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;

		getExtra();
		initWidget();
		loadData();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().popActivity(this);
    }
}
