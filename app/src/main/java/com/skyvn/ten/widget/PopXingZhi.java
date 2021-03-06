package com.skyvn.ten.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.skyvn.ten.R;
import com.skyvn.ten.widget.wheelview.WheelView;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/3115:35
 * desc   :
 * version: 1.0
 */
public class PopXingZhi extends PopupWindow {


    private Activity activity;
    private View dialogView;

    private WheelView startYear;

    private TextView iv_cancel;
    private TextView tv_finish;

    private TextView title_text;

    private List<String> data;

    private String strTitle;

    public PopXingZhi(Activity activity, String title_text, List<String> data) {
        super(activity);
        this.activity = activity;
        this.data = data;
        this.strTitle = title_text;
        dialogView = LayoutInflater.from(activity).inflate(R.layout.pop_select_xingzhi, null);

        initView();
        this.setBackgroundDrawable(new ColorDrawable(0));
        this.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_menu_bottombar);
        //实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0x808080);
        //设置SelectPicPopupWindow弹出窗体的背景
        // this.setBackgroundDrawable(dw);
        this.setOnDismissListener(() -> backgroundAlpha(1f));
    }


    private void initView() {
        startYear = dialogView.findViewById(R.id.select);
        iv_cancel = dialogView.findViewById(R.id.iv_cancel);
        tv_finish = dialogView.findViewById(R.id.tv_finish);
        title_text = dialogView.findViewById(R.id.title_text);

        title_text.setText(strTitle);

        startYear.setItems(data, 0);

        iv_cancel.setOnClickListener(view -> dismiss());
        tv_finish.setOnClickListener(view -> {
            if (listener != null) {
                dismiss();
                listener.commit(startYear.getSelectedPosition(), startYear.getSelectedItem());
            }
        });
    }


    public void setSelectPosition(int position) {
        startYear.setItems(data, position);
    }


    /***
     * 显示时将屏幕置为透明
     */
    public void showAtLocation(View parent) {
        super.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.5f);
    }


    /**
     * 设置添加屏幕的背景透明度
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    onSelectListener listener;

    public void setListener(onSelectListener listener) {
        this.listener = listener;
    }


    public interface onSelectListener {

        void commit(int position, String item);
    }

}
