package com.cxp.mybaselibrary;


import com.cxp.mybaselibrary.base.BaseActivity;
import com.cxp.mybaselibrary.utils.IntentUtils;

public class WelcomActivity extends BaseActivity {

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        //TODO 判断登录状态 启动广告页或引导页
        IntentUtils.startActivityAndFinish(this, MainActivity.class);
    }

    @Override
    protected boolean needRegisterEvent() {
        return false;
    }
}
