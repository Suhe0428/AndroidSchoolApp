package com.example.asus.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 实现图片轮播的核心类，自定义ViewGroup，必须实现方法：测量onMeasure()-》布局onLayout()-》绘制
 * 绘制：这里调用系统自带的绘制，不再重写
 */
public class ImageBarnnerViewGroup extends ViewGroup {
    //子视图布局
    private int children;//子视图个数
    private int childWidth;//子视图宽度
    private int childHeight;//子视图高度
    private int x;//第一次按下的横坐标，和移动过程移动前的横坐标
    private int index = 0;//每张图片的索引

    //底部圆点
    /**
     *思路：自定义一个继承自FrameLayout的布局，利用FrameLayout的特性实现圆点布局
     *底部素材：利用drawable的功能，实现圆点的展示：dot_normal.xml   dot_select.xml
     *自定义一个继承FrameLayout的类，加载ImageBarnnerViewGroup和圆点的布局(用LinearLayout实现)::ImageBarnnerFrameLayout类
     */

    //圆点切换
    /**
     * 定义接口ImageBarnnerViewGroupLisnner，及get、set方法
     * Handler和onTouchEvent中的设置
     * 在ImageBarnnerFrameLayout中实现接口，并在initImageBarnnerViewGroup方法中传递
     */
    public interface ImageBarnnerViewGroupListenner{
        void selectImage(int index);
    }

    private ImageBarnnerViewGroupListenner imageBarnnerViewGroupListenner;

    public ImageBarnnerViewGroupListenner getImageBarnnerViewGroupListenner() {
        return imageBarnnerViewGroupListenner;
    }

    public void setImageBarnnerViewGroupListenner(ImageBarnnerViewGroupListenner listenner) {
        this.imageBarnnerViewGroupListenner = listenner;
    }

    //单击事件
    /**
     *实现图片点击事件的动作获取和处理
     * 利用点击变量开关进行判断，在用户手指松开屏幕是判断其动作是点击还是移动
     * isClick:true表示点击，false与之相反
     */
    private boolean isClick;

    private ImageBarnnerListenner imageBarnnerListenner;

    public ImageBarnnerListenner getImageBarnnerListennr() {
        return imageBarnnerListenner;
    }

    public void setImageBarnnerListennr(ImageBarnnerListenner imageBarnnerListenner) {
        this.imageBarnnerListenner = imageBarnnerListenner;
    }

    public interface ImageBarnnerListenner{
        void clickImageIndex(int position);
    }

