package com.example.orange.picking.app;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void MoveOrange(View view){
    	ImageButton or = (ImageButton) view;
    	or.setImageDrawable(Drawable.createFromPath("res/drawable-hdpi/orangehighlight.png"));
    }
    
}
