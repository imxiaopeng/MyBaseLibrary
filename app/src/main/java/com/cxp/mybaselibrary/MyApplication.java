package com.cxp.mybaselibrary;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.cxp.mybaselibrary.netstate.NetChangeObserver;
import com.cxp.mybaselibrary.netstate.NetWorkUtil;
import com.cxp.mybaselibrary.netstate.NetworkStateReceiver;
import com.cxp.mybaselibrary.utils.Constant;
import com.cxp.mybaselibrary.utils.CrashHandler;
import com.cxp.mybaselibrary.utils.UIUtils;

import static com.cxp.mybaselibrary.utils.Constant.ISDEBUG;


/**
 * Created by Administrator on 2017/5/30.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    public static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mHandler = new Handler();
        initNetState();//初始化网络
        if (!ISDEBUG) {
            CrashHandler.getInstance().init(this);
        }
//        // 以下用来捕获程序崩溃异常
//        Intent intent = new Intent();
//        intent.setClassName("vip.yile.dubaita", "vip.yile.dubaita.activity.MainActivity");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        //     vip.yile.dubaita.CrashHandler.getInstance().init(this,crashUploader,restartIntent);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        /*
        如果App是多进程架构的话，Application会执行多次，这个优化过程无需执行多次；
        而在SDK版本5.0及以上，默认使用ART虚拟机，与Dalvik的区别在于安装时已经将全部的Class.dex转换为了oat文件，
        优化过程在安装时已经完成；因此无需执行。
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            MultiDex.install(this);//方法过多分区
        }
    }

    private void initNetState() {
        NetworkStateReceiver.registerNetworkStateReceiver(instance);
        NetworkStateReceiver.registerObserver(observer);
    }

    public void unegisterNetworkStateReceiver() {
        NetworkStateReceiver.removeRegisterObserver(observer);
        NetworkStateReceiver.unRegisterNetworkStateReceiver(instance);
    }

    private NetChangeObserver observer = new NetChangeObserver() {
        @Override
        public void onConnect(NetWorkUtil.NetType type) {
            super.onConnect(type);
            Constant.isNetConnect = true;
            //  EventBus.getDefault().post(new EventBean("load"));
        }

        @Override
        public void onDisConnect() {
            super.onDisConnect();
            Constant.isNetConnect = false;
            UIUtils.showToastShort(MyApplication.getInstance(), getResources().getString(R.string.net_error));
        }
    };

    public static MyApplication getInstance() {
        return instance;
    }
}
