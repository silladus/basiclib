package silladus.basic;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * @author silladus
 * @date 2017/7/7/0007
 * GitHub: https://github.com/silladus
 * Description:
 */

public class PopupWindowDialog {

    public PopupWindowDialog(View contentView) {
        this.mContentView = contentView;
    }

    private int[] calculatePopWindowPos(View anchorView, View contentView) {
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
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    private View mContentView;
    private PopupWindow mPopupWindow;
    private int[] windowPos;

    public void show(View anchorView) {
        if (mPopupWindow == null) {
            final Activity activity = (Activity) mContentView.getContext();
            mPopupWindow = new PopupWindow(mContentView, (int) (anchorView.getResources().getDisplayMetrics().widthPixels / mWindowWidth), ViewGroup.LayoutParams.WRAP_CONTENT, true);
            // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            // 设置好参数之后再show
            windowPos = calculatePopWindowPos(anchorView, mContentView);
            //创建当前界面的一个参数对象
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.alpha = alpha;
            //把该参数对象设置进当前界面中
            activity.getWindow().setAttributes(params);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams params = activity.getWindow().getAttributes();
                    //设置为不透明，即恢复原来的界面
                    params.alpha = 1.0f;
                    activity.getWindow().setAttributes(params);

                    if (listener != null) {
                        listener.onDismiss();
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
            int yOffset = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    mYOffset,
                    anchorView.getResources().getDisplayMetrics()
            );
            int y = windowPos[1] + yOffset;
            mPopupWindow.showAtLocation(anchorView, pos[position], windowPos[0], y);
        }
    }

    public PopupWindowDialog setYOffset(int yOffset) {
        this.mYOffset = yOffset;
        return this;
    }

    public PopupWindowDialog setOnDismissListener(PopupWindow.OnDismissListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 屏幕宽度与window宽度的比数
     */
    public PopupWindowDialog setWindowWidth(int mWindowWidth) {
        this.mWindowWidth = mWindowWidth;
        return this;
    }

    private PopupWindow.OnDismissListener listener;
    private float mWindowWidth = 1f;
    private int mYOffset = 80;
    private int mGravity;
    private int position = 1;
    private float alpha = 0.6f;

    public PopupWindowDialog setGravityByIndex(int i) {
        position = i;
        return this;
    }

    public PopupWindowDialog setAlpha(float alpha) {
        if (alpha < 0) {
            this.alpha = 0;
        } else if (alpha > 1f) {
            this.alpha = 1f;
        } else {
            this.alpha = alpha;
        }
        return this;
    }

    public PopupWindowDialog setGravity(int gravity) {
        mGravity = gravity;
        return this;
    }

    private boolean horizontal;

    public PopupWindowDialog setGravity(int gravity, boolean horizontal) {
        mGravity = gravity;
        this.horizontal = horizontal;
        return this;
    }

    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }
}
