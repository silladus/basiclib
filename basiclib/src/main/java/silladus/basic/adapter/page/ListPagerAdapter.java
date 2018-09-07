package silladus.basic.adapter.page;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by silladus on 2018/1/20/0020.
 * GitHub: https://github.com/silladus
 * Description:
 *
 * @param <T> Entry type.
 * @param <LV> ListView or RecyclerView.
 * @param <A> ListAdapter.
 */

public abstract class ListPagerAdapter<T, LV extends View, A> extends PagerAdapter {
    private String[] titleText;
    private SparseArray<BasePage<T, LV, A>> pageArr;
    private int[] reqCode;

    public ListPagerAdapter() {
        pageArr = new SparseArray<>();
    }

    @NonNull
    protected abstract BasePage<T, LV, A> initPageLayout(int index, Context context);

    public void setTitles(String... titleText) {
        this.titleText = titleText;
    }

    public void setReqCode(int... reqCode) {
        this.reqCode = reqCode;
    }

    public int[] getReqCode() {
        return reqCode;
    }

    @Override
    public int getCount() {
        return reqCode.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        BasePage<T, LV, A> page = getPage(position, container.getContext());
        if (page.getData() == null) {
            page.requestData(BasePage.PART_START);
        }
        return page.attachToRoot(container);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleText[position];
    }

    @NonNull
    private BasePage<T, LV, A> getPage(int position, Context context) {
        BasePage<T, LV, A> page = getPageLayout(position);
        if (page == null) {
            pageArr.put(position, page = initPageLayout(position, context));
        }
        return page;
    }

    @Nullable
    public final BasePage<T, LV, A> getPageLayout(int position) {
        if (position >= getCount()) {
            throw new IndexOutOfBoundsException("Length is " + getCount() + ", index is " + position);
        }
        return pageArr.get(position);
    }
}
