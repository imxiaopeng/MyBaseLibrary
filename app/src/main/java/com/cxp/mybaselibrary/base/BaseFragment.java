package com.cxp.mybaselibrary.base;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxp.mybaselibrary.R;
import com.cxp.mybaselibrary.utils.UIUtils;


/**
 * Created by Administrator on 2017/5/22.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected Resources mResources;

    public abstract String getTitle(Resources mResources);

    public String title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        mResources = mActivity.getResources();
        getTitle(mResources);
        return initView(inflater, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    /**
     * 初始化View
     * <p>
     * /**
     * 初始化数据
     *
     * @param inflater
     * @param container
     * @return
     */
    public abstract View initView(LayoutInflater inflater, ViewGroup container);

    public abstract void initData();

    /**
     * View被销毁
     */
    public abstract void onViewDestroy();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onViewDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected View getDefault(final String text) {
        View view = null;
        try {
            view = View.inflate(getActivity(), R.layout.fragment_placeholder, null);
            TextView textView = (TextView) view.findViewById(R.id.tv);
            textView.setText(text);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIUtils.showToastShort(mActivity, text);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}

