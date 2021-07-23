package silladus.basic.systembar;

import androidx.annotation.ColorInt;

/**
 * Created by silladus on 2018/5/31/0031.
 * GitHub: https://github.com/silladus
 * Description:
 */
public interface IStatusBar {

    boolean isClipToPadding();

    @ColorInt
    int statusBarColor();

    boolean lightStatusBar();
}
