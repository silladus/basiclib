package silladus.basic.util.statusbar;

import android.view.Window;

/**
 * 不支持的系统，可能为Android M以下
 */
class NotSupportUiOS implements UiOS {
    @Override
    public boolean setStatusBarLightStyle(Window window, boolean yes) {
        return false;
    }
}
