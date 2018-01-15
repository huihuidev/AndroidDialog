package com.www.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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


        findViewById(R.id.open).setOnClickListener(this);

//        CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
//        builder.setTitle("亲爱的用户,你还没有借款记录,需要资金周转记得找卡贷君哈!")
//                .setMessage("亲爱的用户,你还没有借款记录,需要资金周转记得找卡贷君哈!")
//                .setSingleButton("我知道了", null)
//                .show();

//        CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
//        builder.setTitle("爱的用户,你还没有借款记录,需要资金周转记得找卡贷君哈!")
//                .setTitle(spanned)
//                .setSingleButton("确定", null)
//                .show();


    }

    @Override
    public void onClick(View v) {
//        ArrayList<CharSequence> list = new ArrayList<>();
//        list.add("从手机相册选择");
//        list.add("从空间相册选择");
//        new BottomMenuDialog.Builder(MainActivity.this)
//                .setMenus(list)
//                .setTitle("你可以将照片上传至照片墙")
//                .setOnItemClickListener(new BottomMenuDialog.OnItemClickListener() {
//                    @Override
//                    public void click(View view, CharSequence menu) {
//                        Toast.makeText(MainActivity.this, menu, Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .show();


        CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
        builder.setMessage("爱的用户,你还没有借款记录,需要资金周转记得找卡贷君哈!")
                .setSingleButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "单行", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelButton("取消", true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .setConfirmButton("我知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .show();

    }
}
