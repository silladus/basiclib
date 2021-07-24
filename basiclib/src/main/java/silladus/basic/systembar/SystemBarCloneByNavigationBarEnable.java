package silladus.basic.systembar;

public class SystemBarCloneByNavigationBarEnable implements PropertyClone {

    @Override
    public boolean cloneBy(DefaultSystemBar defaultSystemBar, Object source) {
        boolean hadSet = defaultSystemBar.cloneBy(source);
        if (source instanceof INavigationBar) {
            INavigationBar navigationBar = (INavigationBar) source;
            
            defaultSystemBar.setNavigationBarColor(navigationBar.getNavigationBarColor());
            defaultSystemBar.setLightNavigationBar(navigationBar.lightNavigationBar());

            return true;
        }
        return hadSet;
    }
}
