# AndroidDialog

### CustomDialog用法
```
   CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
        builder.setMessage("我渐渐发现了一个悲凉的事实，那就是，站在个人成长的角度来看，太多的人被一份“好工作”给坑了")
                .setTitle("效用函数")
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
```

### BottomMenuDialog用法

```
       ArrayList<CharSequence> list = new ArrayList<>();
        list.add("从手机相册选择");
        list.add("从空间相册选择");
        new BottomMenuDialog.Builder(MainActivity.this)
                .setMenus(list)
                .setTitle("你可以将照片上传至照片墙")
                .setOnItemClickListener(new BottomMenuDialog.OnItemClickListener() {
                    @Override
                    public void click(View view, CharSequence menu) {
                        Toast.makeText(MainActivity.this, menu, Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
 ```
