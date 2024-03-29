package com.kit.app.theme

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDelegate
import com.kit.app.application.AppMaster
import com.kit.utils.ColorUtils
import com.kit.utils.ResWrapper

/**
 * Created by Zhao on 16/7/17.
 */
object ThemeEngine {
    private var themeProvider: ThemeProvider? = AppMaster.app.themeProvider

    val themeColor: Int
        get() = if (themeProvider != null) themeProvider!!.themeColor else Color.BLACK

    @Synchronized
    fun setThemeColor(themeColor: Int) {
        themeProvider?.themeColor = themeColor
    }

    val accentColor: Int
        get() = if (themeProvider != null) themeProvider!!.accentColor else Color.BLACK
    @Synchronized
    fun setAccentColor(accentColor: Int) {
        themeProvider?.accentColor = accentColor
    }

    val themeLightColor: Int
        get() = if (themeProvider != null) themeProvider!!.themeLightColor else Color.BLACK

    @Synchronized
    fun setThemeLightColor(themeLightColor: Int) {
        themeProvider?.themeLightColor = themeLightColor
    }


    val themeDarkColor: Int
        get() = if (themeProvider != null) themeProvider!!.themeDarkColor else Color.BLACK

    @Synchronized
    fun setThemeDarkColor(themeDarkColor: Int) {
        themeProvider?.themeDarkColor = themeDarkColor
    }

    val themeColorDrawable: ColorDrawable
        get() = ColorDrawable(themeColor)


    @Synchronized
    fun setThemeProvider(themeProvider: ThemeProvider) {
        this.themeProvider = themeProvider
    }

    val isLogicDarkMode: Boolean
        get() =
            when (AppMaster.applicationId) {
//                "com.zhao.ink"->{
//                    !ColorUtils.isLightColor(themeColor)
//                }
//
                else -> {
                    if (darkMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM || darkMode == AppCompatDelegate.MODE_NIGHT_AUTO_TIME) {
                        ((ResWrapper.getResources().configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
                                || !ColorUtils.isLightColor(themeColor))
                    } else {
                        isDarkMode
                    }

                }
            }


    val isDarkMode: Boolean
        get() =
            when (darkMode) {

                AppCompatDelegate.MODE_NIGHT_YES -> true

                AppCompatDelegate.MODE_NIGHT_NO -> false

                else -> false
            }

    val darkMode: Int
        get() = themeProvider?.darkMode() ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM


}
