package com.cxp.mybaselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * toast工具类
 */
public class UIUtils {
    static Toast toast = null;
    // 适配低版本手机
    /**
     * Network type is unknown
     */
    private static final int NETWORK_TYPE_UNKNOWN = 0;
    /**
     * Current network is GPRS
     */
    private static final int NETWORK_TYPE_GPRS = 1;
    /**
     * Current network is EDGE
     */
    private static final int NETWORK_TYPE_EDGE = 2;
    /**
     * Current network is UMTS
     */
    private static final int NETWORK_TYPE_UMTS = 3;
    /**
     * Current network is CDMA: Either IS95A or IS95B
     */
    private static final int NETWORK_TYPE_CDMA = 4;
    /**
     * Current network is EVDO revision 0
     */
    private static final int NETWORK_TYPE_EVDO_0 = 5;
    /**
     * Current network is EVDO revision A
     */
    private static final int NETWORK_TYPE_EVDO_A = 6;
    /**
     * Current network is 1xRTT
     */
    private static final int NETWORK_TYPE_1xRTT = 7;
    /**
     * Current network is HSDPA
     */
    private static final int NETWORK_TYPE_HSDPA = 8;
    /**
     * Current network is HSUPA
     */
    private static final int NETWORK_TYPE_HSUPA = 9;
    /**
     * Current network is HSPA
     */
    private static final int NETWORK_TYPE_HSPA = 10;
    /**
     * Current network is iDen
     */
    private static final int NETWORK_TYPE_IDEN = 11;
    /**
     * Current network is EVDO revision B
     */
    private static final int NETWORK_TYPE_EVDO_B = 12;
    /**
     * Current network is LTE
     */
    private static final int NETWORK_TYPE_LTE = 13;
    /**
     * Current network is eHRPD
     */
    private static final int NETWORK_TYPE_EHRPD = 14;
    /**
     * Current network is HSPA+
     */
    private static final int NETWORK_TYPE_HSPAP = 15;
    private static final int NETWORK_TYPE_UNAVAILABLE = -1;
    // private static final int NETWORK_TYPE_MOBILE = -100;
    private static final int NETWORK_TYPE_WIFI = -101;

    private static final int NETWORK_CLASS_WIFI = -101;
    private static final int NETWORK_CLASS_UNAVAILABLE = -1;
    /**
     * Unknown network class.
     */
    private static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * Class of broadly defined "2G" networks.
     */
    private static final int NETWORK_CLASS_2_G = 1;
    /**
     * Class of broadly defined "3G" networks.
     */
    private static final int NETWORK_CLASS_3_G = 2;
    /**
     * Class of broadly defined "4G" networks.
     */
    private static final int NETWORK_CLASS_4_G = 3;

