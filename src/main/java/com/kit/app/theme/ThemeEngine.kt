package com.kit.app.theme

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDelegate
import com.kit.utils.ColorUtils

/**
 * Created by Zhao on 16/7/17.
 */
object ThemeEngine {
    private var themeProvider: ThemeProvider? = null

    val themeColor: Int
        get() = if (themeProvider != null) themeProvider!!.themeColor else Color.BLACK


    @Synchronized
    fun setThemeColor(themeColor: Int) {
        themeProvider?.themeColor = themeColor
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
            darkMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES || !ColorUtils.isLightColor(themeColor)


    val darkMode: Int
        get() = themeProvider?.darkMode() ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM


}
