package com.cxp.mybaselibrary.utils;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * 常量类
 */
public class Constant {
    public static final boolean ISDEBUG = true;
    public static final String PROJECTNAME = "xx";
    public static String IMEI;
    public static boolean isNetConnect;
    public static boolean CANCLEFORNOW = false;
    public static boolean ISUPDATING = false;
    public static String UID = "uid";
    public static String PLATE_NUMBER = "plate_number";
    public static final String LANGUAGE = "language";
    public static final String KEY_TYPE_LOGIN = "login_type";//登录类型
    public static final int TYPE_PHONE_NUMBER = 1;//手机号登录
    public static final int TYPE_QQ = 2;//QQ号登录
    public static final int TYPE_WEIBO = 3;//微博登录
    public static final int TYPE_WECHAT = 4;//微信登录

    public static final String FLAG_LOGIN = "flag_login";
    public static final int FLAG_LOGIN_PASSENGER = 0;
    public static final int FLAG_LOGIN_DRIVER = 1;

    public static abstract class MyClick extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }
}
