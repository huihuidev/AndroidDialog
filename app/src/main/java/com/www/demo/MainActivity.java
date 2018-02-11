package com.www.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import com.huihui.dialog.BottomMenuDialog;
import com.huihui.dialog.CustomDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.open1).setOnClickListener(this);
        findViewById(R.id.open2).setOnClickListener(this);
        findViewById(R.id.open3).setOnClickListener(this);
        findViewById(R.id.open4).setOnClickListener(this);
        findViewById(R.id.open5).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int vId = v.getId();
        switch (vId) {
            case R.id.open1:
                dialogWithTitle();
                break;

            case R.id.open2:
                dialogWithMessage();
                break;

            case R.id.open3:
                dialogWithAll();
                break;

            case R.id.open4:
                bottomMenu();
                break;

            case R.id.open5:
                noCancelDialog();
                break;

            default:


                break;
        }




    }

    private void noCancelDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("提示").setMessage("不点我休想让我关闭").setCancelButton("取消", null)
                .setConfirmButton("确定", null)
                .setCancelable(false).show();
    }

    private void bottomMenu() {
        SpannableString title = new SpannableString("选择你要学习的语言");
        title.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0000")), 0, title.length(), 0);


        SpannableString string = new SpannableString("放假");
        string.setSpan(new ForegroundColorSpan(Color.parseColor("#219829")), 0, string.length(), 0);
        string.setSpan(new AbsoluteSizeSpan(20, true), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ArrayList<CharSequence> list = new ArrayList<>();
        list.add("Java");
        list.add("Android");
        list.add(string);
        list.add("IOS");
        list.add("Kotlin");
        list.add("最厉害的语言\n Java");
        list.add("C++");
        new BottomMenuDialog.Builder(MainActivity.this)
                .setMenus(list)
                .showCancelMenu(true)
                .setTitle(title)
                .setOnItemClickListener(new BottomMenuDialog.OnItemClickListener() {
                    @Override
                    public void click(View view, CharSequence menu) {
                        Toast.makeText(MainActivity.this, menu, Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void dialogWithAll() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("提示").setMessage("完了后测试通过后看能不能紧急发布，不等下周二发布窗口").setCancelButton("取消", null)
                .setConfirmButton("确定", true,null).show();
    }

    private void dialogWithMessage() {
        SpannableString string = new SpannableString("取消");
        string.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0000")), 0, string.length(), 0);

        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("完了后测试通过后看能不能紧急发布，不等下周二发布窗口").setCancelButton(string, true,null)
                .setTextSize(10f, 0f)
                .setConfirmButton("确定", null).show();
    }

    private void dialogWithTitle() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("提示").setCancelButton("取消", null)
                .setConfirmButton("确定", null)
                .setSingleButton("OK", null).show();
    }
}
