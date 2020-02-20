package silladus.sample.impl;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.List;

import silladus.sample.adapter.ExpandableListAdapter;
import silladus.sample.adapter.page.BasePage;
import silladus.sample.R;

/**
 * @author silladus
 * @date 2017/8/23/0023
 * GitHub: https://github.com/silladus
 * Description:
 */
@Deprecated
public abstract class PageExpandableLayout<G, C> extends BasePage<ExpandableListAdapter.ExpandEntry<G, C>, ExpandableListView, ExpandableListAdapter<G, C>> {

    public PageExpandableLayout(int reqCode, int pagerIndex, Context mContext) {
        this(reqCode, pagerIndex, mContext, R.layout.page_item_expandable_lv);
    }

    public PageExpandableLayout(int reqCode, int pagerIndex, Context mContext, int layoutRes) {
        super(reqCode, pagerIndex, mContext, layoutRes);
    }

    /**
     * request success
     */
    @Override
    public void receiveData(List<ExpandableListAdapter.ExpandEntry<G, C>> mData, String tipsText) {
        if (getCurrentPart() == PART_START) {
            getData().clear();
        }
        getData().addAll(mData);
        getListAdapter().setData(getData());
        getListAdapter().notifyDataSetChanged();
        listView.setSelection(getData().size() - mData.size() - 1);

        mPtrFrameLayout.refreshComplete();
        mProgressBar.setVisibility(View.GONE);
        updateTips(tipsText);
    }
}
