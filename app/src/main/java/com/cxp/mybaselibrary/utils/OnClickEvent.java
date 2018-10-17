package com.cxp.mybaselibrary.utils;

import android.view.View;

/**
 * Created by Administrator on 2017/1/13.
 */
public abstract class OnClickEvent implements View.OnClickListener {
    private long lastTime;

    public abstract void singleClick(View v);

    private long delay;

    public OnClickEvent(long delay) {
        this.delay = delay;
    }

    public OnClickEvent() {
        this.delay = 200;
    }

    @Override
    public void onClick(View v) {
        if (onMoreClick(v)) {
            return;
        }
        singleClick(v);
    }

    public boolean onMoreClick(View v) {
        boolean flag = false;
        long time = System.currentTimeMillis() - lastTime;
        if (time < delay) {
            flag = true;
        }
        lastTime = System.currentTimeMillis();
        return flag;
    }
}

