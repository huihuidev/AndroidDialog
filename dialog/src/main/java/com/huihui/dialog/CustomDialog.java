package com.huihui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author HH on 17/11/3.
 */

public class CustomDialog {

    public static class Builder {
        private static final String DEFAULT_BUTTON = "确定";
        private Context mContext;
        private Dialog dialog;
        private TextView titleTextView;
        private TextView msgTextView;
        private TextView cancelTextView;
        private TextView confirmTextView;
        private TextView singleTextView;
        private boolean showTitle = false;
        private boolean showMessage = false;
        private boolean showCancelButton = false;
        private boolean showConfirmButton = false;
        private boolean showSingleButton = false;
        private int minHeight;

        public Builder(Context context) {
            this.mContext = context;

            View rootView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, null);
            titleTextView = rootView.findViewById(R.id.dialog_title);
            msgTextView = rootView.findViewById(R.id.dialog_message);
            cancelTextView = rootView.findViewById(R.id.dialog_cancel_tv);
            confirmTextView = rootView.findViewById(R.id.dialog_confirm_tv);
            singleTextView = rootView.findViewById(R.id.dialog_single_tv);

            titleTextView.setVisibility(View.GONE);
            msgTextView.setVisibility(View.GONE);
            cancelTextView.setVisibility(View.GONE);
            confirmTextView.setVisibility(View.GONE);
            singleTextView.setVisibility(View.GONE);

            dialog = new Dialog(context, R.style.CustomDialogStyle);
            dialog.setContentView(rootView);
        }

        public Builder setTitle(CharSequence title) {
            showTitle = !TextUtils.isEmpty(title);
            titleTextView.setText(title);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            showMessage = !TextUtils.isEmpty(message);
            msgTextView.setText(message);
            return this;
        }


        public Builder setCancelButton(CharSequence cancelButton, View.OnClickListener listener) {
            return setCancelButton(cancelButton, false, listener);
        }

        public Builder setCancelButton(CharSequence cancelButton, boolean bold, View.OnClickListener listener) {
            showCancelButton = !TextUtils.isEmpty(cancelButton);
            return setButton(cancelTextView, cancelButton, listener, bold);
        }

        public Builder setConfirmButton(CharSequence confirmButton, View.OnClickListener listener) {
            return setConfirmButton(confirmButton, true, listener);
        }

        public Builder setConfirmButton(CharSequence confirmButton, boolean bold, View.OnClickListener listener) {
            showConfirmButton = !TextUtils.isEmpty(confirmButton);
            return setButton(confirmTextView, confirmButton, listener, bold);
        }

        public Builder setSingleButton(CharSequence button, View.OnClickListener listener) {
            return setSingleButton(button, false, listener);
        }


        public Builder setSingleButton(CharSequence button, boolean bold, View.OnClickListener listener) {
            showSingleButton = !TextUtils.isEmpty(button);
            return setButton(singleTextView, button, listener, bold);
        }


        public Builder setCanceledOnTouchOutside(boolean cancelOutside) {
            if (dialog != null) {
                dialog.setCanceledOnTouchOutside(cancelOutside);
            }
            return this;
        }

        //屏蔽返回键关闭dialog
        public Builder setCancelable(boolean flag) {
            if (dialog != null) {
                dialog.setCancelable(flag);
            }
            return this;
        }

        private Builder setButton(TextView view, CharSequence text, final View.OnClickListener listener, boolean bold) {
            view.setText(text);
            view.setTypeface(Typeface.defaultFromStyle(bold ? Typeface.BOLD : Typeface.NORMAL));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onClick(view);
                    }
                }
            });
            return this;
        }


        private void showLayout() {
            titleTextView.setVisibility(showTitle ? View.VISIBLE : View.GONE);
            msgTextView.setVisibility(showMessage ? View.VISIBLE : View.GONE);

            if (showSingleButton) {
                singleTextView.setVisibility(View.VISIBLE);
            } else {
                cancelTextView.setVisibility(showCancelButton ? View.VISIBLE : View.GONE);
                confirmTextView.setVisibility(showConfirmButton ? View.VISIBLE : View.GONE);
            }

            if (!showSingleButton) {
                if (!showCancelButton && !showConfirmButton) {
                    singleTextView.setVisibility(View.VISIBLE);
                    singleTextView.setText(DEFAULT_BUTTON);
                    singleTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    });
                }

                if (showCancelButton && !showConfirmButton) {
                    cancelTextView.setBackgroundResource(R.drawable.dialog_single_button_bg);
                } else if (!showCancelButton && showConfirmButton) {
                    confirmTextView.setBackgroundResource(R.drawable.dialog_single_button_bg);
                }
            }

            if (!(showTitle && showMessage)) {
                DisplayMetrics density = mContext.getResources().getDisplayMetrics();
                Log.d("Test", "updateLayout: " + density);
                minHeight = (int) (1.0 / 4 * density.widthPixels);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, minHeight);
            if (showTitle && !showMessage) {
                titleTextView.setLayoutParams(layoutParams);
                titleTextView.setPadding(titleTextView.getPaddingLeft(), 0, titleTextView.getPaddingRight(), 0);
            } else if (!showTitle && showMessage) {
                msgTextView.setLayoutParams(layoutParams);
                msgTextView.setPadding(msgTextView.getPaddingLeft(), 0, msgTextView.getPaddingRight(), 0);
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
    }

}
