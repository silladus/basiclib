package silladus.sample;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import silladus.basic.IActivity;

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
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                if (activity instanceof IActivity) {
                    initToolbar((AppCompatActivity) activity);
                }
                //LinearLayout
                //-----ActionBar
                //-----FrameLayout(android.R.id.content)

                if (activity instanceof FragmentActivity) {
                    ((FragmentActivity) activity)
                            .getSupportFragmentManager()
                            .registerFragmentLifecycleCallbacks(
                                    new BasicFragmentLifecycleCallbacks() {
                                        @Override
                                        public void onFragmentViewCreated(@NonNull FragmentManager fm,
                                                                          @NonNull Fragment f,
                                                                          @NonNull View v,
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
            public int statusBarColor() {
                return Color.parseColor("#ff303030");
            }

            @Override
            public boolean lightStatusBar() {
                return true;
            }

            @Override
            public int getNavigationBarColor() {
                return Color.WHITE;
            }

            @Override
            public boolean lightNavigationBar() {
                return true;
            }
        });
        //registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksImp());
    }

    private void initToolbar(@NonNull AppCompatActivity activity) {
        // Set up the toolbar.
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        // Set the Toolbar as an ActionBar.
        activity.setSupportActionBar(toolbar);
        ActionBar ab = activity.getSupportActionBar();
        if (ab != null) {
            // Title layout in the Toolbar's center.
            TextView tv = new AppCompatTextView(activity);
            tv.setText(activity.getTitle());
            tv.setSingleLine();
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextSize(20);
            tv.setTextColor(Color.WHITE);
            toolbar.addView(tv, new Toolbar.LayoutParams(Gravity.CENTER));
            // Hide the default title.
            ab.setDisplayShowTitleEnabled(false);
            ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

}
