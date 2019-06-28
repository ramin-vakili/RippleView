package vakili.ramin.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import vakili.ramin.customview.views.RippleView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RippleView rippleView = (RippleView) findViewById(R.id.myRippleView);
        rippleView.setRippleListener(new RippleView.RippleListener() {
            @Override
            public void onRippleCompleted() {
                Toast.makeText(MainActivity.this, "Do Something", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
