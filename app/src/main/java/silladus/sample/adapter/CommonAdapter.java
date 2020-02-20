package silladus.sample.adapter;

import androidx.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by silladus on 2017/6/23/0023.
 * GitHub: https://github.com/silladus
 * Description: Use RecyclerView instead of ListView and GridView.
 *
 * @param <T>
 */
@Deprecated
public abstract class CommonAdapter<T> extends BaseAdapter {
    private List<T> mData;
    private int itemLayoutRes;

    public CommonAdapter(@LayoutRes int itemLayoutRes) {
        super();
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        if (null != mData) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(convertView, parent, itemLayoutRes);
        convert(holder, getItem(position), position);
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T item, int position);

    public void setData(List<T> mData) {
        this.mData = mData;
        //notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

}