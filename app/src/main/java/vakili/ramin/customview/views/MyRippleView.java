package vakili.ramin.customview.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

/**
 * Created by RaminV on 6/27/2019.
 */
public class MyRippleView extends LinearLayout {

    private static final long MAX_RIPPLE_DURATION = 2000;
    private int rippleRadius = 0;
    private int rippleDuration = 400;
    private int xStart;
    private int yStart;
    private Paint ripplePaint;
    private Paint highlightPaint;
    private boolean highlight = false;
    private RectF highlightRect;
    private ValueAnimator animator;
    private int rippleColor = Color.GRAY;
    private int highlightColor = Color.argb(20, 50, 50, 50);
    private float cornerRadius = 20;
    private int lastRippleRadius;

    public MyRippleView(Context context) {
        super(context);
        init();
    }

    public MyRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyRippleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        ripplePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ripplePaint.setColor(rippleColor);
        highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        highlightPaint.setColor(highlightColor);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(xStart, yStart, rippleRadius, ripplePaint);
        if (highlight){
            canvas.drawRoundRect(highlightRect, cornerRadius, cornerRadius, highlightPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("TAG2", "action: " + event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("TAG1", "ACTION_DOWN");
                startRippleEffect(event, 0);
                highlight = true;
                animator.setDuration(MAX_RIPPLE_DURATION);
                break;

            case MotionEvent.ACTION_UP:
                Log.i("TAG1", "ACTION_UP");
                highlight = false;
                animator.cancel();
                startRippleEffect(event, lastRippleRadius);
                break;
        }
        return true;
    }

    private void startRippleEffect(MotionEvent event, int startRadius) {
        highlightRect = new RectF(0, 0 , getWidth(), getHeight());
        xStart = (int) event.getX();
        yStart = (int) event.getY();
        animator = ValueAnimator.ofInt(startRadius, Math.max(getWidth() , getHeight()));
        animator.setDuration(rippleDuration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(listener);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                lastRippleRadius = rippleRadius;
                rippleRadius = 0;
                invalidate();
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

    private ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            rippleRadius = (int) valueAnimator.getAnimatedValue();
            invalidate();
        }
    };

}
