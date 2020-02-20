package silladus.sample.impl;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.List;

import silladus.sample.adapter.page.BasePage;
import silladus.sample.adapter.recyclerview.RecyclerViewAdapter;
import silladus.sample.R;

/**
 * @author silladus
 * @date 2017/8/23/0023
 * GitHub: https://github.com/silladus
 * Description:
 */
@Deprecated
public abstract class RecyclerPageLayout<T> extends BasePage<T, RecyclerView, RecyclerViewAdapter<T>> {

    public RecyclerPageLayout(int reqCode, int pagerIndex, Context mContext) {
        this(reqCode, pagerIndex, mContext, R.layout.page_item_recyclerview);
    }

    public RecyclerPageLayout(int reqCode, int pagerIndex, Context mContext, @LayoutRes int layoutRes) {
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