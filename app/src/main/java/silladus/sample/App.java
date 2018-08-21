package silladus.sample;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import silladus.sample.R;
import silladus.basic.BasicActivityLifecycleCallbacksImp;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Activity的公共配置
        initActivity();

    }

    private void initActivity() {
        registerActivityLifecycleCallbacks(new BasicActivityLifecycleCallbacksImp() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
            }

            @Override
            public int statusBarColor(Activity activity) {
                return getResources().getColor(R.color.main);
            }

        });
    }

}
