package silladus.basic;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by silladus on 2018/5/14/0014.
 * GitHub: https://github.com/silladus
 * Description:
 */
public abstract class AbstractFragment extends Fragment {
    private boolean isPrepared;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared) {
            initData();
            // 只允许加载一次
            isPrepared = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void initView(View view, @Nullable Bundle savedInstanceState);

    protected abstract void initData();

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            // 视图创建以后才调用onPause，过滤生成实例后隐藏跳过onCreateView直接触发onPause
            if (getView() != null) {
                onPause();
            }
        } else {
            onResume();
        }
        setUserVisibleHint(!hidden);
    }
}
