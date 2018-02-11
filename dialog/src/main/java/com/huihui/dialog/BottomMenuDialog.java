package com.huihui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @author HH on 17/12/7.
 */

public class BottomMenuDialog {

    public static class Builder {

        private TextView cancelTextView;
        private LinearLayout menusLayout;
        private Context mContext;
        private Dialog dialog;
        private int menuHeight;
        private int menuPadding;
        private CharSequence title;
        private List<CharSequence> menus;
        private LinearLayout.LayoutParams menuLayoutParam;
        private OnItemClickListener onItemClickListener;
        private boolean showCancelMenu = true;


        public Builder(Context context) {
            mContext = context;
            View contentView;
            contentView = LayoutInflater.from(mContext).inflate(R.layout.bottom_dialog_layout, null);
            menusLayout = contentView.findViewById(R.id.item_layout);
            cancelTextView = contentView.findViewById(R.id.tv_cancel);
            cancelTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });

            dialog = new Dialog(mContext, R.style.BottomDialogStyle);
            dialog.setContentView(contentView);
            Window window = dialog.getWindow();
            if (null != window) {
                window.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutParams.gravity = Gravity.BOTTOM;
                window.setAttributes(layoutParams);
            }

            menuHeight = mContext.getResources().getDimensionPixelSize(R.dimen.bottom_menu_height);
            menuPadding = mContext.getResources().getDimensionPixelSize(R.dimen.bottom_menu_padding);
            menuLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        public Builder setMenus(List<CharSequence> menus) {
            this.menus = menus;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            if (!TextUtils.isEmpty(title)) {
                this.title = title;
            }
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
            return this;
        }

        public Builder showCancelMenu(boolean show) {
            this.showCancelMenu = show;
            return this;
        }


        private void showLayout() {
            menusLayout.removeAllViews();
            cancelTextView.setVisibility(showCancelMenu ? View.VISIBLE : View.GONE);
            boolean emptyMenu = isEmptyMenu();

            if (emptyMenu && TextUtils.isEmpty(title)) {
                title = "暂无数据";
            }

            if (!TextUtils.isEmpty(title)) {
                TextView textView = new TextView(mContext);
                textView.setText(title);
                textView.setMinHeight(menuHeight);
                textView.setPadding(0, menuPadding, 0, menuPadding);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.GRAY);
                textView.setTextSize(12);
                menusLayout.addView(textView, menuLayoutParam);
            }

            if (emptyMenu) {
                return;
            }

            for (final CharSequence menu : menus) {
                final TextView textView = new TextView(mContext);
                textView.setText(menu);
                textView.setMinHeight(menuHeight);
                textView.setPadding(0, menuPadding, 0, menuPadding);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(17);
                textView.setBackgroundResource(R.drawable.dialog_menu_bg);
                menusLayout.addView(textView, menuLayoutParam);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }

                        if (onItemClickListener != null) {
                            onItemClickListener.click(textView, menu);
                        }
                    }
                });
            }
        }

        public void show() {
            if (dialog != null && mContext != null) {
                if (mContext instanceof Activity && ((Activity) mContext).isFinishing()) {
                    return;
                }
                showLayout();
                dialog.show();
            }
        }


        //菜单是否为空
        private boolean isEmptyMenu() {
            return menus == null || menus.size() == 0;
        }

    }




    public interface OnItemClickListener {
        void click(View view, CharSequence menu);
    }


}
