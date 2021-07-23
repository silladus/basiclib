package silladus.basic.util.statusbar;

import android.os.Build;
import android.view.View;
import android.view.Window;

/**
 * 原生通用Android，该功能要Android M及以上
 */
class AndroidM implements UiOS {
    @Override
    public boolean setStatusBarLightStyle(Window window, boolean yes) {
        return setStatusBarLightMode(window, yes);
    }

    /**
     * 设置Android 6.0以上状态栏模式
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    static boolean setStatusBarLightMode(Window window, boolean dark) {
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
}
