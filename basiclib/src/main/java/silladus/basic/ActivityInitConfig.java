package silladus.basic;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import silladus.basic.systembar.*;

/**
 * Created by silladus on 2018/5/31/0031.
 * GitHub: https://github.com/silladus
 * Description:
 *
 * <pre><code>
 *
 * app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
 *     @Override
 *     public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
 *         // Common Activity Config.
 *         new ActivityInitConfig(activity, iStatusBar);
 *
 *         ...
 *     }
 *
 *     IStatusBar iStatusBar = new IStatusBar() {
 *         ...
 *     }
 *
 *     ...
 * })
 * </code></pre>
 */
public class ActivityInitConfig {
    private DefaultSystemBar defaultSystemBar;
    private static List<PropertyClone> propertyCloneList;

    public ActivityInitConfig(Activity activity, IStatusBar iStatusBar) {
        this(activity, iStatusBar, false);
    }

    public ActivityInitConfig(Activity activity, IStatusBar iStatusBar, boolean isGray) {
        initConfig(activity, iStatusBar, isGray);
    }

    protected void initConfig(Activity activity, IStatusBar iStatusBar, boolean isGray) {
        if (!(activity instanceof IActivity)) {
            setGrayView(isGray, activity);
            return;
        }

        // AppCompatActivity call the supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        // else call requestWindowFeature(Window.FEATURE_NO_TITLE) if extend Activity.
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (propertyCloneList == null) {
            // 责任链模式设置参数
            propertyCloneList = new ArrayList<>();
            propertyCloneList.add(new SystemBarCloneByNavigationBarEnable());
            propertyCloneList.add(new SystemBarCloneByStatusBarEnable());
        }

        DefaultSystemBar defaultSystemBar = new DefaultSystemBar(propertyCloneList);
        // 优先取activity中的配置
        boolean hadClone = defaultSystemBar.cloneBy(activity);
        // activity中没有配置则取通用配置
        if (!hadClone) {
            defaultSystemBar.cloneBy(iStatusBar);
        }

        if (isGray) {
            defaultSystemBar.setStatusBarColor(0xFFA9A9A9);
        }

        this.defaultSystemBar = defaultSystemBar;

        systemBar(defaultSystemBar, activity);

        ViewGroup view = activity.findViewById(android.R.id.content);
        if (defaultSystemBar.isClipToPadding()) {
            view.setClipToPadding(true);
            view.setFitsSystemWindows(true);
        }

        setGrayView(isGray, activity);

        // inflate root layout
        int layoutRes = ((IActivity) activity).getLayoutRes();
        if (layoutRes != 0) {
            activity.setContentView(layoutRes);
        }

        ((IActivity) activity).onConfigInit(this);

    }

    private void setGrayView(boolean isGray, Activity activity) {
        if (isGray) {
//            ViewGroup view = activity.findViewById(android.R.id.content);
//            //针对设置了windowBackground的情况
//            Drawable backgroundDrawable;
//            TypedValue a = new TypedValue();
//            activity.getTheme().resolveAttribute(android.R.attr.windowBackground, a, true);
//            if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT) {
//                // windowBackground is a color
//                int color = a.data;
//                backgroundDrawable = new ColorDrawable(color);
//            } else {
//                // windowBackground is not a color, probably a drawable
//                backgroundDrawable = activity.getResources().getDrawable(a.resourceId);
//            }
//
//            ViewGroup.LayoutParams lp = view.getLayoutParams();
//            ViewGroup rootView = (ViewGroup) view.getParent();
//            rootView.removeView(view);
//
//            GrayFrameLayout layout = new GrayFrameLayout(activity);
//            layout.setBackground(backgroundDrawable);
//            layout.addView(view);
//
//            rootView.addView(layout, lp);

            Paint paint = new Paint();
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            activity.getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);

        }
    }

    /**
     * Android 沉浸式状态栏
     */
    public final void statusBar(@ColorInt int color, Activity activity) {
        defaultSystemBar.setStatusBarColor(color);
        systemBar(defaultSystemBar, activity);
    }

    public final void systemBar(ISystemBar systemBar, Activity activity) {
        Window window = activity.getWindow();
        int statusBarColor = systemBar.statusBarColor();
        boolean isClipToPadding = systemBar.isClipToPadding();

        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            int decorViewFlag = decorView.getSystemUiVisibility();
            // 5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            // 透明状态栏
            if (!isClipToPadding) {
                statusBarColor = Color.TRANSPARENT;
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(statusBarColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (systemBar.lightStatusBar()) {
                    decorViewFlag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                if (systemBar.lightNavigationBar()) {
                    decorViewFlag |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                }
                decorView.setSystemUiVisibility(decorViewFlag);
                window.setNavigationBarColor(systemBar.getNavigationBarColor());
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            if (isClipToPadding) {
                // Android 4.4沉浸式
                SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                // enable status bar tint
                tintManager.setStatusBarTintEnabled(true);
                // enable navigation bar tint
                tintManager.setNavigationBarTintEnabled(true);
                // 给状态栏设置颜色
                tintManager.setStatusBarTintColor(statusBarColor);
                tintManager.setNavigationBarTintColor(systemBar.getNavigationBarColor());
            }
        }
    }

    /**
     * 全屏
     */
    public static void requestFullScreen(Activity activity) {
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        Window window = activity.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 隐藏导航栏
        View decorView = window.getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setOnSystemUiVisibilityChangeListener(i -> {
            if ((i & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                decorView.setSystemUiVisibility(uiOptions);
            }
        });
    }
}
