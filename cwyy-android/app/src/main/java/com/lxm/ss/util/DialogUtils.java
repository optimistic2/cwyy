package com.lxm.ss.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lxm.ss.R;

/**
 * Created by lxm on 2016/11/21.
 */

public class DialogUtils {

    /**
     * 获取加载页的对话框
     *
     * @param context
     * @return
     */
    public static Dialog getProgressDialog(Context context) {

//        final Dialog dialog = new Dialog(context, R.style.DialogStyle);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.loading_dialog);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//
//        int screenW = Utils.getScreenWidth(context);
//        lp.width = (int) (0.6 * screenW);
//        return dialog;


        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.loading_dialog, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.loading_dialog);

        Dialog loadingDialog = new Dialog(context, R.style.Loading_dialog);
        loadingDialog.setContentView(linearLayout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局

//        动画
//        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.progress_show);
//
//       ImageView imageView = (ImageView) view.findViewById(R.id.progress_img);
//        imageView.startAnimation(operatingAnim);

//        animationIV.setImageResource(R.drawable.welcome01);
//        Animation loadAnimation = AnimationUtils.loadAnimation(mContext, R.anim.push_in);
//        animationIV.setAnimation(loadAnimation);
//        loadAnimation.start();

        return loadingDialog;
    }

}
