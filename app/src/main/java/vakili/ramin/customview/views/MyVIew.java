package vakili.ramin.customview.views;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by RaminV on 6/27/2019.
 */
public class MyVIew extends LinearLayout {

    private int rippleRadius = 0;
    private Paint paint;

    public MyVIew(Context context) {
        super(context);
        init();
    }

    public MyVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyVIew(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        setWillNotDraw(false);
        startRippleEffect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int xMiddle = getWidth() / 2;
        int yMiddle = getHeight() / 2;
        canvas.drawCircle(xMiddle, yMiddle, rippleRadius, paint);
        Log.i("TAG", "invalidate: " + rippleRadius);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startRippleEffect();
                break;
        }
        return true;
    }

    private void startRippleEffect() {
        Log.i("TAG", "getWidth: " + getWidth() + " " + "getHeight: " + getHeight() );
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rippleRadius = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }
}
