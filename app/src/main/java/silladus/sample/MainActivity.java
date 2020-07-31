package silladus.sample;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import silladus.basic.ActivityInitConfig;
import silladus.basic.IActivity;
import silladus.sample.page.RecyclerPagerActivity;

/**
 * @author silladus
 * Created on 2018/10/25/0025.
 * GitHub: https://github.com/silladus
 * Description:
 */
public class MainActivity extends AppCompatActivity implements IActivity, View.OnClickListener {
    private CardView mCardView;
    private Button btnPages;
    private Button btnEmptyActivity;

    private Spring spring;

    @Override
    public void onConfigInit(@NonNull ActivityInitConfig config) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setTitle("Main");
        super.onCreate(savedInstanceState);
        mCardView = findViewById(R.id.card_view_container);
        btnPages = findViewById(R.id.btn_pages);
        btnEmptyActivity = findViewById(R.id.btn_emptyActivity);

        SpringSystem springSystem = SpringSystem.create();
        spring = springSystem.createSpring();
        // default is tension:40, friction:7
        spring.setSpringConfig(SpringConfig
                .fromOrigamiTensionAndFriction(40, 2)
        );

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        mCardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // When pressed start solving the spring to 1.
                        spring.setEndValue(1);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.performClick();
                    case MotionEvent.ACTION_CANCEL:
                        // When released start solving the spring to 0.
                        spring.setEndValue(0);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        btnPages.setOnClickListener(this);
        btnEmptyActivity.setOnClickListener(this);
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1 - value * 0.5f;
                mCardView.setScaleX(scale);
                mCardView.setScaleY(scale);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        spring.removeAllListeners();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pages:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityCompat.startActivity(
                            this,
                            new Intent(getBaseContext(), RecyclerPagerActivity.class),
                            ActivityOptions.makeSceneTransitionAnimation(this, mCardView, "anim").toBundle()
                    );
//                    ActivityCompat.startActivity(this,
//                            new Intent(getBaseContext(), RecyclerPagerActivity.class),
////                            ActivityOptions.makeCustomAnimation(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right).toBundle()
//                            ActivityOptions.makeScaleUpAnimation(mCardView,
//                                    0, mCardView.getHeight() / 2, mCardView.getWidth(), mCardView.getHeight()).toBundle()
//                    );
                } else {
                    startActivity(new Intent(getBaseContext(), RecyclerPagerActivity.class));
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
                break;
            case R.id.card_view_container:
                spring.setEndValue(1);
                break;
            case R.id.btn_emptyActivity:
                startActivity(new Intent(getBaseContext(), EmptyActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            default:
                break;
        }
    }
}
