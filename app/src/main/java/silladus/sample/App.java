package silladus.sample;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import silladus.basic.ActivityLifecycleCallbacksImp;
import silladus.basic.BasicFragmentLifecycleCallbacks;
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
                //LinearLayout
                //-----ActionBar
                //-----FrameLayout(android.R.id.content)

                if (activity instanceof FragmentActivity) {
                    ((FragmentActivity) activity)
                            .getSupportFragmentManager()
                            .registerFragmentLifecycleCallbacks(
                                    new BasicFragmentLifecycleCallbacks(){
                                        @Override
                                        public void onFragmentViewCreated(FragmentManager fm,
                                                                          Fragment f,
                                                                          View v,
                                                                          Bundle savedInstanceState) {
                                            super.onFragmentViewCreated(fm, f, v, savedInstanceState);
                                            if (v instanceof ViewGroup) {
                                                addStatusBarView((ViewGroup) v);
                                            }
                                        }
                                    },
                                    true
                            );
                }
                //ViewGroup viewGroup = activity.findViewById(android.R.id.content);
                //addStatusBarView(viewGroup);
            }

            private void addStatusBarView(ViewGroup viewGroup) {
                View statusBarView = new View(viewGroup.getContext());
                statusBarView.setBackgroundColor(Color.BLUE);
                viewGroup.addView(statusBarView, 0,
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                104));
            }

            @Override
            public int statusBarColor(Activity activity) {
                return getResources().getColor(R.color.main);
            }

        });
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksImp());
    }

}
