package com.vivek.wo.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class ShadowLayout extends FrameLayout {
    //初始化画笔，开启抗锯齿
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //初始化阴影矩阵
    private Rect mBoundRect = new Rect();
    /**
     * 阴影颜色
     */
    private int shadowColor;
    /**
     * 阴影扩散大小
     */
    private int shadowRadio;
    /**
     * X轴偏移大小
     */
    private int shadowOffsetX;
    /**
     * Y轴偏移大小
     */
    private int shadowOffsetY;
    /**
     * 是否隐藏阴影
     */
    private boolean isShadowHidden = true;

    /**
     * 矩阵圆角大小
     */
    private int rectRadio;

    /**
     * 左边阴影部分大小，为0时无阴影，此参数影响 {@link #shadowOffsetX} 左边的参数值
     */
    private int shadowPaddingLeft;
    /**
     * 上边阴影部分大小，为0时无阴影，此参数影响 {@link #shadowOffsetY} 上边的参数值
     */
    private int shadowPaddingTop;
    /**
     * 右边阴影部分大小，为0时无阴影，此参数影响 {@link #shadowOffsetX} 右边的参数值
     */
    private int shadowPaddingRight;
    /**
     * 下边阴影部分大小，为0时无阴影，此参数影响 {@link #shadowOffsetY} 下边的参数值
     */
    private int shadowPaddingBottom;

    public ShadowLayout(final Context context) {
        this(context, null);
    }

    public ShadowLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
        initShadow();
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        if (attr == null) {
            return;
        }
        try {
            //默认是不显示
            isShadowHidden = attr.getBoolean(R.styleable.ShadowLayout_shadowHidden, true);
            //阴影范围
            shadowRadio = attr.getDimensionPixelSize(R.styleable.ShadowLayout_shadowRadio, 0);
            //颜色
            shadowColor = attr.getColor(R.styleable.ShadowLayout_shadowColor, Color.TRANSPARENT);
            //x轴偏移量
            shadowOffsetX = attr.getDimensionPixelSize(R.styleable.ShadowLayout_shadowOffsetX, 0);
            //y轴偏移量
            shadowOffsetY = attr.getDimensionPixelSize(R.styleable.ShadowLayout_shadowOffsetY, 0);
            //圆角大小
            rectRadio = attr.getDimensionPixelSize(R.styleable.ShadowLayout_rectRadio, 0);
            //左边阴影大小
            shadowPaddingLeft = attr.getDimensionPixelSize(R.styleable.ShadowLayout_shadowPaddingLeft, -1);
            //上边阴影大小
            shadowPaddingTop = attr.getDimensionPixelSize(R.styleable.ShadowLayout_shadowPaddingTop, -1);
            //右边阴影大小
            shadowPaddingRight = attr.getDimensionPixelSize(R.styleable.ShadowLayout_shadowPaddingRight, -1);
            //下边阴影大小
            shadowPaddingBottom = attr.getDimensionPixelSize(R.styleable.ShadowLayout_shadowPaddingBottom, -1);
        } finally {
            attr.recycle();
        }
    }

    private void initShadow() {
        setPadding(getShadowPaddingLeft(), getShadowPaddingTop(),
                getShadowPaddingRight(), getShadowPaddingBottom());
        setWillNotDraw(isShadowHidden);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint.setColor(Color.argb(Color.alpha(shadowColor), Color.red(shadowColor),
                Color.green(shadowColor), Color.blue(shadowColor)));
        mPaint.setMaskFilter(new BlurMaskFilter(shadowRadio, BlurMaskFilter.Blur.NORMAL));
    }

    public int getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
        mPaint.setColor(Color.argb(Color.alpha(shadowColor), Color.red(shadowColor),
                Color.green(shadowColor), Color.blue(shadowColor)));
        invalidate();
    }

    public int getShadowRadio() {
        return shadowRadio;
    }

    public void setShadowRadio(int shadowRadio) {
        this.shadowRadio = shadowRadio;
        mPaint.setMaskFilter(new BlurMaskFilter(shadowRadio, BlurMaskFilter.Blur.NORMAL));
        setPadding(getShadowPaddingLeft(), getShadowPaddingTop(),
                getShadowPaddingRight(), getShadowPaddingBottom());
        invalidate();
    }

    public int getShadowOffsetX() {
        return shadowOffsetX;
    }

    public void setShadowOffsetX(int shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
        setPadding(getShadowPaddingLeft(), getShadowPaddingTop(),
                getShadowPaddingRight(), getShadowPaddingBottom());
        invalidate();
    }

    public int getShadowOffsetY() {
        return shadowOffsetY;
    }

    public void setShadowOffsetY(int shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
        setPadding(getShadowPaddingLeft(), getShadowPaddingTop(),
                getShadowPaddingRight(), getShadowPaddingBottom());
        invalidate();
    }

    public boolean isShadowHidden() {
        return isShadowHidden;
    }

    public void setShadowHidden(boolean shadowHidden) {
        isShadowHidden = shadowHidden;
        setWillNotDraw(isShadowHidden);
        setPadding(getShadowPaddingLeft(), getShadowPaddingTop(),
                getShadowPaddingRight(), getShadowPaddingBottom());
        invalidate();
    }

    private int getShadowPaddingLeft() {
        if (shadowPaddingLeft < 0 || shadowPaddingLeft > shadowRadio) {
            return shadowRadio - shadowOffsetX;
        }
        return shadowPaddingLeft;
    }

    private int getShadowPaddingTop() {
        if (shadowPaddingTop < 0 || shadowPaddingTop > shadowRadio) {
            return shadowRadio - shadowOffsetY;
        }
        return shadowPaddingTop;
    }

    private int getShadowPaddingRight() {
        if (shadowPaddingRight < 0 || shadowPaddingRight > shadowRadio) {
            return shadowRadio + shadowOffsetX;
        }
        return shadowPaddingRight;
    }

    private int getShadowPaddingBottom() {
        if (shadowPaddingBottom < 0 || shadowPaddingBottom > shadowRadio) {
            return shadowRadio + shadowOffsetY;
        }
        return shadowPaddingBottom;
    }

    public int getRectRadio() {
        if (rectRadio < 0) {
            return 0;
        }
        int iRectRadio = (int) (((float) mBoundRect.height() - shadowRadio * 2) / (float) mBoundRect.height() * rectRadio);
        return iRectRadio;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            mBoundRect.set(0, 0, w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isShadowHidden) {
            canvas.drawRoundRect(shadowRadio, shadowRadio, mBoundRect.width() - shadowRadio, mBoundRect.height() - shadowRadio, getRectRadio(), getRectRadio(), mPaint);
        }
    }
}

