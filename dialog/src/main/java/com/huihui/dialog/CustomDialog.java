package com.huihui.dialog;

import android.app.Dialog;
import android.content.Context;
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
        private boolean showTitle = false;
        private boolean showMessage = false;
        private boolean showCancelButton = false;
        private boolean showConfirmButton = false;
        private int minHeight;

        public Builder(Context context) {
            this.mContext = context;

            View rootView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, null);
            titleTextView = rootView.findViewById(R.id.dialog_title);
            msgTextView = rootView.findViewById(R.id.dialog_message);
            cancelTextView = rootView.findViewById(R.id.dialog_cancel_tv);
            confirmTextView = rootView.findViewById(R.id.dialog_confirm_tv);

            titleTextView.setVisibility(View.GONE);
            msgTextView.setVisibility(View.GONE);
            cancelTextView.setVisibility(View.GONE);
            confirmTextView.setVisibility(View.GONE);

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

        public Builder setCancelButton(CharSequence cancelButton, final View.OnClickListener listener) {
            return setCancelButton(cancelButton, listener, true);
        }

        public Builder setCancelButton(CharSequence cancelButton, final View.OnClickListener listener, boolean hideDialog) {
            showCancelButton = !TextUtils.isEmpty(cancelButton);
            return setButton(cancelTextView, cancelButton, listener, hideDialog);
        }


        public Builder setConfirmButton(CharSequence confirmButton, final View.OnClickListener listener) {
            return setConfirmButton(confirmButton, listener, true);
        }

        public Builder setConfirmButton(CharSequence confirmButton, final View.OnClickListener listener, boolean hideDialog) {
            showConfirmButton = !TextUtils.isEmpty(confirmButton);
            return setButton(confirmTextView, confirmButton, listener, hideDialog);
        }

        public Builder setSingleButton(CharSequence button, final View.OnClickListener listener) {
            showConfirmButton = !TextUtils.isEmpty(button);
            return setButton(confirmTextView, button, listener, true);
        }

        public Builder setCanceledOnTouchOutside(boolean cancelOutside) {
            if (dialog != null) {
                dialog.setCanceledOnTouchOutside(cancelOutside);
            }
            return this;
        }

        private Builder setButton(TextView view, CharSequence text, final View.OnClickListener listener, final boolean hideDialog) {
            view.setText(text);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (hideDialog) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onClick(view);
                    }
                }
            });
            return this;
        }


        private void updateLayout() {
            titleTextView.setVisibility(showTitle ? View.VISIBLE : View.GONE);
            msgTextView.setVisibility(showMessage ? View.VISIBLE : View.GONE);
            cancelTextView.setVisibility(showCancelButton ? View.VISIBLE : View.GONE);
            confirmTextView.setVisibility(showConfirmButton ? View.VISIBLE : View.GONE);

            if (!showCancelButton && !showConfirmButton) {
                confirmTextView.setVisibility(View.VISIBLE);
                confirmTextView.setText(DEFAULT_BUTTON);
                confirmTextView.setBackgroundResource(R.drawable.dialog_single_button_bg);
                confirmTextView.setOnClickListener(new View.OnClickListener() {
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


            if (!(showTitle && showMessage)) {
                DisplayMetrics density = mContext.getResources().getDisplayMetrics();
                Log.d("Test", "updateLayout: " + density);
                minHeight = (int)(1.0 / 4 * density.widthPixels);
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
            updateLayout();
            dialog.show();
        }
    }

}
