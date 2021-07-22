package silladus.basic.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by silladus on 2018/3/17/0017.
 * GitHub: https://github.com/silladus
 * Description:
 */

public final class StatusBarUtil {
    public static final int MIUI = 0xA;
    public static final int FLYME = 0xB;
    public static final int M = 0xC;
    public static final int NO = 0xD;

    private StatusBarUtil() {

    }

    /**
     * 适配状态栏高度
     *
     * @param view 要操作的View
     */
    public static void setViewFitStatusBar(View view) {
        if (view instanceof ViewGroup) {
            view.setPadding(
                    view.getPaddingStart(),
                    view.getPaddingTop() + getStatusBarHeight(view.getContext()),
                    view.getPaddingEnd(),
                    view.getPaddingBottom());
        } else {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            lp.setMargins(
                    lp.leftMargin,
                    lp.topMargin + getStatusBarHeight(view.getContext()),
                    lp.rightMargin,
                    lp.bottomMargin);
        }
    }

    /**
     * 设置图片背景状态栏
     *
     * @return 人为设置的状态栏View
     */
    public static View setStatusBarView(Activity act, Drawable bg) {
        ViewGroup root = act.findViewById(android.R.id.content);
        View view = null;
        if (root.getChildAt(0) instanceof LinearLayout) {
            String tag = "MyStatusBar";
            view = root.findViewWithTag(tag);
            if (view == null) {
                view = new View(act);
                view.setBackground(bg);
                ((ViewGroup) root.getChildAt(0))
                        .addView(view,
                                0,
                                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        getStatusBarHeight(view.getContext())));
                view.setTag(tag);
            }
        }
        return view;
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 系统类型
     * <p>
     * MIUI:  MIUI
     * FLYME: FLYME
     * M:     Android M
     * NO:    Android M lower.
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MIUI, FLYME, M, NO})
    public @interface SystemAPI {
    }

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUI V6、Flyme和6.0以上版本其他Android
     *
     * @return 1:MIUI 2:FLYME 3:Android 6.0
     */
    @SystemAPI
    public static int statusBarLightMode(@NonNull Window window) {
        int result = NO;

        if (MIUISetStatusBarLightMode(window, true)) {
            result = MIUI;
        } else if (FlymeSetStatusBarLightMode(window, true)) {
            result = FLYME;
        } else if (MSetStatusBarLightMode(window, true)) {
            result = M;
        }

        return result;
    }

    /**
     * 状态栏模式，isDark == false设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUI V6、Flyme和6.0以上版本其他Android
     */
    public static void statusBarMode(Window window, @SystemAPI int type, boolean isDark) {
        switch (type) {
            case MIUI:
                MIUISetStatusBarLightMode(window, isDark);
                break;
            case FLYME:
                FlymeSetStatusBarLightMode(window, isDark);
                break;
            case M:
                MSetStatusBarLightMode(window, isDark);
                break;
            case NO:
            default:
                break;
        }
    }

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUI V6、Flyme和6.0以上版本其他Android
     *
     * for example.
     *
     * int ui = 0;
     * // 未知Ui系统的情况下
     * if (ui == 0) {
     *     // 记录返回值
     *     ui = statusBarLightMode(window);
     * } else {
     *     // 已知Ui系统的情况下
     *     statusBarLightMode(window, ui);
     * }
     *
     */
    public static void statusBarLightMode(Window window, @SystemAPI int type) {
        statusBarMode(window, type, true);
    }

    /**
     * 状态栏暗色模式，清除MIUI、Flyme或6.0以上版本状态栏黑色文字、图标
     */
    public static void statusBarDarkMode(Window window, @SystemAPI int type) {
        statusBarMode(window, type, false);
    }

    /**
     * 设置Android 6.0以上状态栏模式
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean MSetStatusBarLightMode(Window window, boolean dark) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        if (dark) {
            window.getDecorView().setSystemUiVisibility(/*View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | */View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
        return true;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    @SuppressWarnings("JavaReflectionMemberAccess")
    private static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;

        try {
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 需要MIUI V6以上
     *
     * @param dark 是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;

        try {
            Class<?> clazz = window.getClass();
            @SuppressLint("PrivateApi") Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {
                //状态栏透明且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
            result = true;

            //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
            MSetStatusBarLightMode(window, dark);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
