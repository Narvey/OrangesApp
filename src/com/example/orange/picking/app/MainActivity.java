package com.example.orange.picking.app;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    @Override 
    public boolean onTouchEvent(MotionEvent event){
    	super.onTouchEvent(event);
    	ImageButton or = new ImageButton(this);
    	or.setImageDrawable(Drawable.createFromPath("res/drawable-hdpi/orangehighlight.png"));
    	return true;
    }
    
    public class Orange extends ImageButton{//TODO may need to remove the extends
    	private Bitmap orange;
    	private int top;
    	private int left;
		public Orange(Context context) {
			super(context);
			orange=BitmapFactory.decodeResource(getResources(), R.drawable.orange);
			
		}
		public void makeHighlighted(){
			orange=BitmapFactory.decodeResource(getResources(), R.drawable.orangehighlight);
		}
		public void makePlaced(){
			orange=BitmapFactory.decodeResource(getResources(), R.drawable.orangeplaced);
		}
    	
    }
}
