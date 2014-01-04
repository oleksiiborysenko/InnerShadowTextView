package com.selfishpilot.innerShadowTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * User: oleksii
 * Date: 02.01.14 17:04
 */
public class ShadowTextView extends TextView {
    private float innerShadowRadius;
    private int innerShadowColor;
    private int innerShadowDX;
    private int innerShadowDY;
    private Canvas mCanvas;
    private Bitmap mBitmap;

    public ShadowTextView(Context context) {
        super(context);
        init(null);
    }

    public ShadowTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ShadowTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }

        if(attrs != null){
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.InnerShadowTextView);

            if(a.hasValue(R.styleable.InnerShadowTextView_innerShadowColor)){
                innerShadowColor = a.getColor(R.styleable.InnerShadowTextView_innerShadowColor, 0xff000000);
                innerShadowRadius = a.getFloat(R.styleable.InnerShadowTextView_innerShadowRadius, 0);
                innerShadowDX = a.getInt(R.styleable.InnerShadowTextView_innerShadowDX, 0);
                innerShadowDY = a.getInt(R.styleable.InnerShadowTextView_innerShadowDY, 0);
            }
        }
    }

    private void generateCanvas() {
        mCanvas = new Canvas();
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int restoreColor = this.getCurrentTextColor();
        generateCanvas();

        TextPaint paint = this.getPaint();
        this.setTextColor(innerShadowColor);
        super.onDraw(mCanvas);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        paint.setMaskFilter(new BlurMaskFilter(innerShadowRadius, BlurMaskFilter.Blur.NORMAL));

        mCanvas.save();
        mCanvas.translate(innerShadowDX, innerShadowDY);
        super.onDraw(mCanvas);
        mCanvas.restore();
        canvas.drawBitmap(mBitmap, 0, 0, null);
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        paint.setXfermode(null);
        paint.setMaskFilter(null);
        this.setTextColor(restoreColor);
        this.setShadowLayer(0,0,0,0);
    }
}
