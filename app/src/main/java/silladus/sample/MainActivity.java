package silladus.sample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import silladus.basic.ActivityInitConfig;
import silladus.sample.R;

import silladus.basic.IActivity;

public class MainActivity extends AppCompatActivity implements IActivity {
    @Override
    public void onConfigInit(@NonNull ActivityInitConfig config) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void showLoadingDialog() {
        // no impl.
    }

    @Override
    public void hideLoadingDialog() {
        // no impl.
    }
}