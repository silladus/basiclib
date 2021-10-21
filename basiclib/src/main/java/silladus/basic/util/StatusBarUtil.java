package silladus.basic.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by silladus on 2018/3/17/0017.
 * GitHub: https://github.com/silladus
 * Description:
 */
public final class StatusBarUtil {

    private StatusBarUtil() {

    }

    /**
     * 适配状态栏高度
     *
     * @param view 要操作的View
     */
    public static void setViewFitStatusBar(View view) {
        if (view instanceof ViewGroup) {
            view.setPadding(
                    view.getPaddingStart(),
                    view.getPaddingTop() + getStatusBarHeight(view.getContext()),
                    view.getPaddingEnd(),
                    view.getPaddingBottom());
        } else {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            lp.setMargins(
                    lp.leftMargin,
                    lp.topMargin + getStatusBarHeight(view.getContext()),
                    lp.rightMargin,
                    lp.bottomMargin);
        }
    }

    /**
     * 设置图片背景状态栏
     *
     * @return 人为设置的状态栏View
     */
    public static View setStatusBarView(Activity act, Drawable bg) {
        ViewGroup root = act.findViewById(android.R.id.content);
        View view = null;
        if (root.getChildAt(0) instanceof LinearLayout) {
            String tag = "MyStatusBar";
            view = root.findViewWithTag(tag);
            if (view == null) {
                view = new View(act);
                view.setBackground(bg);
                ((ViewGroup) root.getChildAt(0))
                        .addView(view,
                                0,
                                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        getStatusBarHeight(view.getContext())));
                view.setTag(tag);
            }
        }
        return view;
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
