package com.kit.utils;

import android.annotation.SuppressLint;
import android.os.Build;

@SuppressLint({"AnnotateVersionCheck","ObsoleteSdkInt"})
public class ApiLevel {
    public static final boolean ATMOST_O_MR1 =
            Build.VERSION.SDK_INT <=Build.VERSION_CODES.O_MR1;

    public static final boolean ATMOST_Q =
            Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q;

    public static final boolean ATMOST_P =
            Build.VERSION.SDK_INT <= Build.VERSION_CODES.P;

    public static final boolean ATMOST_N_MR1 =
            Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1;


    public static final boolean ATMOST_N =
            Build.VERSION.SDK_INT <= Build.VERSION_CODES.N;

    public static final boolean ATLEAST_Q =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;

    public static final boolean ATLEAST_P =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    public static final boolean ATLEAST_O_MR1 =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1;

    public static final boolean ATLEAST_O =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;

    public static final boolean ATLEAST_N_MR1 =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;


    public static final boolean ATLEAST_N =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;

    public static final boolean ATLEAST_M =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;

    public static final boolean ATLEAST_LOLLIPOP_MR1 =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;

    public static final boolean ATLEAST_LOLLIPOP =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;




    public static final boolean ATLEAST_KITKAT =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;


    public static final boolean ATLEAST_JB_MR2 =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;


    public static final boolean ATLEAST_JB_MR1 =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;


    public static final boolean ATLEAST_JELLY_BEAN =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;



    public static final boolean ATLEAST_HONEYCOMB_MR2 =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;

    public static final boolean ATLEAST_HONEYCOMB =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

}
