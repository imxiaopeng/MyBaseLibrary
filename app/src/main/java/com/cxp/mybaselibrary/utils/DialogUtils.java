package com.cxp.mybaselibrary.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cxp.mybaselibrary.R;

/**
 * dialog工具类
 */
public class DialogUtils {

    public static final int POSITION_WECHAT = 0;
    public static final int POSITION_MOMENTS = 1;
    public static final int POSITION_QQ = 2;
    public static final int POSITION_WEIBO = 3;

    public static final int delayDismiss = 1200;

    /**
     * 自定义分享弹框
     *
     * @return
     */
    /*public static Dialog showShareDialog(final Context context, final OnItemClickListener onItemClickListener) {
        final Dialog shareDialog = new Dialog(context, R.style.my_share_dialog);
        //自定义弹出框布局
        RelativeLayout root = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.dialog_commen, null);
        LinearLayout ll = root.findViewById(R.id.ll);
        ll.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int measuredHeight = ll.getMeasuredHeight();
        LinearLayout llWechat = root.findViewById(R.id.ll_wechat);
        LinearLayout llMoments = root.findViewById(R.id.ll_moments);
        LinearLayout llQQ = root.findViewById(R.id.ll_qq);
        LinearLayout llWeibo = root.findViewById(R.id.ll_weibo);
        llWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
                if (!WXShare.getInstance(context).isWXAppInstalled()) {
                    UIUtils.showToastShort(context, R.string.wechat_uninstall);
                    return;
                }
                onItemClickListener.onItemClick(v, POSITION_WECHAT);
            }
        });
        llMoments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
                if (!WXShare.getInstance(context).isWXAppInstalled()) {
                    UIUtils.showToastShort(context, R.string.wechat_uninstall);
                    return;
                }
                onItemClickListener.onItemClick(v, POSITION_MOMENTS);
            }
        });
        llQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
                if (!WeiboAppManager.getInstance(context).hasWbInstall()) {
                    UIUtils.showToastShort(context, R.string.qq_uninstall);
                    return;
                }
                onItemClickListener.onItemClick(v, POSITION_QQ);
            }
        });
        llWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
                if (!AppUtils.isQQClientAvailable(context)) {
                    UIUtils.showToastShort(context, R.string.weibo_uninstall);
                    return;
                }
                onItemClickListener.onItemClick(v, POSITION_WEIBO);
            }
        });
        shareDialog.setContentView(root);
        Window dialogWindow = shareDialog.getWindow();
        dialogWindow.setType(WindowManager.LayoutParams.TYPE_APPLICATION);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) context.getResources().getDisplayMetrics().widthPixels; // 宽度
        lp.height = measuredHeight; // 高度
        dialogWindow.setAttributes(lp);
        shareDialog.setCancelable(true);
        shareDialog.setCanceledOnTouchOutside(true);
        shareDialog.show();
        return shareDialog;
    }*/

    public static interface OnItemClickListener {
        public void onItemClick(View view, int i);
    }

    public static void showCommenDialog(Context context, String title, String msg, String left, final String right, final View.OnClickListener leftListener, final View.OnClickListener rightListener) {
        final Dialog commenDialog = new Dialog(context, R.style.my_dialog);
        //自定义弹出框布局
        RelativeLayout root = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.dialog_commen, null);
        LinearLayout ll = root.findViewById(R.id.ll);
        ll.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int measuredHeight = ll.getMeasuredHeight();
        commenDialog.setContentView(root);
        TextView tvTitle = root.findViewById(R.id.tv_title);
        TextView tvMessage = root.findViewById(R.id.tv_message);
        TextView tvCancle = root.findViewById(R.id.tv_cancle);
        TextView tvConfirm = root.findViewById(R.id.tv_confirm);
        if (TextUtils.isEmpty(msg)) {
            tvMessage.setVisibility(View.GONE);
        } else {
            tvMessage.setVisibility(View.VISIBLE);
            setText(tvMessage, msg);
        }
        setText(tvTitle, title);
        setText(tvCancle, left);
        setText(tvConfirm, right);
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftListener != null) {
                    leftListener.onClick(v);
                }
                if (commenDialog != null && commenDialog.isShowing()) {
                    commenDialog.dismiss();
                }
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right != null) {
                    rightListener.onClick(v);
                }
                if (commenDialog != null && commenDialog.isShowing()) {
                    commenDialog.dismiss();
                }
            }
        });
        Window dialogWindow = commenDialog.getWindow();
        dialogWindow.setType(WindowManager.LayoutParams.TYPE_APPLICATION);
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) context.getResources().getDisplayMetrics().widthPixels; // 宽度
        lp.height = measuredHeight; // 高度
        dialogWindow.setAttributes(lp);
        commenDialog.setCancelable(true);
        commenDialog.setCanceledOnTouchOutside(true);
        commenDialog.show();
    }

    private static void setText(TextView tvTitle, String title) {
        if (tvTitle != null && !TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }
}
