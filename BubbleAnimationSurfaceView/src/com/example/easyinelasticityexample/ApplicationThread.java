package com.example.easyinelasticityexample;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ApplicationThread extends Thread {

	private boolean mRun;
	private SurfaceHolder mSurfaceHolder;
	private MySurfaceView mSurfaceView;

	public ApplicationThread(SurfaceHolder surfaceHolder, MySurfaceView surfaceView) {
		this.mSurfaceHolder = surfaceHolder;
		this.mSurfaceView = surfaceView;
	}

	public void setRunning(boolean run) {
		this.mRun = run;
	}

	public SurfaceHolder getSurfaceHolder() {
		return mSurfaceHolder;
	}

	@Override
	public void run() {
		Canvas c;
		while (mRun) {
			c = null;
			try {
				c = mSurfaceHolder.lockCanvas(null);
				synchronized (mSurfaceHolder) {
					mSurfaceView.updatePhysics();
					mSurfaceView.onDraw(c);
					mSurfaceView.postInvalidate();
				}
			} finally {
				if (c != null) {
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
}
