package com.kit.utils;

import android.content.Context;
import android.text.ClipboardManager;

import com.kit.app.application.AppMaster;

/**
 * Created by Zhao on 14/11/17.
 */
public class ClipboardUtils {
    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(String content) {
        if (ApiLevel.ATLEAST_HONEYCOMB) {
            final android.content.ClipboardManager clipboardManager =
                    (android.content.ClipboardManager) AppMaster.getInstance().getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
            final android.content.ClipData clipData = android.content.ClipData
                    .newPlainText(content, content);

            if (clipboardManager != null) {
                clipboardManager.setPrimaryClip(clipData);
            }
        } else {
            final android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) AppMaster.getInstance().getAppContext()
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                clipboardManager.setText(content);
            }
        }
    }

    /**
     * 实现粘贴功能
     *
     * @return
     */
    public static String paste() {
        if (ApiLevel.ATLEAST_HONEYCOMB) {
            final android.content.ClipboardManager clipboardManager =
                    (android.content.ClipboardManager) AppMaster.getInstance().getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null && clipboardManager.getPrimaryClip() != null && clipboardManager.getPrimaryClip().getItemCount() > 0) {
                return clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
            }
        } else {
            final android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) AppMaster.getInstance().getAppContext()
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                return clipboardManager.getText().toString().trim();
            }
        }

        return "";
    }
}