    //自动轮播
    /**
     * 1.采用Timer,TimerTask,Hander三者结合的方式
     * 2.通过两个方法控制是否启动自动轮播效果：startAuto和stopAuto
     * 3.设置一个变量参数isAuto来代表是否启动自动轮播的开关：boolean:true or false
     * 4.修改构造函数（这里放在Scroller的辅助方法initObj()中）
     * 5.在onTouchEvent方法中实现切换逻辑
     */
    private boolean isAuto=true;//赋值为true
    private Timer timer=new Timer();
    private TimerTask timerTask;
    private Handler handler=new Handler() {//注意导入的包为android.os.Handler,不是java.util.logging.Handler
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0://此时需要开启自动轮播
                    if(++index>=children){//最后一张图片与第一张的相连
                        index=0;
                    }
                    scrollTo(index*childWidth,0);
                    //图片切换
                    imageBarnnerViewGroupListenner.selectImage(index);
                    break;

            }
        }
    };

    private void  startAuto(){
        isAuto=true;
    }

    private void stopAuto(){
        isAuto=false;
    }

    //实现手动轮播的第二种方法(需要修改构造函数)
    /**
     * 1.辅助私有方法:创建Scroller对象
     * 2.重写方法computeScroll()
     * 3.ACTION_DOWN中的细节优化
     * 4.在onTouchEvent方法中实现切换逻辑
     */
    private Scroller scroller;

    private void initObj() {//辅助私有方法:创建Scroller对象及自动轮播的处理
        scroller = new Scroller(getContext());

        timerTask=new TimerTask() {
            @Override
            public void run() {
                if(isAuto){//开启了轮播
                    handler.sendEmptyMessage(0);
                }
            }
        };
        timer.schedule(timerTask,100,2000);//每隔两秒切换

    }

    public ImageBarnnerViewGroup(Context context) {
        super(context);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }

    //测量：onMeasure()
    /**
     * 实现ViewGroup容器，需要知道所有的子视图。
     * 要想测量ViewGroup的宽度和高度，要先测量子视图的宽度和高度之和。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //1.子视图个数
        children = getChildCount();//获取子视图个数
        if (0 == children) {
            setMeasuredDimension(0, 0);
        } else {
            //2.测量子视图的宽度和高度,以第一个子视图为基准，因为第一个子视图绝对存在。
            // ViewGroup的宽和高即为第一个子视图的宽*子视图个数和高。
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            View view = getChildAt(0);
            childWidth = view.getMeasuredWidth();
            childHeight = view.getMeasuredHeight();
            //3.根据子视图的宽度和高度求ViewGroup的宽度和高度
            int height = view.getMeasuredHeight();
            int width = view.getMeasuredWidth() * children;
            setMeasuredDimension(width, height);
        }
    }

    //布局：onLayout()
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int leftMargin = 0;
            for (int i = 0; i < children; i++) {
                View view = getChildAt(i);
                view.layout(leftMargin, 0, leftMargin + childWidth, childHeight);
                leftMargin = leftMargin + childWidth;
            }
        }
    }

    /**
     * 事件的处理过程：调用容器的拦截方法onInterceptTouchEvent()
     * 返回true：ViewGroup容器会处理拦截事件
     * 返回flase：不处理此次拦截事件，继续向下传递
     * 这里我们希望返回true，处理方法为调用onTouchEvent()逻辑
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * 两种方法实现轮播图的手动切换（1.scrollTo,scrollBy实现。2.Scroller对象实现）
     * 问题分析：
     * 1.滑动图片实际是ViewGroup子视图的移动过程。
     *      1.1、获取滑动前和滑动后的横坐标，求出滑动距离
     *      1.2、利用ScrollBy实现图片切换
     * 2.在按下时，前后横坐标值相同
     * 3.在滑动过程中不断调用ACTION_MOVE方法，要保存滑动前和滑动后的值，以便求出滑动距离
     * 4.抬起时，计算出此时将要滑动到哪张图片，需要求出其索引
     *      4.1、索引=（当前ViewGroup的位置+每张图片宽度/2）/每张图片的宽度
     * 5、利用scrollTo方法滑到该图片或者Scroller对象实现滑动
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://用户按下时的处理
                isClick=true;

                x = (int) event.getX();
                if(!scroller.isFinished()){//优化
                    scroller.abortAnimation();
                }

                stopAuto();//按下时轮播停止
                break;
            case MotionEvent.ACTION_MOVE://用户滑动时的处理
                isClick=false;

                int moveX = (int) event.getX();
                int distance = moveX - x;
                scrollBy(-distance, 0);
                x = moveX;
                break;
            case MotionEvent.ACTION_UP://用户抬起时的处理
                int scrollX = getScrollX();
                index = (scrollX + childWidth / 2) / childWidth;
                if (index < 0) {//此时已将滑动到最左边第一张图
                    index = 0;
                } else if (index > children - 1) {//此时滑动到最右边最后一张图
                    index = children - 1;
                }

                if(isClick){//点击事件处理
                    imageBarnnerListenner.clickImageIndex(index);
                }else {//滑动事件处理
                    //实现手动轮播的第一种方法
                    //scrollTo(index * childWidth, 0);
                    //实现手动轮播的第二种方法
                    int dx = index * childWidth - scrollX;
                    scroller.startScroll(scrollX, 0, dx, 0);
                    postInvalidate();
                    //图片切换
                    imageBarnnerViewGroupListenner.selectImage(index);
                }

                startAuto();//松开时轮播开始
                break;
            default:
                break;
        }
        return true;//事件处理完成
    }



}
