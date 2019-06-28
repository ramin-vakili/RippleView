package vakili.ramin.customview.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import vakili.ramin.customview.R;

/**
 * Created by RaminV on 6/27/2019.
 */
public class MyRippleView extends LinearLayout {

    private int maxRippleDuration;
    private int rippleRadius = 0;
    private int rippleDuration;
    private int xStart;
    private int yStart;
    private Paint ripplePaint;
    private Paint highlightPaint;
    private Path path;
    private boolean highlight = false;
    private RectF rect;
    private ValueAnimator animator;
    private int rippleColor;
    private int highlightColor;
    private float cornerRadius;
    private RippleListener rippleListener;

    public MyRippleView(Context context) {
        super(context);
    }

    public MyRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyRippleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyRippleView, 0, 0);
        try {
            setRippleColor(typedArray.getColor(R.styleable.MyRippleView_rippleColor, Color.GRAY));
            setHighlightColor(typedArray.getColor(R.styleable.MyRippleView_highlightColor, Color.argb(20, 50, 50, 50)));
            setCornerRadius(typedArray.getInteger(R.styleable.MyRippleView_cornerRadius, 0));
            setRippleDuration(typedArray.getInteger(R.styleable.MyRippleView_rippleDuration, 300));
            setMaxRippleDuration(typedArray.getInteger(R.styleable.MyRippleView_maxRippleDuration, 3000));
        } finally {
            typedArray.recycle();
        }

        ripplePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ripplePaint.setColor(rippleColor);
        path = new Path();
        rect = new RectF();
        highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        highlightPaint.setColor(highlightColor);
        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // compute the path
        path.reset();
        rect.set(0, 0, w, h);
        path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW);
        path.close();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.clipPath(path);
        canvas.drawCircle(xStart, yStart, rippleRadius, ripplePaint);
        if (highlight){
            canvas.drawRect(0, 0, getWidth(), getHeight(), highlightPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startSlowRippleEffect(event, 0);
                break;

            case MotionEvent.ACTION_UP:
                animator.cancel();
                startFastRippleEffect(event, rippleRadius);
                break;
        }

        return true;
    }

    private void startFastRippleEffect(MotionEvent event, int startRadius) {
        xStart = (int) event.getX();
        yStart = (int) event.getY();
        animator = ValueAnimator.ofInt(startRadius, Math.max(getWidth() , getHeight()));
        animator.setDuration(rippleDuration);
        animator.addUpdateListener(listener);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rippleRadius = 0;
                highlight = false;
                postInvalidate();
                if (rippleListener != null){
                    rippleListener.onRippleCompleted();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private void startSlowRippleEffect(MotionEvent event, int startRadius) {
        xStart = (int) event.getX();
        yStart = (int) event.getY();
        animator = ValueAnimator.ofInt(startRadius, Math.max(getWidth() , getHeight()));
        highlight = true;
        animator.setDuration(maxRippleDuration);
        animator.addUpdateListener(listener);
        animator.start();
    }

    private ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            rippleRadius = (int) valueAnimator.getAnimatedValue();
            postInvalidate();
        }
    };

    public int getMaxRippleDuration() {
        return maxRippleDuration;
    }

    public void setMaxRippleDuration(int maxRippleDuration) {
        this.maxRippleDuration = maxRippleDuration;
        postInvalidate();
    }

    public int getRippleRadius() {
        return rippleRadius;
    }

    public void setRippleRadius(int rippleRadius) {
        this.rippleRadius = rippleRadius;
        postInvalidate();
    }

    public int getRippleDuration() {
        return rippleDuration;
    }

    public void setRippleDuration(int rippleDuration) {
        this.rippleDuration = rippleDuration;
        postInvalidate();
    }

    public int getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(int highlightColor) {
        this.highlightColor = highlightColor;
        postInvalidate();
    }

    public int getRippleColor() {
        return rippleColor;
    }

    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
        postInvalidate();
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        postInvalidate();
    }

    public void setRippleListener(RippleListener rippleListener) {
        this.rippleListener = rippleListener;
    }

    public interface RippleListener{
        void onRippleCompleted();
    }
}
