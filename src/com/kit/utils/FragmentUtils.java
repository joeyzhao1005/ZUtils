package com.kit.utils;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Zhao on 14-10-17.
 */
public class FragmentUtils {

    /**
     * v4的replace
     *
     * @param fragmentManager
     * @param idContainer
     * @param fragement
     */
    public static void replace(FragmentManager fragmentManager, int idContainer, Fragment fragement) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
// Replace whatever is in thefragment_container view with this fragment,
// and add the transaction to the backstack
        transaction.replace(idContainer, fragement);
        transaction.addToBackStack(null);
//提交修改
        transaction.commit();
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
        transaction.commit();
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
        transaction.commit();
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
        transaction.commit();
    }

}
