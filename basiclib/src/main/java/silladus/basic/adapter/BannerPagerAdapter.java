package silladus.basic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import silladus.basic.WeakHandler;

/**
 * Created by silladus on 2018/3/16/0016.
 * GitHub: https://github.com/silladus
 * Description:
 */
public abstract class BannerPagerAdapter<T> extends PagerAdapter {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * Instantiate item.
     *
     * @param position item index.
     * @param context  view context.
     * @return ItemView.
     */
    @NonNull
    protected abstract View onDisplayItem(int position, Context context);

    /**
     * return the Integer.MAX_VALUE if want to always scroll able.
     * or return the real entry's size, like:
     * <pre><code>
     * {@literal @}@Override
     *             public int getCount() {
     *                 return getRealCount();
     *             }
     * </code></pre>
     *
     * @return item count
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public int getRealCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        position %= getRealCount();

        mAdRunnable.setupWithViewPager((ViewPager) container);

        View layout = onDisplayItem(position, container.getContext());

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private final AdRunnable mAdRunnable = new AdRunnable();

    static final class AdRunnable implements Runnable {
        private static final long DELAY_MILLIS = 5000;
        private int currentItem;
        private WeakHandler mHandler;
        private ViewPager mViewPager;
        private PagerAdapter adapter;

        private boolean isLoop = true;

        void setupWithViewPager(@NonNull ViewPager viewPager) {
            if (this.mViewPager == null) {
                this.mViewPager = viewPager;
                mHandler = new WeakHandler();
                adapter = viewPager.getAdapter();
                // Post Runnable to loop
                start();
            }
        }

        private void start() {
            if (isLoop && mHandler != null) {
                mHandler.postDelayed(this, DELAY_MILLIS);
            }
        }

        void startLoop() {
            if (!isLoop) {
                isLoop = true;
                start();
            }
        }

        void stopLoop() {
            isLoop = false;
            if (mHandler != null) {
                mHandler.removeCallbacks(this);
            }
        }

        @Override
        public void run() {
            currentItem = mViewPager.getCurrentItem();
            currentItem++;
            currentItem %= adapter.getCount();
            mViewPager.setCurrentItem(currentItem);

            start();
        }
    }

    public void startBannerLoop() {
        mAdRunnable.startLoop();
    }

    public void stopBannerLoop() {
        mAdRunnable.stopLoop();
    }
}