    public static void showToast(Context context, String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), msg, duration);
        }
        if (!TextUtils.isEmpty(msg)) {
            toast.setText(msg);
            toast.show();
        }
    }

    public static void showToastShort(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        }
        if (!TextUtils.isEmpty(msg)) {
            toast.setText(msg);
            toast.show();
        }
    }


    public static void showToastLong(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
        }
        if (!TextUtils.isEmpty(msg)) {
            toast.setText(msg);
            toast.show();
        }
    }

    public static void showToastShort(Context context, int resId) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
        }
        if (resId != -1) {
            toast.setText(resId);
            toast.show();
        }
    }

    public static void showToastLong(Context context, int resId) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_LONG);
        }
        if (resId != -1) {
            toast.setText(resId);
            toast.show();
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9

------------------------------------------------
        13(老)号段：130、131、132、133、134、135、136、137、138、139
        14(新)号段：145、147
        15(新)号段：150、151、152、153、154、155、156、157、158、159
        17(新)号段：170、171、173、175、176、177、178
        18(3G)号段：180、181、182、183、184、185、186、187、188、189
        新添号码段199 198 166
    */
        String telRegex = "[1][3456789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 1-透明  0- 黑色
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 获取到期时间 天 时 分
     *
     * @param time 到期日期
     */
    public static String getGmtExpired(long time) {
        String date;
        long l = time - System.currentTimeMillis();
        if (l / 1000 / 60 < 60) //小于 1小时
            date = l / 1000 / 60 + "分";
        else {
            date = l / 1000 / 60 / 60 + "时";
//            if (l / 1000 / 60 / 60 < 24)d
//                date = l / 1000 / 60 / 60 + "小时";
//            else {
//                date = l / 1000 / 60 / 60 / 24 + "天";
//            }
        }
        return date;
    }

    /**
     * 获取到期时间 天 时 分
     *
     * @param time 到期日期
     */
    public static String getGmtExpiredStr(long time) {
        String date;
        long l = time - System.currentTimeMillis();
        if (l / 1000 / 60 < 60) //小于 1小时
            date = l / 1000 / 60 + "分钟";
        else {
            date = l / 1000 / 60 / 60 + "小时";
//            if (l / 1000 / 60 / 60 < 24)d
//                date = l / 1000 / 60 / 60 + "小时";
//            else {
//                date = l / 1000 / 60 / 60 / 24 + "天";
//            }
        }
        return date;
    }

    /**
     * 时间转换
     *
     * @param l long类型的时间
     * @return 字符串时间 MM月dd日 HH:mm
     */
    public static String timeTransformation(long l) {
        Date date = new Date(l);
        return new SimpleDateFormat("MM-dd HH:mm").format(date);
    }


    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap decodeUriAsBitmapFromNet(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            // 主线程不允许 访问网络
            // 加入下面代码 就可以了
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    /*public static int getVIPImageRes(String vip) {
        int res;
        switch (vip) {
            case "VIP0":
                res = R.mipmap.homepage_icon_v0;
                break;
            case "VIP1":
                res = R.mipmap.homepage_icon_v1;
                break;
            case "VIP2":
                res = R.mipmap.homepage_icon_v2;
                break;
            case "VIP3":
                res = R.mipmap.homepage_icon_v3;
                break;
            case "VIP4":
                res = R.mipmap.homepage_icon_v4;
                break;
            case "VIP5":
                res = R.mipmap.homepage_icon_v5;
                break;
            case "VIP6":
                res = R.mipmap.homepage_icon_v6;
                break;
            case "VIP7":
                res = R.mipmap.homepage_icon_v7;
                break;
            case "VIP8":
                res = R.mipmap.homepage_icon_v8;
                break;
            case "VIP9":
                res = R.mipmap.homepage_icon_v9;
                break;
            case "VIP10":
                res = R.mipmap.homepage_icon_v10;
                break;
            case "VIP11":
                res = R.mipmap.homepage_icon_v11;
                break;
            case "VIP12":
                res = R.mipmap.homepage_icon_v12;
                break;
            default:
                res = R.mipmap.homepage_icon_v0;
                break;
        }
        return res;
    }*/

    /**
     * @param time 格式化成 MM-dd HH:mm:ss
     * @return
     */
    public static String formatTime(Long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String formatTimeMMDD(Long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * @param time 格式化成 yyyy-MM-dd HH:mm
     * @return
     */
    public static String formatTime1(Long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * @param time 格式化成 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatTime2(Long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * @param time 格式化成 MM-dd HH:mm
     * @return
     */
    public static String formatTime2HHmm(Long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * @param time 格式化成 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatTime4(Long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    /* */

    /**
     * 获取用户代理设置信息
     *
     * @param context
     * @return
     *//*
    public static String getCurrentUserAgent(Context context) {
        String versionName = getVersionName(context);//app版本名称
        String model = "Android";//手机型号
        String id = Build.ID;//编译代号
        String version = Build.VERSION.RELEASE;//设备android版本号
        if (TextUtils.isEmpty(version)) {
            version = "4.4";
        }
        if (TextUtils.isEmpty(versionName)) {
            versionName = "1.0";
        }
        // add the model for the release build
        if ("REL".equals(Build.VERSION.CODENAME)) {
            model = Build.MODEL;
        }
        String userAgent = String.format(context.getString(R.string.user_agent), versionName, version, model, id, getNetWorkType(context));
        Logger.e(userAgent+"");
        return userAgent;
    }*/

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 解码邀请码，返回用户ID
     *
     * @param recommendCode
     * @return
     */
    public static int codeDecode(String recommendCode) {
        try {
            return (Integer.parseInt(recommendCode, 36) - 2145675) / 7;
        } catch (Exception e) {

            return 0;
        }
    }

    private static int getNetworkClass(Context context) {
        int networkType = NETWORK_TYPE_UNKNOWN;
        try {
            final NetworkInfo network = ((ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();
            if (network != null && network.isAvailable()
                    && network.isConnected()) {
                int type = network.getType();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    networkType = NETWORK_TYPE_WIFI;
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                            Context.TELEPHONY_SERVICE);
                    networkType = telephonyManager.getNetworkType();
                }
            } else {
                networkType = NETWORK_TYPE_UNAVAILABLE;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getNetworkClassByType(networkType);

    }

    private static int getNetworkClassByType(int networkType) {
        switch (networkType) {
            case NETWORK_TYPE_UNAVAILABLE:
                return NETWORK_CLASS_UNAVAILABLE;
            case NETWORK_TYPE_WIFI:
                return NETWORK_CLASS_WIFI;
            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_CDMA:
            case NETWORK_TYPE_1xRTT:
            case NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_EVDO_0:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSUPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
                return NETWORK_CLASS_3_G;
            case NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    /**
     * 获取网络类型
     *
     * @return
     */
    public static String getNetWorkType(Context context) {
        int networkClass = getNetworkClass(context);
        String type = "未知";
        switch (networkClass) {
            case NETWORK_CLASS_UNAVAILABLE:
                type = "无";
                break;
            case NETWORK_CLASS_WIFI:
                type = "WIFI";
                break;
            case NETWORK_CLASS_2_G:
                type = "2G";
                break;
            case NETWORK_CLASS_3_G:
                type = "3G";
                break;
            case NETWORK_CLASS_4_G:
                type = "4G";
                break;
            case NETWORK_CLASS_UNKNOWN:
                type = "未知";
                break;
        }
        Logger.e("当前网络状态：" + type);
        return type;
    }

    public static boolean isMatchedId(String id) {
        if (TextUtils.isEmpty(id)) {
            return false;
        }
        String rex = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)";
        return id.matches(rex);
    }

    public static void setTextMarquee(TextView textView) {
        if (textView != null) {
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setSingleLine(true);
            textView.setSelected(true);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }
    }

    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public interface OnDeviceIdGettedListener {
        void onDeviceIdGetted(String id);
    }

}
