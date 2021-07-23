package silladus.basic.systembar;

public class SystemBarCloneByStatusBarEnable implements PropertyClone {

    @Override
    public boolean cloneBy(DefaultSystemBar defaultSystemBar, Object source) {
        if (source instanceof IStatusBar) {
            IStatusBar statusBar = (IStatusBar) source;

            defaultSystemBar.setClipToPadding(statusBar.isClipToPadding());
            defaultSystemBar.setStatusBarColor(statusBar.statusBarColor());
            defaultSystemBar.setLightStatusBar(statusBar.lightStatusBar());
            
            return true;
        }
        return false;
    }
}
