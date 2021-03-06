package io.innofang.basedemo.module;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.innofang.basedemo.R;
import io.innofang.basedemo.base.BaseFragment;
import io.innofang.basedemo.utils.Toasts;

/**
 * Author: Inno Fang
 * Description:
 */


public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {

        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void createView(View view, Bundle savedInstanceState) {
        TextView text = (TextView) find(R.id.text_view);
        Toasts.showToast(text.getText().toString());
    }

}
