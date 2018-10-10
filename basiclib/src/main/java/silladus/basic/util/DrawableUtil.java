package silladus.basic.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by silladus on 2017/8/17/0017.
 * GitHub: https://github.com/silladus
 * Description:
 */

public final class DrawableUtil {
    private DrawableUtil() {

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dpValue * scale);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static final int DRAWABLE_LEFT = 0,
            DRAWABLE_TOP = 1,
            DRAWABLE_RIGHT = 2,
            DRAWABLE_BOTTOM = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DRAWABLE_LEFT, DRAWABLE_TOP, DRAWABLE_RIGHT, DRAWABLE_BOTTOM})
    private @interface DrawableType {
    }

    public static void addDrawable(TextView tv, int resId, @DrawableType int position, int w) {
        addDrawable(tv, resId, position, w, w);
    }

    public static void addDrawable(TextView tv, int resId, int position) {
        addDrawable(tv, resId, position, 0, 0);
    }

    public static void addDrawable(TextView tv, int resId, @DrawableType int position, int w, int h) {
        Context context = tv.getContext();
        Drawable d = null;
        if (resId != 0) {
            d = context.getResources().getDrawable(resId);
            if (w > 0 && h > 0) {
                d.setBounds(0, 0, dp2px(context, w), dp2px(context, h));
            } else {
                d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
            }
        }
        Drawable[] drawables = new Drawable[4];
        for (int i = 0; i < drawables.length; i++) {
            if (position == i) {
                drawables[position] = d;
            }
        }
        // start / top / end / bottom
        tv.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    public static void addDrawable(TextView tv, Drawable[] drawArr, int w) {
        addDrawable(tv, drawArr, w, w);
    }

    public static void addDrawable(TextView tv, Drawable[] drawArr, int w, int h) {
        Context context = tv.getContext();
        Drawable[] drawables = new Drawable[4];
        for (int i = 0; i < drawables.length; i++) {
            Drawable d = drawArr[i];
            if (d != null) {
                if (w > 0 && h > 0) {
                    d.setBounds(0, 0, dp2px(context, w), dp2px(context, h));
                } else {
                    d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
                }
            }
            drawables[i] = d;
        }
        // start / top / end / bottom
        tv.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    public static void addDrawable(TextView tv, Drawable[] drawArr, int... bounds) {
        Context context = tv.getContext();
        Drawable[] drawables = new Drawable[4];
        for (int i = 0; i < drawables.length; i++) {
            Drawable d = drawArr[i];
            //int b = bounds[i];
            if (d != null) {
                if (i == 3) {
                    d.setBounds(0, 0, bounds[3] == 0 ? d.getMinimumWidth() : bounds[3], d.getMinimumHeight());
                } else {
                    d.setBounds(0, 0, dp2px(context, bounds[0]), dp2px(context, bounds[1]));
                }
            }
            drawables[i] = d;
        }
        // start / top / end / bottom
        tv.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }


    public static void addDrawable(TextView tv, Drawable[] drawables,
                                   int leftW, int leftH,
                                   int topW, int topH,
                                   int rightW, int rightH,
                                   int bottomW, int bottomH) {

        int[][] bounds = new int[][]{
                {leftW, leftH},
                {topW, topH},
                {rightW, rightH},
                {bottomW, bottomH}
        };

        for (int i = 0; i < drawables.length; i++) {
            Drawable d = drawables[i];
            if (d == null) {
                continue;
            }

            int w = bounds[i][0] > 0 ? dp2px(tv.getContext(), bounds[i][0]) : d.getMinimumWidth();
            int h = bounds[i][1] > 0 ? dp2px(tv.getContext(), bounds[i][1]) : d.getMinimumHeight();
            d.setBounds(0, 0, w, h);
        }
        // start / top / end / bottom
        tv.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    /**
     * 生成xml中的selector-drawable
     *
     * @param checked state_checked=true 的drawable
     * @param normal  state_checked=false 的drawable
     * @return selector drawable
     */
    public static Drawable makeCheckDrawableSelector(Drawable checked, Drawable normal) {
        // 生成xml中的selector-drawable
        StateListDrawable selectorDrawable = new StateListDrawable();
        // 需要注意的是，这里有一个负号，
        // 即在-android.R.attr.state_focused 里的负号代表的值为false，反之为true
        selectorDrawable.addState(
                new int[]{/*-android.R.attr.state_focused,*/ android.R.attr.state_checked},
                checked
        );
        selectorDrawable.addState(
                new int[]{/*-android.R.attr.state_focused,*/ -android.R.attr.state_checked},
                normal
        );
        return selectorDrawable;
    }

    /**
     * 生成xml中的selector-color
     *
     * @param checkColor  state_checked=true状态的color
     * @param normalColor 默认状态的color
     * @return selector-color
     */
    public static ColorStateList makeCheckColorSelector(@ColorInt int checkColor,
                                                        @ColorInt int normalColor) {
        // 生成xml中的selector-color
        return new ColorStateList(
                new int[][]{{android.R.attr.state_checked}, {}},
                new int[]{checkColor, normalColor});
    }

    /**
     * 生成xml中的shape
     *
     * @param color        solid
     * @param cornerRadius corners android:radius
     * @return shape
     */
    public static GradientDrawable makeRectangleShape(@ColorInt int color, float cornerRadius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(cornerRadius);
        shape.setColor(color);
        return shape;
    }

    public static int[] getIntArray(Context context, int arrayId) {
        TypedArray ar = context.getResources().obtainTypedArray(arrayId);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++) {
            resIds[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        return resIds;
    }

}
