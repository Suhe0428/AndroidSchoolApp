package com.example.asus.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.asus.myapplication.R;

import java.util.List;

public class ImageBarnnerFrameLayout extends FrameLayout implements ImageBarnnerViewGroup.ImageBarnnerViewGroupListenner, ImageBarnnerViewGroup.ImageBarnnerListenner {
    //圆点布局（要修改构造函数）
    private ImageBarnnerViewGroup imageBarnnerViewGroup;

    private LinearLayout linearLayout;

    private void initImageBarnnerViewGroup() {//初始化自定义的核心类ImageBarnnerViewGroup
        imageBarnnerViewGroup = new ImageBarnnerViewGroup(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageBarnnerViewGroup.setLayoutParams(layoutParams);
        imageBarnnerViewGroup.setImageBarnnerViewGroupListenner(this);//将Listenner传递给FrameLayout
        imageBarnnerViewGroup.setImageBarnnerListennr(this);
        addView(imageBarnnerViewGroup);
    }

    private void initDotLinearLayout() {//初始化底部圆点的线性布局
        linearLayout = new LinearLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 40);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(Color.WHITE);
        addView(linearLayout);

        LayoutParams layoutParams1 = (LayoutParams) linearLayout.getLayoutParams();
        layoutParams1.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(layoutParams1);

        //设置底部圆点透明度（3.0版本之前和之后，方法setAlpha()的调用者不同）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            linearLayout.setAlpha(0.5f);
        } else {
            linearLayout.getBackground().setAlpha(100);
        }

    }

    public void addBitmap(List<Bitmap> list) {
        for (int i = 0; i < list.size(); i++) {
            Bitmap bitmap = list.get(i);
            addBitmapToImageBarnnerViewGroup(bitmap);
            addDotsToLinearLayout();
        }
    }

    private void addBitmapToImageBarnnerViewGroup(Bitmap bitmap) {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(PhoneWidth.WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setImageBitmap(bitmap);
        imageBarnnerViewGroup.addView(imageView);
    }

    private void addDotsToLinearLayout() {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5,5,5,5);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.dot_normal);
        linearLayout.addView(imageView);
    }

    //点击事件
    private FrameLayoutListenner frameLayoutListenner;

    public FrameLayoutListenner getFrameLayoutListenner() {
        return frameLayoutListenner;
    }

    public void setFrameLayoutListenner(FrameLayoutListenner frameLayoutListenner) {
        this.frameLayoutListenner = frameLayoutListenner;
    }

    //构造函数
    public ImageBarnnerFrameLayout(@NonNull Context context) {
        super(context);
        initImageBarnnerViewGroup();
        initDotLinearLayout();
    }

    public ImageBarnnerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initImageBarnnerViewGroup();
        initDotLinearLayout();
    }

    public ImageBarnnerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageBarnnerViewGroup();
        initDotLinearLayout();
    }

    //圆点切换
    @Override
    public void selectImage(int index) {
        int count=linearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            ImageView imageView= (ImageView) linearLayout.getChildAt(i);
            if(i==index){
                imageView.setImageResource(R.drawable.dot_select);
            }else {
                imageView.setImageResource(R.drawable.dot_normal);
            }
        }
    }

    //单击事件
    @Override
    public void clickImageIndex(int position) {
        frameLayoutListenner.clickImageIndex(position);
    }
    public interface FrameLayoutListenner{
        void clickImageIndex(int position);
    }
}
