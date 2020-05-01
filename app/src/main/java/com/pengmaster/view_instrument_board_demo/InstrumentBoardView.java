package com.pengmaster.view_instrument_board_demo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : Wp
 *     e-mail : 1101313414@qq.com
 *     time   : 2020-05-01 13:48
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class InstrumentBoardView extends View {

    private Paint paint;
    private Path path;
    private static float RADIUS = px2dp(100);
    private static float ANGLE = 45f;
    private static int SCALE_COUNT = 12;
    private static int POINTER_LENGTH = (int)px2dp(60);
    private PathDashPathEffect dashPathEffect;

    public InstrumentBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(px2dp(2));
        //仪表盘弧长
        path = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        path.addArc((float) getWidth() / 2 - RADIUS, (float)getHeight() / 2 - RADIUS,
                (float)getWidth() / 2 + RADIUS, (float)getHeight() / 2 + RADIUS
                ,ANGLE + 90, 360 - 2 * ANGLE);
        //刻度矩形大小
        Path rectPath = new Path();
        rectPath.addRect(0,0,(int)px2dp(2),(int)px2dp(10), Path.Direction.CW);
        //刻度间距
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path,false);
        int advance = ((int)pathMeasure.getLength() -(int)px2dp(2))  / SCALE_COUNT;
        dashPathEffect = new PathDashPathEffect(rectPath,advance,0,PathDashPathEffect.Style.ROTATE);


    }

    private static float px2dp(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                Resources.getSystem().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(path, paint);

        paint.setPathEffect(dashPathEffect);
        canvas.drawPath(path, paint);

        paint.setPathEffect(null);

        //画指针
        float stopX = getWidth() / 2f - (float)(Math.cos(Math.toDegrees(ANGLE)) * POINTER_LENGTH);
        float stopY = getHeight() / 2f - (float)(Math.sin(Math.toDegrees(ANGLE)) * POINTER_LENGTH);
        canvas.drawLine(getWidth() / 2 , getHeight() / 2 ,stopX , stopY , paint);

        //画指针2
        float stopX2 = getWidth() / 2f - (float)(Math.cos(Math.toDegrees(ANGLE + 35)) * (POINTER_LENGTH + 10));
        float stopY2 = getHeight() / 2f - (float)(Math.sin(Math.toDegrees(ANGLE + 35)) * (POINTER_LENGTH + 10));
        canvas.drawLine(getWidth() / 2 , getHeight() / 2 ,stopX2 , stopY2 , paint);
    }
}
