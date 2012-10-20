package com.example.orange.picking.app;

import java.util.Random;
import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class OrangePick extends Activity {
	private Orange[] tree;
	public static final int NUMORANGES=3;
	public static final String TAG = "OrangePick";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tree = new Orange[NUMORANGES];
		FrameLayout frame = (FrameLayout) findViewById(R.id.framer);
		for(int i = 0; i<NUMORANGES; i++) {
			tree[i] = new Orange(this);
			frame.addView(tree[i]);
		}
	}


	@Override 
	public boolean onTouchEvent(MotionEvent event){
		super.onTouchEvent(event);
		for(Orange each : tree){
			each.handleTouch(event);
		}
		// tell the system that we handled the event and no further processing is required
		return true; 
	}

	@Override
	protected void onResume() {
		super.onResume();
		tree = new Orange[NUMORANGES];
		FrameLayout frame = (FrameLayout) findViewById(R.id.framer);
		frame.removeAllViews();
		for(int i = 0; i<NUMORANGES; i++) {
			tree[i] = new Orange(this);
			frame.addView(tree[i]);
		}
	}

	public class Orange extends View{//TODO may need to remove the extends
		private Bitmap orange;
		private int top;
		private int left;
		protected boolean moving;
		protected boolean done;/// True if the orange is stuck in the basket. 
		public Orange(Context context) {
			super(context);
			orange=BitmapFactory.decodeResource(getResources(), R.drawable.orange);
			Random r = new Random();
			top = r.nextInt(200);
			left = r.nextInt(100);
			invalidate();
		}
		public void makeHighlighted(){
			//if()
			Log.d(TAG, "Orange has been pressed");
			orange=BitmapFactory.decodeResource(getResources(), R.drawable.orangehighlight);
			moving = true;
			invalidate();
		}
		public void makeNormal(){
			Log.d(TAG, "Orange has been released");
			orange=BitmapFactory.decodeResource(getResources(), R.drawable.orange);
			invalidate();
		}
		public void makePlaced(){
			Log.d(TAG, "Orange has been placed in basket!");
			orange=BitmapFactory.decodeResource(getResources(), R.drawable.orangeplaced);
			moving = false;//should be already, but just in case.
			done = true;
			invalidate();
		}
		/**
		 * 
		 * @param x
		 * @param y
		 * @return true if the orange was successfully moved.
		 */
		public boolean moveTo(int x, int y){
			if(x<0||y<0)return false;
			top=y-72;
			left=x;
			invalidate();
			return true;
		}


		public void handleTouch(MotionEvent event){
			int eventaction=event.getAction();

			switch (eventaction) {
			case MotionEvent.ACTION_DOWN: 
				if(!done&&event.getX()>=left&&event.getX()<=getR()
				&&event.getY()-72>=top&&event.getY()-72<=getB()){
					moving = true;
					makeHighlighted();
				}
				break;

			case MotionEvent.ACTION_MOVE:
				if(moving){
					Log.v(TAG, "moving orange");
					moveTo((int)event.getX(), (int)event.getY());
				}
				break;

			case MotionEvent.ACTION_UP:   
				// finger leaves the screen
				if(moving)Log.v(TAG,"Orange dropped at ("+left+","+top+")");
				moving = false;
				if(top>510&&left>=150&&left<=275){
					makePlaced();
					done=true;
				}else makeNormal();
				break;
			}

		}
		public int getR(){
			return left+orange.getWidth();
		}
		public int getB(){
			return top+orange.getHeight();
		}

		/**
		 * Should draw the image based on it's current condition.
		 * @see android.widget.View#onDraw(android.graphics.Canvas)
		 */
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawBitmap(orange, left, top, null);
		}

	}
}
