package silladus.basic;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by silladus on 2018/5/31/0031.
 * GitHub: https://github.com/silladus
 * Description:
 */
public class ActivityInitConfig {
    private boolean isClipToPadding;

    public ActivityInitConfig(Activity activity, IStatusBar2 iStatusBar) {
        // AppCompatActivity call the supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        // else call requestWindowFeature(Window.FEATURE_NO_TITLE) if extend Activity.
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (activity instanceof IActivity) {
            // inflate root layout
            final ViewGroup rootLayout = (ViewGroup) View.inflate(
                    activity, ((IActivity) activity).getLayoutRes(), null
            );

            int statusBarColor;
            if (activity instanceof IStatusBar) {
                isClipToPadding = ((IStatusBar) activity).isClipToPadding();
                statusBarColor = ((IStatusBar) activity).statusBarColor();
            } else if (activity instanceof IStatusBarColor) {
                isClipToPadding = iStatusBar.isClipToPadding();
                statusBarColor = ((IStatusBarColor) activity).statusBarColor();
            } else {
                isClipToPadding = iStatusBar.isClipToPadding();
                statusBarColor = iStatusBar.statusBarColor(activity);
            }
            if (isClipToPadding) {
                rootLayout.setClipToPadding(true);
                rootLayout.setFitsSystemWindows(true);
            }

            statusBar(statusBarColor, activity);
            activity.setContentView(rootLayout);

            ((IActivity) activity).onConfigInit(this);

        }
    }

    /**
     * Android 沉浸式状态栏
     *
     * @param colorInt color value
     */
    public final void statusBar(@ColorInt int colorInt, Activity activity) {
        Window window = activity.getWindow();
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明状态栏
            if (!isClipToPadding) {
                colorInt = Color.TRANSPARENT;
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorInt);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (isClipToPadding) {
                // Android 4.4沉浸式
                SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                // enable status bar tint
                tintManager.setStatusBarTintEnabled(true);
                // enable navigation bar tint
                tintManager.setNavigationBarTintEnabled(true);
                // 给状态栏设置颜色
                tintManager.setStatusBarTintColor(colorInt);
            }
        }
    }
}
