package com.kit.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kit.utils.ResWrapper;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseV4Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BaseV4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseV4Fragment extends Fragment implements IDoFragmentInit {


    public OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseV4Fragment newInstance() {
        BaseV4Fragment fragment = new BaseV4Fragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BaseV4Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getExtra(savedInstanceState);
        if (getActivity() != null) {
            ResWrapper.getInstance().setContext(getActivity());
        }
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ResWrapper.getInstance().setContext(getActivity());
        }
    }

    @Override
    public void onDestroy() {
        destory();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = initWidget(inflater, container,
                savedInstanceState);

        initWidgetWithExtra();

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        if (getActivity() != null) {
            ResWrapper.getInstance().setContext(getActivity());
        }
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public boolean getExtra(Bundle savedInstanceState) {

        return true;
    }



    /**
     * 去网络或者本地加载数据
     */
    public boolean loadData() {
        return true;
    }

    /**
     * 初始化界面
     */
    public View initWidget(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        return null;
    }

    /**
     * 去网络或者本地加载数据
     */
    public void initWidgetWithExtra() {
    }

    public void destory() {
//        onPause();
//        onStop();
//        onDestroyView();
//        onDestroy();
//        onDetach();
    }

}
