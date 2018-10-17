package com.cxp.mybaselibrary.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxp.mybaselibrary.R;
import com.cxp.mybaselibrary.statusBar.StatusBarCompat;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity mContext;
    private boolean needRegisterEvent = true;
    protected Resources mResource;

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mResource = getResources();
        needRegisterEvent = needRegisterEvent();
        if (needRegisterEvent) {
            EventBus.getDefault().register(mContext);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        setBackEnableAndTitle(false, null);
        StatusBarCompat.translucentStatusBar(this, true);
        initData();
    }

    public void setBackEnableAndTitle(boolean b, String title) {
        View viewBack = findViewById(R.id.ll_back);
        if (viewBack != null && b) {
            viewBack.setVisibility(View.VISIBLE);
            viewBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else if (viewBack != null && !b) {
            viewBack.setVisibility(View.GONE);
        }
        View tvTitle = findViewById(R.id.tv_title);
        if (tvTitle != null && tvTitle instanceof TextView && !TextUtils.isEmpty(title)) {
            ((TextView) tvTitle).setText(title);
        }
    }

    public void setRightImage(boolean visible, int res, View.OnClickListener clickListener) {
        View viewById = findViewById(R.id.ll_right);
        if (viewById != null) {
            viewById.setVisibility(visible ? View.VISIBLE : View.GONE);
            if (!visible) {
                return;
            }
            ImageView ivRight = findViewById(R.id.iv_right);
            viewById.setOnClickListener(clickListener);
            if (ivRight != null) {
                ivRight.setImageResource(res);
            }
        }
    }

    public void setLeftImage(boolean visible, int res, View.OnClickListener clickListener) {
        View viewById = findViewById(R.id.ll_back);
        if (viewById != null) {
            viewById.setVisibility(visible ? View.VISIBLE : View.GONE);
            ImageView ivLeft = findViewById(R.id.iv_left);
            viewById.setOnClickListener(clickListener);
            if (ivLeft != null) {
                ivLeft.setImageResource(res);
            }
        }
    }

    public void setBackEnableAndTitle(boolean b, String title, View.OnClickListener listener) {
        View viewBack = findViewById(R.id.ll_back);
        if (viewBack != null && b) {
            viewBack.setVisibility(View.VISIBLE);
            viewBack.setOnClickListener(listener);
        } else if (viewBack != null && !b) {
            viewBack.setVisibility(View.GONE);
        }
        View tvTitle = findViewById(R.id.tv_title);
        if (tvTitle != null && tvTitle instanceof TextView && !TextUtils.isEmpty(title)) {
            ((TextView) tvTitle).setText(title);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (needRegisterEvent) {
            EventBus.getDefault().unregister(mContext);
        }
    }

    /**
     * 是否需要注册Eventbus事件
     *
     * @return
     */
    protected abstract boolean needRegisterEvent();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        for (OnNewIntentListener listener : listeners) {
            listener.onNewIntent(intent);
        }
    }

    ArrayList<OnNewIntentListener> listeners = new ArrayList<>();
    ArrayList<OnActivityListener> activityListeners = new ArrayList<>();

    public interface OnNewIntentListener {
        public void onNewIntent(Intent intent);
    }

    public void addOnNewIntentListener(OnNewIntentListener l) {
        listeners.add(l);
    }

    public interface OnActivityListener {
        public void onActivityResults(int requestCode, int resultCode, Intent data);
    }

    public void addOnActivityListener(OnActivityListener l) {
        activityListeners.add(l);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (OnActivityListener activityListener : activityListeners) {
            activityListener.onActivityResults(requestCode, resultCode, data);
        }
    }

    /*public void changeAppLanguage() {
        String languageType = SPUtils.getLanguageType(mContext);
        // 本地语言设置
        Locale myLocale = null;
        switch (languageType) {
            case Constants.LANGUAGE_CN:
                myLocale = Locale.CHINESE;
                break;
            case Constants.LANGUAGE_EN:
                myLocale = Locale.ENGLISH;
                break;
            case Constants.LANGUAGE_HK:
            case Constants.LANGUAGE_TW:
                myLocale = Locale.TAIWAN;
                break;
        }
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }*/

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void onLanguageChangeEvent(OnLanguageChangeEvent event) {
        if (event != null) {
            changeAppLanguage();
            recreate();//刷新界面
        }
    }*/
}
