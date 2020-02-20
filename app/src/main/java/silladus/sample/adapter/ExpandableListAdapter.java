package silladus.sample.adapter;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silladus on 2017/8/24/0024.
 * GitHub: https://github.com/silladus
 * Description:Use RecyclerView instead of ExpandableListView.
 *
 * @param <G>
 * @param <C>
 */
@Deprecated
public abstract class ExpandableListAdapter<G, C> extends BaseExpandableListAdapter {
    public static class ExpandEntry<G, C> {
        G mGroupData;
        List<C> mChildData;

        public void setGroupData(G mGroupData) {
            this.mGroupData = mGroupData;
        }

        public void setChildData(List<C> mChildData) {
            this.mChildData = mChildData;
        }
    }

    private List<ExpandEntry<G, C>> mData;

    private int pLayoutRes;
    private int cLayoutRes;

    public ExpandableListAdapter(int pLayoutRes, int cLayoutRes) {
        this.pLayoutRes = pLayoutRes;
        this.cLayoutRes = cLayoutRes;

    }

    @Override
    public int getGroupCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ExpandEntry<G, C> entry = mData.get(groupPosition);
        return entry == null ? 0 : entry.mChildData == null ? 0 : entry.mChildData.size();
    }

    @Override
    public G getGroup(int groupPosition) {
        return mData.get(groupPosition).mGroupData;
    }

    @Override
    public C getChild(int groupPosition, int childPosition) {
        return mData.get(groupPosition).mChildData.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        String id = groupPosition + "" + childPosition;
        return Long.valueOf(id);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void setData(List<ExpandEntry<G, C>> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setGroupData(List<G> gList) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        for (G g : gList) {
            ExpandEntry<G, C> item = new ExpandEntry<>();
            item.mGroupData = g;
            mData.add(item);
        }
        notifyDataSetChanged();
    }

    public void setChildData(int groupPosition, List<C> mChildData) {
        if (mData == null) {
            throw new NullPointerException("No GroupData");
        }
        mData.get(groupPosition).mChildData = mChildData;
    }

    public boolean existChildData(int groupPosition) {
        return getChildrenCount(groupPosition) > 0;
    }

    public List<C> getChildData(int groupPosition) {
        return mData.get(groupPosition).mChildData;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(convertView, parent, pLayoutRes);
        groupView(groupPosition, isExpanded, holder, getGroup(groupPosition));
        return holder.getConvertView();
    }

    public abstract void groupView(int groupPosition, boolean isExpanded, ViewHolder holder, G item);

    public abstract void childView(int groupPosition, int childPosition, boolean isLastChild, ViewHolder holder, C childItem);

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(convertView, parent, cLayoutRes);
        childView(groupPosition, childPosition, isLastChild, holder, getChild(groupPosition, childPosition));
        return holder.getConvertView();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
