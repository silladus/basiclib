package silladus.sample;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import silladus.basic.ActivityInitConfig;
import silladus.basic.IStatusBar;

/**
 * Created by silladus on 2018/5/31/0031.
 * GitHub: https://github.com/silladus
 * Description:
 */
public abstract class BasicActivityLifecycleCallbacksImp
        extends SimpleActivityLifecycleCallbacks
        implements IStatusBar {

    @CallSuper
    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
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
