package com.example.orange.picking.app;

import java.util.Random;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class OrangePick extends Activity {
	Orange[] tree;
	@SuppressWarnings("unused")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tree = new Orange[3];
		FrameLayout frame = (FrameLayout) findViewById(R.id.framer);
		for(Orange each : tree) {each = new Orange(this);
		frame.addView(each);
		}
	}

	@Override 
	public boolean onTouchEvent(MotionEvent event){
		super.onTouchEvent(event);
		int eventaction = event.getAction();
		for(Orange each : tree){
			each.handleTouch(event);
		}
		// tell the system that we handled the event and no further processing is required
		return true; 
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
			top = r.nextInt(60);
			left = r.nextInt(60);
			invalidate();
		}
		public void makeHighlighted(){
			orange=BitmapFactory.decodeResource(getResources(), R.drawable.orangehighlight);
			moving = true;
			invalidate();
		}
		public void makeNormal(){
			orange=BitmapFactory.decodeResource(getResources(), R.drawable.orange);
			invalidate();
		}
		public void makePlaced(){
			orange=BitmapFactory.decodeResource(getResources(), R.drawable.orangeplaced);
			moving = false;//should be already, but just in case.
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
			top=y;
			left=x;
			invalidate();
			return true;
		}


		public void handleTouch(MotionEvent event){
			int eventaction=event.getAction();
			switch (eventaction) {
			case MotionEvent.ACTION_DOWN: 
				if(!done){
					moving = true;
					makeHighlighted();
				}
				break;

			case MotionEvent.ACTION_MOVE:
				if(moving){
					moveTo((int)event.getX(), (int)event.getY());
				}
				break;

			case MotionEvent.ACTION_UP:   
				// finger leaves the screen
				moving = false;
				break;
			}

		}

		/* (non-Javadoc)
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
