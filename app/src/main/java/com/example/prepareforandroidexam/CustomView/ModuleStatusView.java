package com.example.prepareforandroidexam.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.prepareforandroidexam.R;

/**
 * TODO: document your custom view class.
 */
public class ModuleStatusView extends View {

    private float outlineWidth;
    private float shapeSize;
    private float spacing;
    private Paint mOutlinePaint;
    private int mOutlineColor;
    private Paint mFillPaint;
    private int mFillColor;
    private Rect[] mModuleRectangles;
    private float radius;
    private int  EDIT_MODE_MODULE_COUNT = 7;
    private int maxHorizontalModules;
    private int  INVALID_INDEX = -1;;
    private int shape_circle;
    private int mShape;
    private float DEFAULT_OUTLINE_WIDTH_DP;

    public boolean[] getmModuleStatus() {
        return mModuleStatus;
    }

    public void setmModuleStatus(boolean[] mModuleStatus) {
        this.mModuleStatus = mModuleStatus;
    }

    private boolean[] mModuleStatus;



    public ModuleStatusView(Context context) {
        super(context);
        init(null, 0);
    }

    public ModuleStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ModuleStatusView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        if(isInEditMode()){
            setUpEditModeValues();
        }
        DisplayMetrics displayMetrics=getContext().getResources().getDisplayMetrics();
        float displayDensity=displayMetrics.density;


        DEFAULT_OUTLINE_WIDTH_DP = 2f;
        float defaultOutlineWidthPixels=displayDensity* DEFAULT_OUTLINE_WIDTH_DP;

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ModuleStatusView, defStyle, 0);
        mOutlineColor = a.getColor(R.styleable.ModuleStatusView_outlineColor,Color.BLACK);
        shape_circle = 0;
        mShape = a.getInt(R.styleable.ModuleStatusView_shape, shape_circle);
        outlineWidth = a.getDimension(R.styleable.ModuleStatusView_outlineWidth,defaultOutlineWidthPixels);
        a.recycle();


        shapeSize = 144f;
        spacing = 30f;
        radius = (shapeSize-outlineWidth)/2;



        mOutlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOutlinePaint.setStyle(Paint.Style.STROKE);
        mOutlinePaint.setStrokeWidth(outlineWidth);
        mOutlinePaint.setColor(mOutlineColor);

        mFillColor = Color.RED;
        mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setColor(mFillColor);
    }

    private void setUpEditModeValues() {
        boolean[] exampleModuleValues=new boolean[EDIT_MODE_MODULE_COUNT];
        int middle=(exampleModuleValues.length/2);
        for(int i =0;i<middle;i++){
            exampleModuleValues[i]=true;
        }
        setmModuleStatus(exampleModuleValues);
    }

    private void setUpModuleRectangles(int width) {
        int availableWidth=width-getPaddingLeft()-getPaddingRight();
        int horizontalModulesThatCanFit=(int)(availableWidth/(shapeSize+spacing));
        int maxHorizontalModules=Math.min(horizontalModulesThatCanFit,mModuleStatus.length);


        mModuleRectangles = new Rect[mModuleStatus.length];
        for(int moduleIndex = 0; moduleIndex< mModuleRectangles.length; moduleIndex++){

            int column=moduleIndex%maxHorizontalModules;
            int row=moduleIndex/maxHorizontalModules;

            int x=getPaddingLeft()+(int)(column*(shapeSize+spacing));
            int y=getPaddingTop()+(int)(row*(shapeSize+spacing));
            Rect newRect=new Rect(x,y,x+(int)shapeSize,y+(int)shapeSize);
            mModuleRectangles[moduleIndex]=newRect;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:return true;
            case MotionEvent.ACTION_UP:
                int moduleIndex=fndItemAtPoint(event.getX(),event.getY());
                onModuleSelected(moduleIndex);
                return true;

        }
        return  super.onTouchEvent(event);
    }

    private  void onModuleSelected(int moduleIndex){
        if(moduleIndex==INVALID_INDEX){
            return;
        }
        mModuleStatus[moduleIndex]=!mModuleStatus[moduleIndex];
        invalidate();
    }

    private int fndItemAtPoint(float x,float y ){

        int moduleIndex=INVALID_INDEX;
        for(int i=0; i<mModuleRectangles.length;i++){
            if(mModuleRectangles[i].contains((int)x,(int)y)){
                moduleIndex=i;
                break;
            }
        }
        return  moduleIndex;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setUpModuleRectangles(w);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int moduleIndex=0;moduleIndex<mModuleStatus.length;moduleIndex++) {
            if(mShape==shape_circle){
            float x = mModuleRectangles[moduleIndex].centerX();
            float y = mModuleRectangles[moduleIndex].centerY();
            if (mModuleStatus[moduleIndex])
                canvas.drawCircle(x, y, radius, mFillPaint);
            canvas.drawCircle(x, y, radius, mOutlinePaint);
        }else{
                drawSquare(canvas,moduleIndex);
            }


    }}

    private void drawSquare(Canvas canvas,int moduleIndex){
        Rect moduleRectangles=mModuleRectangles[moduleIndex];

        if(mModuleStatus[moduleIndex]){
            canvas.drawRect(moduleRectangles,mFillPaint);
        }
        canvas.drawRect(moduleRectangles.left+(outlineWidth/2),moduleRectangles.top+(outlineWidth/2),moduleRectangles.right+(outlineWidth/2),moduleRectangles.bottom+(outlineWidth/2),mOutlinePaint);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int specWidth=MeasureSpec.getSize(widthMeasureSpec);
        int availableWidth=specWidth-getPaddingLeft()-getPaddingRight();
        int horizontalModulesThatCanFit=(int)(availableWidth/(shapeSize+spacing));
        maxHorizontalModules = Math.min(horizontalModulesThatCanFit,mModuleStatus.length);

        int rows=((mModuleStatus.length-1)/ maxHorizontalModules)+1;
        int desiredWidth=0;
        int desiredHeight=0;

        desiredWidth=(int)((mModuleStatus.length*(shapeSize+spacing))-spacing);
        desiredWidth+=getPaddingLeft()+getPaddingRight();

        desiredHeight=(int)((rows*(shapeSize+spacing))-spacing);
        desiredHeight+=getPaddingTop()+getPaddingBottom();

        int width=resolveSizeAndState(desiredWidth,widthMeasureSpec,0);
        int height=resolveSizeAndState(desiredHeight,heightMeasureSpec,0);
        setMeasuredDimension(width,height);
    }
}
