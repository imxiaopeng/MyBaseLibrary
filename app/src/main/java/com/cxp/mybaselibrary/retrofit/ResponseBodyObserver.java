package com.cxp.mybaselibrary.retrofit;

import android.accounts.NetworkErrorException;
import android.app.Activity;

import com.cxp.mybaselibrary.utils.LoadingDialogUtils;

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
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/1/20.
 */

public abstract class ResponseBodyObserver<T extends ResponseBody> implements Observer<ResponseBody> {

    protected Activity mActivity;
    private boolean isShowDialog;//是否要显示对话框
    private boolean isCancel; //按返回键能否取消对话框

    public ResponseBodyObserver(Activity cxt, boolean isShowDialog, boolean isCancel) {
        this.mActivity = cxt;
        this.isShowDialog = isShowDialog;
        this.isCancel = isCancel;
    }

    public ResponseBodyObserver(Activity cxt, boolean isShowDialog) {
        this.mActivity = cxt;
        this.isShowDialog = isShowDialog;
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
    public void onNext(ResponseBody tBaseEntity) {
        onRequestEnd();
        if (tBaseEntity == null) {
            onError(new Exception("服务器异常"));
        } else {
            try {
                onSuccees(tBaseEntity.string());
            } catch (Exception e) {
                e.printStackTrace();
                onError(e);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
//        Log.w(TAG, "onError: ", );这里可以打印错误信息
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
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
     * @throws Exception
     */
    protected abstract void onSuccees(String json) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(T t) throws Exception {
    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart() {
        if (isShowDialog && mActivity != null) {
            showProgressDialog();
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

