package cn.akit.frontlinepro.imagedtlscale;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;

import com.socks.library.KLog;

/**
 * Created by chenYuXuan on 2019/4/13.
 * email : southxvii@163.com
 */
public class MyTextureView extends TextureView {

    public MyTextureView(Context context) {
        super(context);
    }

    public MyTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyTextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.debug("Test--->");
        return super.onTouchEvent(event);
    }
}
