/*
 * Copyright (c) 2018.
 * Author：Zhao
 * Email：joeyzhao1005@gmail.com
 */
package com.kit.app.application

import android.content.Context
import com.kit.app.application.AppMaster

/**
 * Created by joeyzhao on 2018/3/16.
 */
object AppMaster : IApp {
    override fun restartApp() {
        app.restartApp()
    }

    override fun isDebug(): Boolean {
        return app.isDebug
    }

    override fun getAppName(): String {
        return app.appName
    }

    override fun getAppContext(): Context {
        return app.appContext
    }

    override fun getApplicationId(): String {
        return app.applicationId
    }

    override fun getFlavor(): String {
        return app.flavor
    }

    override fun getVersionName(): String {
        return app.versionName
    }

    override fun getVersionCode(): Long {
        return app.versionCode
    }

    lateinit var app: IApp

}