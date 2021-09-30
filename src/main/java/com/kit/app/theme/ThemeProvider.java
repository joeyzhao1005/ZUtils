package com.kit.app.theme;

/**
 * @author joeyzhao
 */
public interface ThemeProvider {


    int getThemeColor();

    /**
     * @param themeColor
     */
    void setThemeColor(int themeColor);

    int getAccentColor();

    /**
     * @param themeColor
     */
    void setAccentColor(int themeColor);

    int getThemeLightColor();

    void setThemeLightColor(int themeLightColor);

    int getThemeDarkColor();

    void setThemeDarkColor(int themeDarkColor);


    int darkMode();

}
