package silladus.basic;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

/**
 * Created by silladus on 2018/5/31/0031.
 * GitHub: https://github.com/silladus
 * Description:
 */
public interface IActivity extends IProcess {
    /**
     * To get a default Activity window config instance.
     *
     * @param config The config instance.
     */
    void onConfigInit(@NonNull ActivityInitConfig config);

    /**
     * Provide a layout resource.
     *
     * @return a layout resource reference.
     */
    @LayoutRes
    int getLayoutRes();

}
