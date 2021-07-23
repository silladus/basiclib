package silladus.basic.util.statusbar;

import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * 魅族FlyMe Ui
 */
class FlyMe implements UiOS {
    @Override
    public boolean setStatusBarLightStyle(Window window, boolean yes) {
        return setStatusBarLightMode(window, yes);
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
    private static boolean setStatusBarLightMode(Window window, boolean dark) {
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
}
