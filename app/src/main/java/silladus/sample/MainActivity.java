package silladus.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import silladus.basic.ActivityInitConfig;
import silladus.basic.IActivity;
import silladus.basic.IProcess;

public class MainActivity extends AppCompatActivity implements IActivity, IProcess {
    @Override
    public void onConfigInit(@NonNull ActivityInitConfig config) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle("toolbar");
        super.onCreate(savedInstanceState);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(new BackgroundFragment(), "BackgroundFragment")
//                .add(android.R.id.content, new MainFragment(), "MainFragment")
//                .commit();
    }

    @Override
    public int getLayoutRes() {
//        return 0;
        return R.layout.activity_main;
    }

    @Override
    public void showLoading() {
        // no impl.
    }

    @Override
    public void hideLoading() {
        // no impl.
    }
}