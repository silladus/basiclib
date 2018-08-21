package silladus.basic;

import android.app.Activity;
import android.support.annotation.ColorInt;

/**
 * Created by silladus on 2018/5/31/0031.
 * GitHub: https://github.com/silladus
 * Description:
 */
public interface IStatusBar2 {

    boolean isClipToPadding();

    @ColorInt
    int statusBarColor(Activity activity);

}
