
package com.example.easyinelasticityexample;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.Animation;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private ApplicationThread mThread;

    private Paint mBubblePaint;

    private float mXpos = 0;

    private float mYpos = 0;

    private int mRadius = 200;

    private float mCenterPointX;

    private float mCenterPointY;

    private ValueAnimator mValueAnimator;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        mThread = new ApplicationThread(getHolder(), this);
        setFocusable(true);
    }

    public void surfaceCreated(SurfaceHolder holder) {

        setBackgroundColor(Color.BLACK);
        mBubblePaint = new Paint();
        mBubblePaint.setColor(Color.WHITE);

        mCenterPointX = getWidth() / 2;
        mCenterPointY = getHeight() / 2;

        mThread.setRunning(true);
        mThread.start();

        mValueAnimator = ValueAnimator.ofInt(200, 350);
        mValueAnimator.setDuration(3000);
        mValueAnimator.setRepeatCount(Animation.INFINITE);
        mValueAnimator.setRepeatMode(Animation.REVERSE);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer)animation.getAnimatedValue();
                mRadius = value.intValue();
                //invalidate();
            }
        });
        mValueAnimator.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mThread.setRunning(false);
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // Handle later
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null) {
            canvas.drawCircle(mCenterPointX + mXpos, mCenterPointY + mYpos, mRadius, mBubblePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (mThread.getSurfaceHolder()) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                mXpos = event.getX() - this.getWidth() / 2;
                mYpos = event.getY() - this.getHeight() / 2;
                updatePhysics();
            }
        }
        return true;
    }

    public void updatePhysics() {

        /*
         * mMovementX = mDistanceX / mSpeed; mMovementY = mDistanceY / mSpeed;
         *
         * float newX = getBitmapX() + mMovementX; float newY = getBitmapY() +
         * mMovementY;
         *
         * setBimapX(newX); setBitmapY(newY);
         *
         * setBitmap2X(newX2); setBitmap2Y(newY2);
         */
    }

}
