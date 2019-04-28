package cn.akit.frontlinepro;

/**
 * Created by chenYuXuan on 2019/3/30.
 * email : southxvii@163.com
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * DongShanCircleImageView
 * 圆角阴影带描边圆形控件
 * @author：Shimmer
 * @emil：kaijson6@gmail.com Create：2019/3/21 18:19
 * Description：
 */
public class DongShanCircleImageView extends AppCompatImageView {
    private int borderWidth = 4;
    private int viewWidth;
    private int viewHeight;
    private Bitmap image;
    private Paint paint;
    private Paint paintBorder;
    private BitmapShader shader;
    private Matrix mMatrix;

    private int mRadius; //圆形图片的半径
    private float mScale; //图片的缩放比例

    public DongShanCircleImageView(Context context) {
        super(context);
        setup();
    }

    public DongShanCircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public DongShanCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    private void setup() {
        mMatrix = new Matrix();
        paint = new Paint();
        paint.setAntiAlias(true);
        paintBorder = new Paint();
        setBorderColor(Color.WHITE);
        paintBorder.setAntiAlias(true);
        this.setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
        paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f,  Color.parseColor("#33000000"));
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        this.invalidate();
    }

    public void setBorderColor(int borderColor) {
        if (paintBorder != null)
            paintBorder.setColor(borderColor);
        this.invalidate();
    }

    private void loadBitmap() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();
        if (bitmapDrawable != null)
            image = bitmapDrawable.getBitmap();
    }

    @Override
    public void onDraw(Canvas canvas) {
        loadBitmap();
        if (image != null) {
            int circleCenter = viewWidth / 2;
            shader = new BitmapShader(Bitmap.createScaledBitmap(image, circleCenter + borderWidth, circleCenter + borderWidth, false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            int bitmapWidth = circleCenter + borderWidth;
            int bitmapHeight = circleCenter + borderWidth;
            float sx = canvas.getWidth() * 1.0f / bitmapWidth;
            float sy = canvas.getWidth() * 1.0f / bitmapHeight;
            float scale = Math.max(sx, sy);

            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            shader.setLocalMatrix(matrix);

            paint.setShader(shader);
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter + borderWidth - 4.0f, paintBorder);
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter - 4.0f, paint);
        }
    }


    private Bitmap drawableToBitamp(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec, widthMeasureSpec);
        viewWidth = width - (borderWidth * 2);
        viewHeight = height - (borderWidth * 2);

        mRadius = viewWidth / 2;

        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = viewWidth;
        }
        return result;
    }

    private int measureHeight(int measureSpecHeight, int measureSpecWidth) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = viewHeight;
        }
        return (result + 2);
    }
}

