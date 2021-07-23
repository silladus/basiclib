package silladus.basic.systembar;

import android.graphics.Color;

import androidx.annotation.NonNull;

import java.util.List;

public class DefaultSystemBar implements ISystemBar {
    private List<PropertyClone> mPropertyCloneList;
    private int index;

    private boolean mClipToPadding;
    private int mStatusBarColor;
    private boolean mLightStatusBar;

    private int mNavigationBarColor;
    private boolean mLightNavigationBar;

    /**
     * 已经设置过参数
     */
    private boolean finishCloneProperty;

    public DefaultSystemBar(@NonNull List<PropertyClone> propertyCloneList) {
        this.mPropertyCloneList = propertyCloneList;
        this.mStatusBarColor = Color.BLACK;
        this.mNavigationBarColor = Color.BLACK;
    }

    public void setClipToPadding(boolean clipToPadding) {
        this.mClipToPadding = clipToPadding;
    }

    public void setStatusBarColor(int statusBarColor) {
        this.mStatusBarColor = statusBarColor;
    }

    public void setLightStatusBar(boolean lightStatusBar) {
        this.mLightStatusBar = lightStatusBar;
    }

    public void setNavigationBarColor(int navigationBarColor) {
        this.mNavigationBarColor = navigationBarColor;
    }

    public void setLightNavigationBar(boolean lightNavigationBar) {
        this.mLightNavigationBar = lightNavigationBar;
    }

    @Override
    public boolean isClipToPadding() {
        return mClipToPadding;
    }

    @Override
    public int statusBarColor() {
        return mStatusBarColor;
    }

    @Override
    public boolean lightStatusBar() {
        return mLightStatusBar;
    }

    @Override
    public int getNavigationBarColor() {
        return mNavigationBarColor;
    }

    @Override
    public boolean lightNavigationBar() {
        return mLightNavigationBar;
    }

    public boolean cloneBy(Object source) {
        index = 0;
        finishCloneProperty = false;

        doCloneBy(source);

        return finishCloneProperty;
    }

    private void doCloneBy(Object source) {
        if (index >= mPropertyCloneList.size()) {
            return;
        }

        PropertyClone propertyClone = mPropertyCloneList.get(index);

        index++;

        boolean hadSet = propertyClone.cloneBy(this, source);

        if (hadSet) {
            finishCloneProperty = true;
        }

        doCloneBy(source);
    }
}
