
package com.example.easyinelasticityexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private ApplicationThread mThread;

    private ArrayList<Bitmap> mGraphics = new ArrayList<Bitmap>();

    private float mCenterX = 0;

    private float mCenterY = 0;

    private Bitmap mBitmap;

    private float mOffsetX = 0;

    private float mOffsetY = 0;

    private float mDistanceX = 0;

    private float mDistanceY = 0;

    private float mBitmapXpos = 0;

    private float mBitmapYpos = 0;

    private float mMovementX = 0;

    private float mMovementY = 0;

    private float mX;

    private float mY;

    private float mSpeed = 10;

    /**** Bitmap 2 *************/
    private float mSpring = 0.08f;

    private float mAccelerationX = 0;

    private float mVelocityX = 0;

    private float mFriction = 0.9f;

    private float mBitmap2Xpos = 0;

    private Bitmap mBitmapTwo;

    private float mBitmap2Ypos;

    private float mAccelerationY;

    private float mVelocityY;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        mThread = new ApplicationThread(getHolder(), this);
        setFocusable(true);
    }

    public void surfaceCreated(SurfaceHolder holder) {

        this.setBackgroundResource(R.drawable.sonic_bg_green);

        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;

        /* Bitmap 1 */
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sonic_hovering_60x71);

        mOffsetX = mBitmap.getWidth() / 2;
        mOffsetY = mBitmap.getHeight() / 2;

        setBimapX(mCenterX - mOffsetX);
        setBitmapY(mCenterY - mOffsetY);

        set_x(getBitmapX());
        set_y(getBitmapY());

        /* Bitmap 2 */
        mBitmapTwo = BitmapFactory.decodeResource(getResources(), R.drawable.tails_flying_86x71);
        setBitmap2X(getBitmapX() + mBitmap.getWidth());
        setBitmap2Y(getBitmapY() + mBitmap.getHeight());

        mGraphics.add(mBitmap);
        mGraphics.add(mBitmapTwo);

        mThread.setRunning(true);
        mThread.start();
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
            canvas.drawBitmap(mBitmapTwo, getBitmap2X(), getBitmap2Y(), null);
            canvas.drawBitmap(mBitmap, getBitmapX(), getBitmapY(), null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (mThread.getSurfaceHolder()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                set_x(event.getX());
                set_y(event.getY());
                updatePhysics();
                // Toast.makeText(mCtx, "_x _y: " + get_x() + " " + get_y(),
                // Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }

    public void updatePhysics() {

        /* Easy-in effect */
        mDistanceX = get_x() - getBitmapX();
        mDistanceY = get_y() - getBitmapY();

        mMovementX = mDistanceX / mSpeed;
        mMovementY = mDistanceY / mSpeed;

        float newX = getBitmapX() + mMovementX;
        float newY = getBitmapY() + mMovementY;

        setBimapX(newX);
        setBitmapY(newY);

        /* Elasticity effect*/
        mAccelerationX = (get_x() - getBitmap2X()) * mSpring;
        mVelocityX += mAccelerationX;
        mVelocityX *= mFriction;

        mAccelerationY = (get_y() - getBitmap2Y()) * mSpring;
        mVelocityY += mAccelerationY;
        mVelocityY *= mFriction;

        float newX2 = getBitmap2X();
        newX2 += mVelocityX;

        float newY2 = getBitmap2Y();
        newY2 += mVelocityY;

        setBitmap2X(newX2);
        setBitmap2Y(newY2);
    }

    private void setBitmapY(float x) {
        mBitmapXpos = x;
    }

    private void setBimapX(float y) {
        mBitmapYpos = y;
    }

    private float getBitmapY() {
        return mBitmapXpos;
    }

    private float getBitmapX() {
        return mBitmapYpos;
    }

    private void setBitmap2X(float newX2) {
        mBitmap2Xpos = newX2;
    }

    private float getBitmap2X() {
        return mBitmap2Xpos;
    }

    private void setBitmap2Y(float newY2) {
        mBitmap2Ypos = newY2;
    }

    private float getBitmap2Y() {
        return mBitmap2Ypos;
    }

    public float get_x() {
        return mX;
    }

    public void set_x(float x) {
        mX = x;
    }

    public float get_y() {
        return mY;
    }

    public void set_y(float y) {
        mY = y;
    }
}
