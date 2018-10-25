package silladus.sample.impl;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import silladus.basic.R;
import silladus.basic.adapter.CommonAdapter;
import silladus.basic.adapter.page.BasePage;

/**
 * @author silladus
 * @date 2017/8/23/0023
 * GitHub: https://github.com/silladus
 * Description:
 */
@Deprecated
public abstract class PageLayout<T> extends BasePage<T, ListView, CommonAdapter<T>> {

    public PageLayout(int reqCode, int pagerIndex, Context mContext) {
        this(reqCode, pagerIndex, mContext, R.layout.page_item_lv);
    }

    public PageLayout(int reqCode, int pagerIndex, Context mContext, @LayoutRes int layoutRes) {
        super(reqCode, pagerIndex, mContext, layoutRes);
    }

    /**
     * request success
     */
    @Override
    public void receiveData(List<T> mData, String tipsText) {
        if (getCurrentPart() == PART_START) {
            getData().clear();
        }
        getData().addAll(mData);
        getListAdapter().setData(getData());
        getListAdapter().notifyDataSetChanged();

        mPtrFrameLayout.refreshComplete();
        mProgressBar.setVisibility(View.GONE);
        updateTips(tipsText);
    }

}