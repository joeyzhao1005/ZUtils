package com.kit.utils;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.kit.utils.intentutils.BundleData;

/**
 * Created by Zhao on 14-10-17.
 * Fragment工具类
 */
public class FragmentUtils {
    private static final String KEY_DATA = "zhao_fragment_bundle_data";

    /**
     * v4的replace
     *
     * @param fragmentManager
     * @param idContainer
     * @param fragment
     */
    public static void replace(FragmentManager fragmentManager, int idContainer, Fragment fragment) {
        if (fragmentManager == null || fragment == null) {
            ZogUtils.printError(FragmentUtils.class, "fragmentManager or fragment is null");
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
// Replace whatever is in thefragment_container view with this fragment,
// and add the transaction to the backstack
        transaction.replace(idContainer, fragment);
        transaction.addToBackStack(null);
//提交修改
        transaction.commitAllowingStateLoss();
    }

    /**
     * v4的replace
     *
     * @param fragmentManager
     * @param idContainer
     * @param fragment
     */
    public static void replace(FragmentManager fragmentManager, int idContainer, Fragment fragment, BundleData bundleData) {
        if (fragmentManager == null || fragment == null) {
            ZogUtils.printError(FragmentUtils.class, "fragmentManager or fragment is null");
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
// Replace whatever is in thefragment_container view with this fragment,
// and add the transaction to the backstack
        pushData(fragment, bundleData);
        transaction.replace(idContainer, fragment);
        transaction.addToBackStack(null);
//提交修改
        transaction.commitAllowingStateLoss();
    }


    /**
     * @param fragmentManager
     * @param idContainer
     * @param fragment
     */
    @TargetApi(11)
    public static void replace(android.app.FragmentManager fragmentManager, int idContainer, android.app.Fragment fragment) {

        if (fragmentManager == null || fragment == null) {
            ZogUtils.printError(FragmentUtils.class, "fragmentManager or fragment is null");
            return;
        }
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
// Replace whatever is in thefragment_container view with this fragment,
// and add the transaction to the backstack
        transaction.replace(idContainer, fragment);
        transaction.addToBackStack(null);
//提交修改
        transaction.commitAllowingStateLoss();
    }


    /**
     * @param fragmentManager
     * @param idContainer
     * @param fragment
     */
    @TargetApi(11)
    public static void replace(android.app.FragmentManager fragmentManager, int idContainer, android.app.Fragment fragment, BundleData bundleData) {
        if (fragmentManager == null || fragment == null) {
            ZogUtils.printError(FragmentUtils.class, "fragmentManager or fragment is null");
            return;
        }
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
// Replace whatever is in thefragment_container view with this fragment,
// and add the transaction to the backstack
        pushData(fragment, bundleData);
        transaction.replace(idContainer, fragment);
        transaction.addToBackStack(null);
//提交修改
        transaction.commitAllowingStateLoss();
    }


    /**
     * v4的remove
     *
     * @param fragmentManager
     * @param fragment
     */
    public static void remove(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager == null || fragment == null) {
            ZogUtils.printError(FragmentUtils.class, "fragmentManager or fragment is null");
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        //提交remove
        transaction.commitAllowingStateLoss();
    }


    /**
     * v4的remove
     *
     * @param fragmentManager
     * @param fragment
     */
    @TargetApi(11)
    public static void remove(android.app.FragmentManager fragmentManager, android.app.Fragment fragment) {
        if (fragmentManager == null || fragment == null) {
            ZogUtils.printError(FragmentUtils.class, "fragmentManager or fragment is null");
            return;
        }
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
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
        if (fragment == null) {
            ZogUtils.printError(FragmentUtils.class, "fragmentManager or fragment is null");
            return;
        }
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
        if (fragment == null) {
            ZogUtils.printError(FragmentUtils.class, "fragmentManager or fragment is null");
            return;
        }
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
        if (fragment == null) {
            ZogUtils.printError(FragmentUtils.class, "fragmentManager or fragment is null");
            return null;
        }
        BundleData bundleData = null;
        try {
            String bundleDataStr = fragment.getArguments().getString(KEY_DATA);
            bundleData = GsonUtils.getObj(bundleDataStr, BundleData.class);
        } catch (Exception e) {
            ZogUtils.showException(e);
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
        if (fragment == null) {
            ZogUtils.printError(FragmentUtils.class, "fragmentManager or fragment is null");
            return null;
        }
        BundleData bundleData = null;
        try {
            String bundleDataStr = fragment.getArguments().getString(KEY_DATA);
            bundleData = GsonUtils.getObj(bundleDataStr, BundleData.class);
        } catch (Exception e) {
            ZogUtils.showException(e);
        }
        return bundleData;
    }


}
