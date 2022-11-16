package com.clock;

import static java.lang.Math.PI;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.Calendar;


public class Clock extends View {

    private int radius = 0;
    private Paint paint;
    private boolean isInit;
    private float centerX;
    private float centerY;
    private LineParameters hourHand;
    private LineParameters minuteHand;
    private LineParameters secondHand;

    public Clock(Context context) {
        super(context);
    }

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Clock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initClock() {
        int height = getHeight();
        int width = getWidth();

        int padding = 50;
        int min = Math.min(height, width);
        radius = min/2 - padding;


        paint = new Paint();
        isInit = true;
        centerX = width / 2f;
        centerY = height /2f;

        hourHand = new LineParameters(14, radius* 0.3f, radius* 0.7f, Color.BLACK);
        minuteHand = new LineParameters(11, radius* 0.3f, radius* 0.5f, Color.RED);
        secondHand = new LineParameters(7, radius* 0.2f, radius* 0.3f, Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            initClock();
        }

        canvas.drawColor(Color.WHITE);
        drawCircle(canvas);
        drawHands(canvas);
        drawThicks(canvas);


        postInvalidateDelayed(500);
        invalidate();
    }

    private void drawThicks(Canvas canvas) {
        float lineLength = 30;

        float innerRadius = radius - lineLength;
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(15);
        for(int i = 0; i < 12; ++i) {
           double angleInDegrees = i*360d/12;
           double angleInRad = Math.toRadians(angleInDegrees);
           double cos = Math.cos(angleInRad);
           double sin = Math.sin(angleInRad);
           float startX = (float) (innerRadius* cos) + centerX;
           float startY = (float) (innerRadius* sin) + centerY;
           float endX = (float) (radius* cos) + centerX;
           float endY = (float) (radius* sin) + centerY;

           canvas.drawLine(startX, startY,
                     endX, endY,
                     paint);
        }
    }

    private void drawHand(Canvas canvas, double loc, LineParameters lineParameters) {
        float overFlow = lineParameters.getOverFlow();
        float handRadius = lineParameters.getLength();
        paint.setColor(lineParameters.getColor());
        paint.setStrokeWidth(lineParameters.getThickness());

        double angle = PI * loc / 30 - PI / 2;
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        canvas.drawLine((float) (centerX - cos* overFlow), (float) (centerY - sin* overFlow),
                (float) (centerX + cos * handRadius),
                (float) (centerY + sin * handRadius),
                paint);
    }
    private void drawHands(Canvas canvas) {
        Calendar c = Calendar.getInstance();
        float hour = c.get(Calendar.HOUR_OF_DAY);
        hour = hour > 12 ? hour - 12 : hour;
        drawHand(canvas, (hour + c.get(Calendar.MINUTE) / 60f) * 5f, hourHand);
        paint.setColor(Color.RED);
        drawHand(canvas, c.get(Calendar.MINUTE), minuteHand);
        paint.setColor(Color.BLUE);
        drawHand(canvas, c.get(Calendar.SECOND), secondHand);
    }

    private void drawCircle(Canvas canvas) {
        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setStrokeWidth(12);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(centerX, centerY, radius, paint);
    }
}
