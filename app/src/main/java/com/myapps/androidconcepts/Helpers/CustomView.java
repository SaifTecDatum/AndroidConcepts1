package com.myapps.androidconcepts.Helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import com.myapps.androidconcepts.R;

import java.util.Timer;
import java.util.TimerTask;

public class CustomView extends View {
    private static final int SQUARE_SIZE_DEF = 200;
    private Rect mRectSquare;
    private Paint mPaintSquare, mPaintCircle;
    private int mSquareColor, mSquareSize;
    private float mCircleX, mCircleY;
    private float mCircleRadius = 100f;
    private Bitmap bitmap_Image;


//linksForThisCustomViews :
    //part1 : https://www.youtube.com/watch?v=sb9OEl4k9Dk
    //part2 : https://www.youtube.com/watch?v=cfwO0Yui43g
    //part3 : https://www.youtube.com/watch?v=BxBcs1ddEn8


    public CustomView(Context context) {
        super(context);

        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        mRectSquare = new Rect();
        mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setColor(Color.parseColor("#00CCFF"));

        bitmap_Image = BitmapFactory.decodeResource(getResources(), R.drawable.img_bckgrnd_1);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                int padding = 50;
                bitmap_Image = getResizedBitmap(bitmap_Image, getWidth() - padding, getHeight() - padding);

                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        int newWidth = bitmap_Image.getWidth() - 50;
                        int newHeight = bitmap_Image.getHeight() - 50;

                        if (newWidth <= 0 || newHeight <= 0) {
                            cancel();
                            return;
                        }

                        bitmap_Image = getResizedBitmap(bitmap_Image, newWidth, newHeight);
                        postInvalidate();
                    }
                }, 5000, 500);
            }
        });

        if (set == null)
            return;

        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.CustomView);
        mSquareColor = ta.getColor(R.styleable.CustomView_square_Color, Color.GREEN);
        mSquareSize = ta.getDimensionPixelSize(R.styleable.CustomView_square_Size, SQUARE_SIZE_DEF);

        mPaintSquare.setColor(mSquareColor);

        ta.recycle();
    }

    public void swapColor() {
        mPaintSquare.setColor(mPaintSquare.getColor() == mSquareColor ? Color.RED : mSquareColor);

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mRectSquare.left = 50;
        mRectSquare.top = 50;
        mRectSquare.right = mRectSquare.left + mSquareSize;
        mRectSquare.bottom = mRectSquare.top + mSquareSize;

        canvas.drawRect(mRectSquare, mPaintSquare);

        if (mCircleX == 0f || mCircleY == 0f) {     //fromHereToBottomWeDiscussedAbtCircle.
            mCircleX = getWidth() / 2;
            mCircleY = getHeight() / 2;
        }

        canvas.drawCircle(mCircleX, mCircleY, mCircleRadius, mPaintCircle);

        float imageX = (getWidth() - bitmap_Image.getWidth()) / 2;
        float imagey = (getHeight() - bitmap_Image.getHeight()) / 2;

        canvas.drawBitmap(bitmap_Image, imageX, imagey, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { //thisMethodBelongsToCircleWchHave2switchCaseStatements.

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {     //byThisStatementCircleEnlargesWhileClickingOnSquareBox.
                float x = event.getX();
                float y = event.getY();

                if (mRectSquare.left < x && mRectSquare.right > x)
                    if (mRectSquare.top < y && mRectSquare.bottom > y) {
                        mCircleRadius += 10f;

                        postInvalidate();
                    }
                return true;
            }

            case MotionEvent.ACTION_MOVE: {     //byThisStatementCircleMovesWhileTouchingOnIt.
                float x = event.getX();
                float y = event.getY();

                double dx = Math.pow(x - mCircleX, 2);
                double dy = Math.pow(y - mCircleY, 2);

                if (dx + dy < Math.pow(mCircleRadius, 2)) {
                    //Touched
                    mCircleX = x;
                    mCircleY = y;

                    postInvalidate();

                    return true;
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private Bitmap getResizedBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
        Matrix matrix = new Matrix();

        RectF src = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF dst = new RectF(0, 0, reqWidth, reqHeight);

        matrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

}