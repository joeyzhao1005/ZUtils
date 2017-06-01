package com.kit.model.shortcut;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by Zhao on 2017/5/31.
 */

public class ZShortcutInfo implements Cloneable {


    public ComponentName getComponentName() {
        return componentName;
    }

    public void setComponentName(ComponentName componentName) {
        this.componentName = componentName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getShortcutId() {
        return shortcutId;
    }

    public void setShortcutId(String shortcutId) {
        this.shortcutId = shortcutId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getShortcutShortLabel() {
        return shortcutShortLabel;
    }

    public void setShortcutShortLabel(int shortcutShortLabel) {
        this.shortcutShortLabel = shortcutShortLabel;
    }


    public int getShortcutLongLabel() {
        return shortcutLongLabel;
    }

    public void setShortcutLongLabel(int shortcutLongLabel) {
        this.shortcutLongLabel = shortcutLongLabel;
    }

    public int getShortcutDisabledMessage() {
        return shortcutDisabledMessage;
    }

    public void setShortcutDisabledMessage(int shortcutDisabledMessage) {
        this.shortcutDisabledMessage = shortcutDisabledMessage;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public String getDisabledMessage() {
        return disabledMessage;
    }

    public void setDisabledMessage(String disabledMessage) {
        this.disabledMessage = disabledMessage;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public String getLongLabel() {
        return longLabel;
    }

    public void setLongLabel(String longLabel) {
        this.longLabel = longLabel;
    }



    ComponentName componentName;


    String shortcutId;
    boolean enabled;

    Drawable iconDrawable;
    String shortLabel;
    String longLabel;
    String disabledMessage;
    int icon;
    int shortcutShortLabel;
    int shortcutLongLabel;
    int shortcutDisabledMessage;


    String action;
    String targetPackage;
    String targetClass;

    ArrayList<String> categories;
    Extra extra;

    String data;

    @Override
    public ZShortcutInfo clone() throws CloneNotSupportedException {
        ZShortcutInfo zShortcutInfo = new ZShortcutInfo();
        zShortcutInfo.setComponentName(this.componentName);
        zShortcutInfo.setShortcutId(this.shortcutId);
        zShortcutInfo.setEnabled(this.enabled);
        zShortcutInfo.setIconDrawable(this.iconDrawable);
        zShortcutInfo.setShortLabel(this.shortLabel);
        zShortcutInfo.setLongLabel(this.longLabel);
        zShortcutInfo.setDisabledMessage(this.disabledMessage);
        zShortcutInfo.setIcon(this.icon);
        zShortcutInfo.setShortcutShortLabel(this.shortcutShortLabel);
        zShortcutInfo.setShortcutLongLabel(this.shortcutLongLabel);
        zShortcutInfo.setShortcutDisabledMessage(this.shortcutDisabledMessage);
        zShortcutInfo.setAction(this.action);
        zShortcutInfo.setTargetPackage(this.targetPackage);
        zShortcutInfo.setTargetClass(this.targetClass);
        zShortcutInfo.setCategories(this.categories);
        zShortcutInfo.setExtra(this.extra);
        zShortcutInfo.setData(this.data);
        return zShortcutInfo;
    }
}
