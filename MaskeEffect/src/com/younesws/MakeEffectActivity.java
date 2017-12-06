package com.younesws;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MakeEffectActivity extends Activity {

	float mX = 0;

	float mY;

	private int screenWidth;

	private int screenHeight;

	private TouchView mTouchView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTouchView = new TouchView(this);
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    layoutInflater.inflate(R.layout.main, mTouchView);
		setContentView(mTouchView);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		screenWidth = size.x;
		screenHeight = size.y;
		mY = screenHeight;

	}

	class TouchView extends ViewGroup {

		Bitmap overlayDefault;

		Bitmap overlay;

		Paint dragableMask;

		Canvas mCanvas;

		public TouchView(Context context) {
			super(context);
			
			//View.inflate(context, R.layout.main, null);
			
			/*LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        addView(inflater.inflate(R.layout.main, this));*/  
	        
            /*setAlwaysDrawnWithCacheEnabled(true);
            buildDrawingCache();
	        
			overlayDefault = getDrawingCache();
			overlay = overlayDefault.copy(Config.ARGB_8888, true);

			// overlayDefault = BitmapFactory.decodeResource(getResources(),
			// R.drawable.sonic_bg);
			//overlay = BitmapFactory.decodeResource(getResources(),
			//		R.drawable.sonic_bg).copy(Config.ARGB_8888, true);

			mCanvas = new Canvas(overlay);
			dragableMask = new Paint(Paint.ANTI_ALIAS_FLAG);
			dragableMask.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
			dragableMask.setColor(Color.TRANSPARENT);*/
		}

		@Override
		public boolean onTouchEvent(MotionEvent ev) {

			switch (ev.getAction()) {

			case MotionEvent.ACTION_DOWN: {

				mY = (int) ev.getY();
				invalidate();
				break;
			}

			case MotionEvent.ACTION_MOVE: {

				mY = (int) ev.getY();
				invalidate();
				break;

			}

			case MotionEvent.ACTION_UP:
				break;
			}
			return true;
		}

		@Override
		public void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			/*mCanvas.drawBitmap(overlayDefault, 0, 0, null);
			mCanvas.drawRect(mX, mY, screenWidth, screenHeight, dragableMask);
			canvas.drawBitmap(overlay, 0, 0, null);*/
		}

		@Override
		protected void dispatchDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.dispatchDraw(canvas);
		}
		
		@Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			/*for(int i = 0 ; i < getChildCount() ; i++){
                getChildAt(i).layout(l, t, r, b);
            }*/
			
		}

		
	}
}
