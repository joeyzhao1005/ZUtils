package com.kit.utils;

import android.annotation.TargetApi;
import android.os.Bundle;
import androidx.annotation.AnimRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kit.utils.intent.BundleData;
import com.kit.utils.log.Zog;

/**
 * Created by Zhao on 14-10-17.
 */
public class FragmentUtils {
    private static final String KEY_DATA = "zhao_fragment_bundle_data";

    /**
     * v4的replace
     *
     * @param fragmentManager
     * @param idContainer
     * @param fragement
     */
    public static void replace(FragmentManager fragmentManager, int idContainer, Fragment fragement) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(idContainer, fragement);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }


    public static void replace(FragmentManager fragmentManager, int idContainer, Fragment fragement,String name, @AnimRes int enter,
                               @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(enter, exit, popEnter, popExit);
        transaction.replace(idContainer, fragement);
        transaction.addToBackStack(name);
        transaction.commitAllowingStateLoss();
    }

    public static void replace(FragmentManager fragmentManager, int idContainer, Fragment fragement, @AnimRes int enter,
                               @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(enter, exit, popEnter, popExit);
        transaction.replace(idContainer, fragement);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }


    /**
     * v4的replace
     *
     * @param fragmentManager
     * @param idContainer
     * @param fragement
     */
    public static void replace(FragmentManager fragmentManager, int idContainer, Fragment fragement, BundleData bundleData) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
// Replace whatever is in thefragment_container view with this fragment,
// and add the transaction to the backstack
        pushData(fragement, bundleData);
        transaction.replace(idContainer, fragement);
        transaction.addToBackStack(null);
//提交修改
        transaction.commitAllowingStateLoss();
    }


    /**
     * @param fragmentManager
     * @param idContainer
     * @param fragement
     */
    @TargetApi(11)
    public static void replace(android.app.FragmentManager fragmentManager, int idContainer, android.app.Fragment fragement) {
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
// Replace whatever is in thefragment_container view with this fragment,
// and add the transaction to the backstack
        transaction.replace(idContainer, fragement);
        transaction.addToBackStack(null);
//提交修改
        transaction.commitAllowingStateLoss();
    }


    /**
     * @param fragmentManager
     * @param idContainer
     * @param fragement
     */
    @TargetApi(11)
    public static void replace(android.app.FragmentManager fragmentManager, int idContainer, android.app.Fragment fragement, BundleData bundleData) {
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
// Replace whatever is in thefragment_container view with this fragment,
// and add the transaction to the backstack
        pushData(fragement, bundleData);
        transaction.replace(idContainer, fragement);
        transaction.addToBackStack(null);
//提交修改
        transaction.commitAllowingStateLoss();
    }


    /**
     * v4的remove
     *
     * @param fragmentManager
     * @param fragement
     */
    public static void remove(FragmentManager fragmentManager, Fragment fragement) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragement);
        //提交remove
        transaction.commitAllowingStateLoss();
    }


    /**
     * v4的remove
     *
     * @param fragmentManager
     * @param fragement
     */
    @TargetApi(11)
    public static void remove(android.app.FragmentManager fragmentManager, android.app.Fragment fragement) {
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragement);
        //提交remove
        transaction.commitAllowingStateLoss();
    }


    /**
     * 压入数据
     *
     * @param fragment
     * @param data
     */
    public static void pushData(Fragment fragment, BundleData data) {
        Bundle bundle = new Bundle();
        String value = GsonUtils.toJson(data);
        bundle.putString(KEY_DATA, value);
        fragment.setArguments(bundle);
    }


    /**
     * 压入数据
     *
     * @param fragment
     * @param data
     */
    @TargetApi(11)
    public static void pushData(android.app.Fragment fragment, BundleData data) {
        Bundle bundle = new Bundle();
        String value = GsonUtils.toJson(data);
        bundle.putString(KEY_DATA, value);
        fragment.setArguments(bundle);
    }


    /**
     * 获取传过去的值
     *
     * @return
     */
    public static BundleData getData(Fragment fragment) {
        BundleData bundleData = null;
        try {
            String bundleDataStr = fragment.getArguments().getString(KEY_DATA);
            bundleData = GsonUtils.getObj(bundleDataStr, BundleData.class);
        } catch (Exception e) {
            Zog.showException(e);
        }
        return bundleData;
    }


    /**
     * 获取传过去的值
     *
     * @return
     */
    @TargetApi(11)
    public static BundleData getData(android.app.Fragment fragment) {
        BundleData bundleData = null;
        try {
            String bundleDataStr = fragment.getArguments().getString(KEY_DATA);
            bundleData = GsonUtils.getObj(bundleDataStr, BundleData.class);
        } catch (Exception e) {
            Zog.showException(e);
        }
        return bundleData;
    }


    /**
     * v4的replace
     *
     * @param fragement
     */
    public static boolean isShowing(Fragment fragement) {
        return (fragement != null && fragement.isVisible() && !fragement.isRemoving() && !fragement.isDetached() && fragement.isResumed());
    }

}
