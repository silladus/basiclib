package silladus.basic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * @author silladus
 * Created on 2018/7/14/0014.
 * GitHub: https://github.com/silladus
 * Description:
 */
public class BasicFragmentLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {

    /**
     * Bind views.
     */
    @Override
    public void onFragmentViewCreated(FragmentManager fm,
                                      Fragment f,
                                      View v,
                                      Bundle savedInstanceState) {

    }

    /**
     * Install some instance exclude View.
     */
    @Override
    public void onFragmentActivityCreated(FragmentManager fm,
                                          Fragment f,
                                          Bundle savedInstanceState) {
    }

    /**
     * Unbind views.
     */
    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {

    }

    /**
     * Release references.
     */
    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {

    }
}
