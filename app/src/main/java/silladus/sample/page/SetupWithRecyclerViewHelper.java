package silladus.sample.page;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

/**
 * @author silladus
 * Created on 2018/10/25/0025.
 * GitHub: https://github.com/silladus
 * Description: Use RecyclerView as ViewPager and setup with TabLayout.
 */
public class SetupWithRecyclerViewHelper extends RecyclerView.OnScrollListener implements TabLayout.OnTabSelectedListener {
    private boolean isTabClick;
    private int ds;
    private TabLayout tabLayout;
    private RecyclerView mViewPager;

    public static void setup(TabLayout tabLayout, RecyclerView viewPager) {
        new SetupWithRecyclerViewHelper(tabLayout, viewPager);
    }

    private SetupWithRecyclerViewHelper(TabLayout tabLayout, RecyclerView viewPager) {
        this.tabLayout = tabLayout;
        this.mViewPager = viewPager;

        viewPager.setLayoutManager(new LinearLayoutManager(viewPager.getContext(),
                LinearLayoutManager.HORIZONTAL, false)
        );
        // 一个一个的滑动（完全ViewPager效果）
        new PagerSnapHelper().attachToRecyclerView(viewPager);

        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnScrollListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        isTabClick = true;
        mViewPager.smoothScrollToPosition(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            isTabClick = false;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        float ww;
        if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
            ww = recyclerView.getWidth();
            ds += dx;
        } else {
            ww = recyclerView.getHeight();
            ds += dy;
        }
        if (Math.abs(ds) >= ww) {
            ds = (int) (ds % ww);
        }
        //滑动后获取当前页
        int lastItemPosition = layoutManager.findLastVisibleItemPosition();
        if (ds != 0) {
            lastItemPosition--;
        }
        // 偏移参考位置的百分比
        float offsetPercent = ds < 0 ? 1 + ds / ww : ds / ww;
        if (!isTabClick) {
            tabLayout.setScrollPosition(lastItemPosition, offsetPercent, true);
        }
    }
}
