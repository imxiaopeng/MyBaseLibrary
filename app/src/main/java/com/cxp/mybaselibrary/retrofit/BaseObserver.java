package com.cxp.mybaselibrary.retrofit;

import android.accounts.NetworkErrorException;
import android.app.Activity;

import com.cxp.mybaselibrary.R;
import com.cxp.mybaselibrary.base.BaseEntity;
import com.cxp.mybaselibrary.utils.LoadingDialogUtils;
import com.cxp.mybaselibrary.utils.UIUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/20.
 */

public abstract class BaseObserver<T extends BaseEntity> implements Observer<T> {

    protected Activity mActivity;
    private boolean isShowDialog;//是否要显示对话框
    private boolean isCancel; //按返回键能否取消对话框
    private int retryCount;

    public BaseObserver(Activity cxt, boolean isShowDialog, boolean isCancel) {
        this.mActivity = cxt;
        this.isShowDialog = isShowDialog;
        this.isCancel = isCancel;
    }

    public BaseObserver(Activity cxt, boolean isShowDialog) {
        this.mActivity = cxt;
        this.isShowDialog = isShowDialog;
        this.isCancel = true;
    }

    public BaseObserver(Activity cxt) {
        this.mActivity = cxt;
        this.isShowDialog = true;
        this.isCancel = true;
    }


    protected <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();

    }

    @Override
    public void onNext(T tBaseEntity) {
        onRequestEnd();
        if (tBaseEntity.isSuccess()) {
            try {
                onSuccees(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onCodeError(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            int code = ((HttpException) e).code();
            if (code == 401) {
               /* MyApplication.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SPUtils.clear(mActivity);
                        UIUtils.showToastShort(mActivity, "请重新登录");
                        mActivity.startActivity(new Intent(mActivity, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                });*/
                return;
            }
        }
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException || e instanceof IOException) {
                onFailure(e, true);
                UIUtils.showToastShort(mActivity, R.string.net_error);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(T t);

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(T t) {
        try {
            String message = t.Msg;
            UIUtils.showToastShort(mActivity, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError);

    protected void onRequestStart() {
        try {
            if (isShowDialog && mActivity != null && !mActivity.isFinishing()) {
                showProgressDialog();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onRequestEnd() {
        if (mActivity == null || mActivity.isFinishing())
            return;
        try {
            closeProgressDialog();

        } catch (Exception e) {
        }

    }

    private void showProgressDialog() {
        LoadingDialogUtils.showLoading(mActivity, "", isCancel);
        //  loadingDialog = LoadingDialogUtils.createLoadingDialog(mActivity, "", isCancel);
        // loadingDialog.show();
    }

    private void closeProgressDialog() {
        /*if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }*/
        LoadingDialogUtils.stopLoading();
    }
}

