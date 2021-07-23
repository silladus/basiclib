package silladus.basic.util.statusbar;

import android.annotation.SuppressLint;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 小米MiUi
 */
class MiUi implements UiOS {
    @Override
    public boolean setStatusBarLightStyle(Window window, boolean yes) {
        return setStatusBarLightMode(window, yes);
    }

    /**
     * 需要MIUI V6以上
     *
     * @param dark 是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setStatusBarLightMode(Window window, boolean dark) {
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
            AndroidM.setStatusBarLightMode(window, dark);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
