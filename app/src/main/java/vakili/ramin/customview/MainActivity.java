package vakili.ramin.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import vakili.ramin.customview.views.MyRippleView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyRippleView myRippleView = (MyRippleView) findViewById(R.id.myRippleView);
        myRippleView.setRippleListener(new MyRippleView.RippleListener() {
            @Override
            public void onRippleCompleted() {
                Toast.makeText(MainActivity.this, "Do Something", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
