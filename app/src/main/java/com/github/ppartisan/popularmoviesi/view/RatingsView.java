package com.github.ppartisan.popularmoviesi.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import android.view.View;

import com.github.ppartisan.popularmoviesi.R;

public class RatingsView extends View {

    private float score = 50;

    private Paint mOutlinePaint, mFillPaint;

    public RatingsView(Context context) {
        super(context);
        init();
    }

    public RatingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RatingsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressWarnings("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatingsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        final int accentColor = ContextCompat.getColor(getContext(), R.color.accent);

        mOutlinePaint = new Paint();
        mOutlinePaint.setColor(accentColor);
        mOutlinePaint.setStyle(Paint.Style.STROKE);

        mFillPaint = new Paint();
        mFillPaint.setColor(accentColor);
        mFillPaint.setStyle(Paint.Style.FILL);
    }

    public void setScore(double score) {
        if (score < 0 || score > 10) {
            throw new IllegalArgumentException("Score must range from 0 to 10");
        }
        this.score = (float) score;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int height = resolveSize(widthSize/20, heightMeasureSpec);

        setMeasuredDimension(widthSize, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mOutlinePaint);

        final float barWidth = getMeasuredWidth()*(score/10);
        canvas.drawRect(0, 0, barWidth, getMeasuredHeight(), mFillPaint);

    }
}
