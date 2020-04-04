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

/**
 * Created by silladus on 2018/5/31/0031.
 * GitHub: https://github.com/silladus
 * Description:
 */
public class ActivityInitConfig {
    private boolean isClipToPadding;

    public ActivityInitConfig(Activity activity, IStatusBar iStatusBar) {
        this(activity, iStatusBar, false);
    }

    public ActivityInitConfig(Activity activity, IStatusBar iStatusBar, boolean isGray) {
        if (!(activity instanceof IActivity)) {
            setGrayView(isGray, activity);
            return;
        }

        // AppCompatActivity call the supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        // else call requestWindowFeature(Window.FEATURE_NO_TITLE) if extend Activity.
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        int statusBarColor;
        if (activity instanceof IStatusBar) {
            isClipToPadding = ((IStatusBar) activity).isClipToPadding();
            statusBarColor = ((IStatusBar) activity).statusBarColor();
        } else if (activity instanceof IStatusBarColor) {
            isClipToPadding = iStatusBar.isClipToPadding();
            statusBarColor = ((IStatusBarColor) activity).statusBarColor();
        } else {
            isClipToPadding = iStatusBar.isClipToPadding();
            statusBarColor = iStatusBar.statusBarColor();
        }

        if (isGray) {
            statusBarColor = 0xFFA9A9A9;
        }
        statusBar(statusBarColor, activity);

        ViewGroup view = activity.findViewById(android.R.id.content);
        if (isClipToPadding) {
            view.setClipToPadding(true);
            view.setFitsSystemWindows(true);
        }

        setGrayView(isGray, activity);

        // inflate root layout
        int layoutRes = ((IActivity) activity).getLayoutRes();
        activity.setContentView(layoutRes);

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

    /**
     * 全屏
     */
    public static void requestFullScreen(Activity activity) {
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        Window window = activity.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
