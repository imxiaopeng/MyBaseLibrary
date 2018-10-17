package com.cxp.mybaselibrary.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxp.mybaselibrary.R;


/**
 * loading弹窗工具类
 */
public class LoadingDialogUtils {

    private static Dialog loadingDialog;
    private static Activity activity;

    public static void showLoading(Activity mActivity, String loadingMsg, boolean isCancel) {
        try {
            if (TextUtils.isEmpty(loadingMsg)) {
                loadingMsg = "加载中...";
            }
            activity = mActivity;
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            View v = inflater.inflate(R.layout.loading_layout, null);// 得到加载view
            ImageView pb = v.findViewById(R.id.pb);
            ObjectAnimator animator = ObjectAnimator.ofFloat(pb, "rotation", 0, 360);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(-1);
            animator.setDuration(800);
            animator.start();
            TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
            tipTextView.setText(loadingMsg);// 设置加载信息
            // 创建自定义样式dialog
            if (loadingDialog == null) {
                loadingDialog = new Dialog(mActivity, R.style.loading_dialog);
            }
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setCancelable(isCancel);
            loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT));
            loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                }
            });
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopLoading() {
        try {
            if (loadingDialog != null && activity != null && !activity.isFinishing()) {
                loadingDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
