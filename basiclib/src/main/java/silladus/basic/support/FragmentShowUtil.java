package silladus.basic.support;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author silladus
 * Created by silladus on 2017/3/30.
 * GitHub: https://github.com/silladus
 * Description:
 */

public class FragmentShowUtil {
    private int containerIdRes;
    private Fragment[] fragments;
    /**
     * 当前显示的fragment所在的数组下标
     */
    private int currentIndex = -1;
    private FragmentManager fm;

    public FragmentShowUtil(@IdRes int containerIdRes, FragmentManager fm, Fragment... fragments) {
        this.containerIdRes = containerIdRes;
        this.fm = fm;

        initFragmentArray(fragments);
    }

    /**
     * 检测Fragment是否已添加，已添加则取出，未添加则赋值
     *
     * @param fs 检测的目标数组
     */
    private void initFragmentArray(Fragment... fs) {
        // 创建等长数组容器
        fragments = new Fragment[fs.length];

        // 检查并初始化数组
        for (int i = 0; i < fs.length; i++) {
            Fragment f = fm.findFragmentByTag(getTag(fs[i], i));
            if (f != null) {
                fragments[i] = f;
            } else {
                fragments[i] = fs[i];
            }
        }
    }

    private String getTag(Fragment f, int i) {
        return f.getClass().getName() + "@" + i;
    }

    public void showItem(int index) {
        if (index < 0 || index >= fragments.length || index == currentIndex) {
            return;
        }

        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            Fragment f = fm.findFragmentByTag(getTag(fragments[i], i));
            if (index != i) {
                if (f != null) {
                    ft.hide(fragments[i]);
                }
            } else {
                if (f == null) {
                    ft.add(containerIdRes, fragments[i], getTag(fragments[i], i));
                } else {
                    ft.show(fragments[i]);
                }
            }
        }
        ft.commit();
        currentIndex = index;
    }

    public void replaceItem(int index) {
        if (index != currentIndex) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(containerIdRes, fragments[index]);
            ft.commit();
            currentIndex = index;
        }
    }

    public void destroy() {
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            Fragment af = fm.findFragmentByTag(getTag(fragments[i], i));
            if (af != null) {
                ft.remove(af);
            }
        }
        ft.commitAllowingStateLoss();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public Fragment[] getFragments() {
        return fragments;
    }

    public int getFragmentPosition(Class<? extends Fragment> cls) {
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i].getClass() == cls) {
                return i;
            }
        }
        throw new IllegalArgumentException("This fragment class is not found in the array.");
    }
}
