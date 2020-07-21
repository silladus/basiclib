package silladus.basic;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author silladus
 * @date 2017/7/7/0007
 * GitHub: https://github.com/silladus
 * Description:
 */

public class PopupWindowConfig {

    private PopupWindow mPopupWindow;
    private int[] windowPos;

    @NonNull
    public static <T extends PopupWindow> PopupWindowConfig of(@NonNull T popupWindow) {
        if (popupWindow.getContentView() == null) {
            throw new NullPointerException("the " + popupWindow.getClass().getName() + "is not have a content view.");
        }
        return new PopupWindowConfig(popupWindow);
    }

    @NonNull
    public static PopupWindowConfig create(@NonNull View contentView, int width, int height) {
        PopupWindow popupWindow = new PopupWindow(contentView, width, height, true);
        return of(popupWindow);
    }

    @NonNull
    public static PopupWindowConfig create(@NonNull View contentView) {
        return create(
                contentView,
                contentView.getResources().getDisplayMetrics().widthPixels,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    private PopupWindowConfig(PopupWindow mPopupWindow) {
        this.mPopupWindow = mPopupWindow;
    }

    public void showAt(@NonNull View anchorView) {
        if (windowPos == null) {
            Context context = anchorView.getContext();
            Activity activity = getActivityFromContext(context);

            if (activity == null) {
                return;
            }

            // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            // 设置好参数之后再show
            windowPos = calculatePopWindowPos(anchorView, mPopupWindow.getContentView());
            final Window window = activity.getWindow();
            //创建当前界面的一个参数对象
            WindowManager.LayoutParams params = window.getAttributes();
            params.alpha = alpha;
            //把该参数对象设置进当前界面中
            window.setAttributes(params);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams params = window.getAttributes();
                    //设置为不透明，即恢复原来的界面
                    params.alpha = 1.0f;
                    window.setAttributes(params);

                    if (onDismissListener != null) {
                        onDismissListener.onDismiss();
                    }
                }
            });
        }

        // 适应键盘输入
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (mGravity != 0) {
            if (horizontal) {
                mPopupWindow.showAtLocation(anchorView, mGravity, 0, windowPos[1]);
            } else {
                mPopupWindow.showAtLocation(anchorView, mGravity, 0, 0);
            }
        } else {
            int[] pos = {Gravity.CENTER, Gravity.TOP | Gravity.START, Gravity.CENTER_HORIZONTAL, Gravity.BOTTOM | Gravity.START};
            // 可以自己调整偏移
            int offsetY = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    mOffsetY,
                    anchorView.getResources().getDisplayMetrics()
            );
            int y = windowPos[1] + offsetY;
            mPopupWindow.showAtLocation(anchorView, pos[position], windowPos[0], y);
        }
    }

    private static int[] calculatePopWindowPos(@NonNull View anchorView, @NonNull View contentView) {
        int[] windowPos = new int[2];
        int[] anchorLoc = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        int screenHeight = contentView.getResources().getDisplayMetrics().heightPixels;
        int screenWidth = contentView.getResources().getDisplayMetrics().widthPixels;
        // 测量contentView
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        int windowHeight = contentView.getMeasuredHeight();
        int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        boolean isNeedShowUp = screenHeight - anchorLoc[1] - anchorHeight < windowHeight;
        windowPos[0] = screenWidth - windowWidth;
        if (isNeedShowUp) {
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    @Nullable
    private static Activity getActivityFromContext(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }

            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public PopupWindowConfig setOffsetY(int offsetY) {
        this.mOffsetY = offsetY;
        return this;
    }

    public PopupWindowConfig setOnDismissListener(PopupWindow.OnDismissListener listener) {
        this.onDismissListener = listener;
        return this;
    }

    private PopupWindow.OnDismissListener onDismissListener;
    private int mOffsetY = 80;
    private int mGravity;
    private int position = 1;
    private float alpha = 0.6f;

    public PopupWindowConfig setGravityByIndex(int i) {
        position = i;
        return this;
    }

    public PopupWindowConfig setAlpha(float alpha) {
        if (alpha < 0) {
            this.alpha = 0;
        } else {
            this.alpha = Math.min(alpha, 1f);
        }
        return this;
    }

    public PopupWindowConfig setGravity(int gravity) {
        mGravity = gravity;
        return this;
    }

    private boolean horizontal;

    public PopupWindowConfig setGravity(int gravity, boolean horizontal) {
        mGravity = gravity;
        this.horizontal = horizontal;
        return this;
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }
}
