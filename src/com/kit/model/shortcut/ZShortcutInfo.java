package com.kit.model.shortcut;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;

import com.google.gson.annotations.Expose;
import com.kit.utils.SetUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Zhao on 2017/5/31.
 */

public class ZShortcutInfo implements Cloneable {


    public ComponentName getComponentName() {
        if (componentName == null) {
            try {
                componentName = new ComponentName(targetPackage, targetClass);
            } catch (Exception e) {
            }
        }
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

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public Set<Extra> getExtras() {
        return extras;
    }


    public void setExtras(Set<Extra> extras) {
        this.extras = extras;
    }

    public void putExtra(Extra extra) {
        if (extra == null)
            return;

        if (this.extras == null) {
            this.extras = new HashSet<>();
        }
        this.extras.add(extra);
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

    public boolean isRootLaunch() {
        return isRootLaunch;
    }

    public void setRootLaunch(boolean rootLaunch) {
        isRootLaunch = rootLaunch;
    }


    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getIntentStr() {
        return intentStr;
    }

    public void setIntentStr(String intentStr) {
        this.intentStr = intentStr;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public long getSerialNumberForUser() {
        return serialNumberForUser;
    }

    public void setSerialNumberForUser(long serialNumberForUser) {
        this.serialNumberForUser = serialNumberForUser;
    }

    long serialNumberForUser;


    @Expose(serialize = false)
    Intent intent;

    String intentStr;

    boolean isPinned;
    boolean isDynamic;

    int rank;

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

    Set<String> categories;
    Set<Extra> extras;

    String data;

    boolean isRootLaunch;

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
        zShortcutInfo.setExtras(this.extras);
        zShortcutInfo.setData(this.data);
        zShortcutInfo.setRootLaunch(this.isRootLaunch);
        zShortcutInfo.setIntentStr(this.intentStr);
        zShortcutInfo.setIntent(this.intent);
        zShortcutInfo.setDynamic(this.isDynamic);
        zShortcutInfo.setPinned(this.isPinned);
        zShortcutInfo.setRank(this.rank);
        zShortcutInfo.setSerialNumberForUser(this.serialNumberForUser);
        return zShortcutInfo;
    }
}
