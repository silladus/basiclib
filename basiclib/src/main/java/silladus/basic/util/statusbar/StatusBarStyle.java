package silladus.basic.util.statusbar;

import android.view.Window;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class StatusBarStyle {
    private static volatile StatusBarStyle instance;

    private List<UiOS> uiOSList;

    /**
     * 当前手机的Ui
     */
    private UiOS currentUiOS;

    private StatusBarStyle() {
        this.uiOSList = new ArrayList<>();
        uiOSList.add(new MiUi());
        uiOSList.add(new FlyMe());
        uiOSList.add(new AndroidM());
    }

    public static StatusBarStyle getInstance() {
        if (instance == null) {
            synchronized (StatusBarStyle.class) {
                if (instance == null) {
                    instance = new StatusBarStyle();
                }
            }
        }
        return instance;
    }

    /**
     * 提供增加UiOS实现的入口, 应该在调用{@link StatusBarStyle#setStatusBarLightStyle}之前就调用
     */
    public void addUiOS(@NonNull UiOS uiOS) {
        // 重置当前系统记录（虽然不合常理，避免在setStatusBarLightStyle之后调用添加不生效的情况）
        currentUiOS = null;

        uiOSList.add(uiOS);
    }

    public void setStatusBarLightStyle(Window window, boolean yes) {
        // 已知手机的Ui系统，直接交给他设置
        if (currentUiOS != null) {
            currentUiOS.setStatusBarLightStyle(window, yes);
            return;
        }

        // 逐一遍历设置，直到设置成功或者遍历完为止
        boolean hadSet = false;
        for (UiOS uiOS : uiOSList) {
            hadSet = uiOS.setStatusBarLightStyle(window, yes);
            if (hadSet) {
                currentUiOS = uiOS;
                break;
            }
        }

        // 遍历完了，列表已经不再需要
        uiOSList.clear();

        // 无法设置状态栏模式
        if (!hadSet) {
            currentUiOS = new NotSupportUiOS();
        }
    }

}
