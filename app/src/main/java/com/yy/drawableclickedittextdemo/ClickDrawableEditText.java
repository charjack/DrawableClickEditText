package com.yy.drawableclickedittextdemo;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ClickDrawableEditText extends android.support.v7.widget.AppCompatEditText {
    private static String tag= ClickDrawableEditText.class.getSimpleName();
    //点击位置
    private int positionX = 0;
    private int positionY;
    //
    private OnDrawableClickListener onDrawableClickListener;

    public ClickDrawableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ClickDrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnDrawableClickListener(OnDrawableClickListener onDrawableClickListener) {
        this.onDrawableClickListener = onDrawableClickListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //图标区域
        Rect bounds;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            positionX = Math.round(event.getX());
            positionY = Math.round(event.getY());

            Drawable[] ds=this.getCompoundDrawables();
            Drawable drawableRight = ds[2];
            Drawable drawableLeft = ds[0];
            Drawable drawableTop = ds[1];
            Drawable drawableBottom = ds[3];

            //计算点击点在Drawable中的位置
            int xClickPosition,yClickPosition;
            //长宽
            int w=getWidth();
            int h=getHeight();

            //文本中心的坐标
            int midX=(w-getTotalPaddingLeft()-getTotalPaddingRight())/2+getTotalPaddingLeft();
            int midY=(h-getTotalPaddingTop()-getTotalPaddingBottom())/2+getTotalPaddingTop();

            if (drawableLeft != null) {
                bounds = drawableLeft.getBounds();
                //
                xClickPosition = positionX-getPaddingLeft();
                yClickPosition = (midY-positionY)+bounds.height()/2;

                if (bounds.contains(xClickPosition, yClickPosition) && onDrawableClickListener != null) {
                    onDrawableClickListener.onClick(DrawablePosition.LEFT);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return false;
                }
            }

            if (drawableRight != null) {
                bounds = drawableRight.getBounds();

                xClickPosition = positionX - w + bounds.right +getPaddingRight();
                yClickPosition= (midY-positionY)+bounds.height()/2;

                if (bounds.contains(xClickPosition, yClickPosition) && onDrawableClickListener != null) {
                    onDrawableClickListener.onClick(DrawablePosition.RIGHT);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return false;
                }
            }

            if (drawableTop != null) {
                bounds = drawableTop.getBounds();

                xClickPosition = (midX - positionX)+bounds.width()/2;
                yClickPosition= positionY - getPaddingTop();

                if (bounds.contains(xClickPosition, yClickPosition) && onDrawableClickListener != null) {
                    onDrawableClickListener.onClick(DrawablePosition.TOP);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return false;
                }
            }

            if(drawableBottom!=null)
            {
                bounds = drawableBottom.getBounds();

                xClickPosition = (midX - positionX)+bounds.width()/2;
                yClickPosition= positionY - h + bounds.height()+ getPaddingBottom();

                if (bounds.contains(xClickPosition, yClickPosition) && onDrawableClickListener != null) {
                    onDrawableClickListener.onClick(DrawablePosition.BOTTOM);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return false;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置点击图标的侦听接口
     */
    public interface OnDrawableClickListener{
        public void onClick(int position);
    }

    /**
     * 图标的位置方向
     */
    public final static class DrawablePosition{
        public final static int LEFT=0;
        public final static int TOP=1;
        public final static int RIGHT=2;
        public final static int BOTTOM=3;
    }
}
