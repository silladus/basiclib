package silladus.basic.systembar;

public interface PropertyClone {
    /**
     * 
     * @param defaultSystemBar which will set new property from the source.
     * @param source Property source
     * @return ture when some properties has set
     */
    boolean cloneBy(DefaultSystemBar defaultSystemBar, Object source);
}
