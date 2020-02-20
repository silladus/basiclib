package silladus.sample;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import silladus.basic.ActivityInitConfig;
import silladus.basic.IActivity;

/**
 * @author silladus
 */
public class EmptyActivity extends AppCompatActivity implements IActivity {
    @Override
    public void onConfigInit(@NonNull ActivityInitConfig config) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(new BackgroundFragment(), "BackgroundFragment")
//                .add(android.R.id.content, new MainFragment(), "MainFragment")
//                .commit();


    }

    @Override
    public int getLayoutRes() {
        return 0;
//        return R.layout.activity_main;
    }

}