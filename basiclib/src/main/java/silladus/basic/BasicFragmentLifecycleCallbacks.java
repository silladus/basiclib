package silladus.basic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
    public void onFragmentViewCreated(@NonNull FragmentManager fm,
                                      @NonNull Fragment f,
                                      @NonNull View v,
                                      Bundle savedInstanceState) {

    }

    /**
     * Install some instance exclude View.
     */
    @Override
    public void onFragmentActivityCreated(@NonNull FragmentManager fm,
                                          @NonNull Fragment f,
                                          Bundle savedInstanceState) {
    }

    /**
     * Unbind views.
     */
    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {

    }

    /**
     * Release references.
     */
    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {

    }
}
