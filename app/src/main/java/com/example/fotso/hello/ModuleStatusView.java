package com.example.fotso.hello;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.opengl.GLES30;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class ModuleStatusView extends View {
    private static final int EDIT_MODE_MODULE_COUNT = 8 ;

    float mOutlineWidth, mShapeSize,mSpacing,mRadius ;
    int mOutlineColor ;
    public Paint mPaintOutline;
    Paint mPaintFill;
    int fillColor;
    int maxHorizontaleModules;

    public boolean[] getmModuleStatus() {
        return mModuleStatus;
    }

    public void setmModuleStatus(boolean[] mModuleStatus) {
        this.mModuleStatus = mModuleStatus;
    }

    private boolean[]  mModuleStatus;
    Rect[] mModuleRectangles;

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
        // TODO 6:
        if(isInEditMode())
            setupEditModeValues();

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ModuleStatusView, defStyle, 0);

        a.recycle();

                /*
           TODO (1)

           */
        mOutlineWidth = 6f;
        mShapeSize = 120f;
        mSpacing = 135f;
        mRadius = (mShapeSize - mOutlineWidth) / 2;

       // setupModuleRectangles();
                /*
           TODO (3)
           */
        mOutlineColor = Color.BLACK;
        mPaintOutline = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintOutline.setStyle(Paint.Style.STROKE);
        mPaintOutline.setStrokeWidth(mOutlineWidth);
        mPaintOutline.setColor(mOutlineColor);

            /*
       TODO (4)
       color fill my round property
       */
        fillColor = getContext().getResources().getColor(R.color.my_orange);
        mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(fillColor);

    }

    private void setupEditModeValues() {
        boolean[] examples = new boolean[EDIT_MODE_MODULE_COUNT];
        int middle = EDIT_MODE_MODULE_COUNT / 2;

        for (int i = 0; i < middle; i++)
            examples[i]= true;

        setmModuleStatus(examples);
    }

    /*
    TODO (2)
    creation of rectangle
    */
    private void setupModuleRectangles(int width) {
        //TODO 9 adapt to grid given responsive style width new width
        int avialableWidth = width - getPaddingRight() - getPaddingLeft();
        int horizontalModulesThatCanFit = (int)(avialableWidth / (mShapeSize + mSpacing)) ;
        int  maxHorizontalModules = Math.min(horizontalModulesThatCanFit,mModuleStatus.length);

        mModuleRectangles = new Rect[mModuleStatus.length];
        // i represent de current module
        for (int i = 0; i < mModuleRectangles.length ; i++) {

            //TODO 8 adapt to grid content positionning
            int colunm = i % maxHorizontalModules;
            int row =   i / maxHorizontalModules;

            //int x = getPaddingLeft() + (int)(i*(mShapeSize + mSpacing));
            //int y = getPaddingTop();

            int x = getPaddingLeft() + (int)(colunm*(mShapeSize + mSpacing));
            int y = getPaddingTop() + (int)(row*(mShapeSize + mSpacing));

            mModuleRectangles[i] = new Rect(x,y,(int) mShapeSize,(int) mShapeSize);

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        setupModuleRectangles(w);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO (5):
        for (int i = 0; i < mModuleRectangles.length ; i++) {
            float x = mModuleRectangles[i].centerX();
            float y =  mModuleRectangles[i].centerY();

            if (mModuleStatus[i])
                canvas.drawCircle(x,y,mRadius,mPaintFill);

            canvas.drawCircle(x,y,mRadius,mPaintOutline);

        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desireWidth=0,desireHeight=0;
        //TODO 7
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int avialableWidth = specWidth -getPaddingLeft() - getPaddingRight();
        int horizontalModuleThatCanFit = (int)(avialableWidth / (mShapeSize + mSpacing)) ;

        maxHorizontaleModules = Math.min(horizontalModuleThatCanFit,mModuleStatus.length);

        //desireWidth = (int)(mModuleStatus.length*(mShapeSize + mSpacing) - mSpacing);
        desireWidth = (int)(maxHorizontaleModules*(mShapeSize + mSpacing) - mSpacing);
        desireWidth+=(getPaddingLeft()+ getPaddingRight());

        //number pf rows
        int rows = ((mModuleStatus.length -1 )/ maxHorizontaleModules) +1;
        desireHeight =(int)(rows*(mShapeSize + mSpacing) - mSpacing);


        int width = resolveSizeAndState(desireWidth,widthMeasureSpec,0);
        int height = resolveSizeAndState(desireHeight,heightMeasureSpec,0);

        setMeasuredDimension(width,height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
         switch (event.getAction()){
             case MotionEvent.ACTION_DOWN:
                 return true;
             case MotionEvent.ACTION_UP:
                 int moduleIndex =findItemAtpoint(event.getX(),event.getY());
                 return true;


         }
        return super.onTouchEvent(event);
    }

    private int findItemAtpoint(float x, float y) {
        int moduleIndex = GLES30.GL_INVALID_INDEX;
        for (int i = 0 ; i < mModuleRectangles.length; i++){
            if(mModuleRectangles[i].contains((int)x,(int)y)){
                moduleIndex = i;
                break;

            }
        }
        return moduleIndex;
    }
}
