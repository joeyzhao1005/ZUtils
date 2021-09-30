package com.kit.config

import android.util.Log
import com.kit.app.application.AppMaster
import com.kit.config.AppConfig.IAppConfig
import com.kit.config.AppConfig

object AppConfig {
    @JvmStatic
    val appConfig: IAppConfig = AppMaster.appConfig

    interface IAppConfig {
        val isShowLog: Boolean
        val isShowException: Boolean
        fun getFilesDir(type: String?): String?
        val cacheDir: String?
        val cacheDataDir: String?
        val cacheImageDir: String?
        val theme: String?
    }
}