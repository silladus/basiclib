package silladus.sample.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by silladus on 2017/3/3.
 * GitHub: https://github.com/silladus
 * Description: Use RecyclerView to instead.
 */

@Deprecated
public class ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;

    public ViewHolder(ViewGroup parent, int layoutId) {
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static ViewHolder get(View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new ViewHolder(parent, layoutId);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId viewId
     * @return view
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

}
