package silladus.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by silladus on 2018/5/31/0031.
 * GitHub: https://github.com/silladus
 * Description:
 */
public abstract class BasicActivityLifecycleCallbacksImp
        extends SimpleActivityLifecycleCallbacks
        implements IStatusBar2 {

    @CallSuper
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // Common Activity Config.
        new ActivityInitConfig(activity, this);

        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity)
                    .getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(
                            new BasicFragmentLifecycleCallbacks(),
                            true
                    );
        }

    }

    @Override
    public boolean isClipToPadding() {
        return true;
    }
}
