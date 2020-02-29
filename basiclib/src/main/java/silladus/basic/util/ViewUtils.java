package silladus.basic.util;

import android.view.View;

public class ViewUtils {

    private ViewUtils() {
    }

    public static boolean setViewVisible(View view, boolean isVisible) {
        if (view != null) {
            if (isVisible) {
                if (view.getVisibility() != View.VISIBLE) {
                    view.setVisibility(View.VISIBLE);
                }
            } else {
                if (view.getVisibility() != View.GONE) {
                    view.setVisibility(View.GONE);
                }
            }
        }
        return isVisible;
    }

    public static void visibleToggle(View view) {
        if (view == null) {
            return;
        }

        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static boolean isViewVisible(View view) {
        return view != null && view.getVisibility() == View.VISIBLE;
    }

    /**
     * Set view's visibility with checked.
     *
     * @param view       opt view
     * @param visibility {@link View#VISIBLE}, {@link View#INVISIBLE}, {@link View#GONE}
     */
    public static void setViewVisibility(View view, int visibility) {
        if (view == null || view.getVisibility() == visibility) {
            return;
        }
        view.setVisibility(visibility);
    }


}
