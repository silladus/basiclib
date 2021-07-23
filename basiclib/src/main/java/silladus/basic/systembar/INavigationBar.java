package silladus.basic.systembar;

import androidx.annotation.ColorInt;

public interface INavigationBar {

    @ColorInt
    int getNavigationBarColor();

    boolean lightNavigationBar();
}
