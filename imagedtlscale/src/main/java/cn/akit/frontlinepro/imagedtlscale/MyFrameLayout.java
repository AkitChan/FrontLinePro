package cn.akit.frontlinepro.imagedtlscale;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.socks.library.KLog;

/**
 * Created by chenYuXuan on 2019/4/13.
 * email : southxvii@163.com
 */
public class MyFrameLayout extends FrameLayout {


    private ValueAnimator mValueAnimator;

    public MyFrameLayout(Context context) {
        super(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float downY = 0.0f;
    private float downX = 0.0f;
    private float downTransY = 0.0f;
    private float downTransX = 0.0f;
    private float downScale = 0.0f;
    private float offsetY = 50;
    private float offsetX = 0;
    private boolean dragging = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mValueAnimator != null)
                    mValueAnimator.cancel();
                downY = event.getRawY();
                downX = event.getRawX();
                downTransX = getTranslationX();
                downTransY = getTranslationY();
                downScale = getScaleX();

                if (getScaleX() > 1 || getScaleX() < 1) {
                    //处于缩放状态，禁止父类拦截事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                    //直接进入拖拽状态
                    dragging = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getRawY();
                float moveX = event.getRawX();
                float dy = moveY - downY;
                float dx = moveX - downX;
                if (!dragging) {
                    if (dy > offsetY) {
                        dragging = true;
                        KLog.debug("拖拽模式---》");
                        offsetX = dx;
                    }
                } else {
                    //减去开始拖拽前的滑动距离
                    dy -= offsetY;
                    dx -= offsetX;

                    float scale = Math.abs(dy / 3000);
                    MyFrameLayout.this.setScaleX(Math.max(0.3f, downScale - scale));
                    MyFrameLayout.this.setScaleY(Math.max(0.3f, downScale - scale));
                    MyFrameLayout.this.setTranslationX(downTransX + (int) dx);
                    MyFrameLayout.this.setTranslationY(downTransY + (int) dy);
                }
                break;
            case MotionEvent.ACTION_UP:
                dragging = false;
                resetAnimator();
                break;
        }

        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    public void resetAnimator() {
        final float ratio = getScaleX();

        mValueAnimator = ValueAnimator.ofFloat(ratio, 1f).setDuration(400);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setScaleX(value);
                setScaleY(value);
                setTranslationX(getTranslationX() * ((value - 1) / (ratio - 1)));
                setTranslationY(getTranslationY() * ((value - 1) / (ratio - 1)));
            }
        });
        mValueAnimator.start();
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
