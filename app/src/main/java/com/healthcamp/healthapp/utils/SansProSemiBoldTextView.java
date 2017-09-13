package com.healthcamp.healthapp.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ITH-143 on 09-Sep-17.
 */
public class SansProSemiBoldTextView extends TextView {

    public SansProSemiBoldTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "SourceSansPro-Semibold.ttf");
        this.setTypeface(face);
    }

    public SansProSemiBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "SourceSansPro-Semibold.ttf");
        this.setTypeface(face);
    }

    public SansProSemiBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "SourceSansPro-Semibold.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}