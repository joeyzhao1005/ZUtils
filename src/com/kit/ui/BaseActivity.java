package com.kit.ui;

import android.os.Bundle;

import com.kit.app.resouce.DrawableId;
import com.kit.utils.ActionBarUtils;
import com.kit.utils.ResourceUtils;

public abstract class BaseActivity extends BaseAppCompatActivity  implements BaseV4Fragment.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
    }

    public void initTheme() {
        ActionBarUtils.setHomeActionBar(this, ResourceUtils.getDrawableId(this, DrawableId.ic_back));
    }

}

