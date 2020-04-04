package silladus.basic.util;

import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by silladus on 2018/3/10/0010.
 * GitHub: https://github.com/silladus
 * Description:
 */

public final class UToast {

    private static Toast mToast;
    private static int offsetY;

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

//        // 对Toast而言，只在需要提示的时候才显示出来，不需要的时候甚至都不会生成
//        // 所以希望他是阅后即焚的，隐藏后希望可以销毁被内存回收，并不需要静态引用保存
//        Toast.makeText(AppContext.get(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Make a standard toast that just contains a text view.
     *
     * @param text     The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link android.widget.Toast#LENGTH_SHORT} or
     *                 {@link android.widget.Toast#LENGTH_LONG}
     */
    public static void show(CharSequence text, int duration) {
        show(text, duration, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
    }

    public static void showAtCenter(CharSequence text) {
        show(text, Toast.LENGTH_SHORT, Gravity.CENTER);
    }

    public static void showAtCenter(CharSequence text, int duration) {
        show(text, duration, Gravity.CENTER);
    }

    private static void show(CharSequence text, int duration, int gravity) {
        if (mToast == null) {
            mToast = Toast.makeText(AppContext.get(), text, duration);
            offsetY = DrawableUtil.dp2px(AppContext.get(), 64);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.setGravity(gravity, mToast.getXOffset(), gravity == Gravity.CENTER ? 0 : offsetY);
        mToast.show();

    }

}
