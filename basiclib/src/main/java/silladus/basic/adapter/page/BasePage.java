package silladus.basic.adapter.page;

import android.content.Context;
import androidx.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;
import silladus.basic.R;

/**
 * Created by silladus on 2018/1/22/0022.
 * GitHub: https://github.com/silladus
 * Description:
 */

public abstract class BasePage<T, LV extends View, A> implements PtrHandler2, IData {
    public static final int PART_START = 1;
    private ViewGroup rootView;

    private int reqCode;
    private int mCurrentPart;
    private int pagerIndex;
    private A mListAdapter;
    private List<T> data;

    protected LV listView;
    protected PtrFrameLayout mPtrFrameLayout;
    private TextView tips;
    protected ProgressBar mProgressBar;

    public BasePage(int reqCode, int pagerIndex, Context mContext, @LayoutRes int layoutRes) {
        this.reqCode = reqCode;
        this.pagerIndex = pagerIndex;
        rootView = (ViewGroup) View.inflate(mContext, layoutRes, null);
        bindView();
        initView();
    }

    private void bindView() {
        listView = rootView.findViewById(R.id.listView);
        mPtrFrameLayout = rootView.findViewById(R.id.m_ptr_frame);
        tips = rootView.findViewById(R.id.tips);
        mProgressBar = rootView.findViewById(R.id.mProgressBar);
    }

    private void initView() {
        //下拉刷新头
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(rootView.getContext());
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        //上拉加载更多尾
        PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(rootView.getContext());
        mPtrFrameLayout.setFooterView(footer);
        mPtrFrameLayout.addPtrUIHandler(footer);
        mPtrFrameLayout.setPtrHandler(this);

        mListAdapter = createListAdapter(listView);
    }

    /**
     * @param mode {@link in.srain.cube.views.ptr.PtrFrameLayout.Mode}
     */
    public void setMode(PtrFrameLayout.Mode mode) {
        mPtrFrameLayout.setMode(mode);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        tips.setVisibility(View.GONE);
        requestData(PART_START);
    }

    @Override
    public void onLoadMoreBegin(PtrFrameLayout frame) {
        tips.setVisibility(View.GONE);
        mCurrentPart++;
        requestData(mCurrentPart);
    }

    //-----------------------------------------下拉刷新，上拉加载更多---------------------------------

    /**
     * 判断是否可以下拉刷新。 UltraPTR 的 Content 可以包含任何内容，用户在这里判断决定是否可以下拉。
     * 例如，如果 Content 是 TextView，则可以直接返回 true，表示可以下拉刷新。
     * 如果 Content 是 ListView，当第一条在顶部时返回 true，表示可以下拉刷新。
     * 如果 Content 是 ScrollView，当滑动到顶部时返回 true，表示可以刷新。
     * {@literal https://github.com/captainbupt/android-Ultra-Pull-To-Refresh-With-Load-More}
     */
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
        return PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer);
    }

    public void requestData(int partIndex) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mCurrentPart = partIndex;
        initData(reqCode, mCurrentPart, pagerIndex);
    }

    /**
     * 该方法接受生成的数据适配器
     *
     * @param listView 列表显示控件
     * @return adapter
     */
    public abstract A createListAdapter(LV listView);

    /**
     * request success
     */
    public abstract void receiveData(List<T> mData, String tipsText);

    public void updateTips(String tipsText) {
        if (data.size() == 0) {
            tips.setText(tipsText);
            tips.setVisibility(View.VISIBLE);
        } else {
            tips.setVisibility(View.GONE);
        }
    }

    /**
     * request fail
     */
    public void onErrorCall(String tipsText) {
        mProgressBar.setVisibility(View.GONE);
        tips.setText(tipsText);
        tips.setVisibility(View.VISIBLE);
    }

    /**
     * Get the current page index.A List has many sublist, a sublist as a page.
     *
     * @return The current page index.
     */
    public int getCurrentPart() {
        return mCurrentPart;
    }

    /**
     * Get the data form request result.
     *
     * @return Data entry.
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Get list adapter.
     *
     * @return list adapter.
     */
    public A getListAdapter() {
        return mListAdapter;
    }

    public ViewGroup attachToRoot(ViewGroup container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            if (rootView == container.getChildAt(i)) {
                return rootView;
            }
        }
        container.addView(rootView);
        return rootView;
    }

    protected int getPagerIndex() {
        return pagerIndex;
    }
}
