package silladus.sample.adapter.recyclerview;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by silladus on 2018/1/20/0020.
 * GitHub: https://github.com/silladus
 * Description:
 */
@Deprecated
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter implements View.OnClickListener {
    private final static int VIEW_TYPE_EMPTY = 0x1;
    private final static int VIEW_TYPE_ITEM = 0x2;
    private List<T> data;

    private int itemLayoutRes;
    private int emptyLayoutRes;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerViewAdapter(@LayoutRes int itemLayoutRes, @LayoutRes int emptyLayoutRes) {
        this.itemLayoutRes = itemLayoutRes;
        this.emptyLayoutRes = emptyLayoutRes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // To make the list item LayoutParams.MATCH_PARENT work of RecyclerView,
        // it's must be inflate by LayoutInflater.from(context).inflate(resource, parent, false).
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                return new RecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(itemLayoutRes, viewGroup, false), false);
            default:
                return new RecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(emptyLayoutRes, viewGroup, false), true);
        }
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == VIEW_TYPE_ITEM) {
            if (null != onItemClickListener) {
                viewHolder.itemView.setTag(i);
                viewHolder.itemView.setOnClickListener(this);
            }
            onBindViewHolder((RecyclerViewHolder) viewHolder, data.get(i), i);
        } else {
            if (emptyLayoutRes != 0) {
                onNoData((RecyclerViewHolder) viewHolder);
            }
        }

    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v, (Integer) v.getTag());
    }

    /**
     * If have a empty layout(emptyLayoutRes != 0), when data's size 0 it will be call.
     *
     * @param holder Empty view holder
     */
    protected void onNoData(RecyclerViewHolder holder) {}

    public abstract void onBindViewHolder(RecyclerViewHolder holder, T item, int i);

    @Override
    public int getItemCount() {
        if (emptyLayoutRes == 0) {
            return data == null ? 0 : data.size();
        } else {
            return (data == null || data.size() == 0) ? 1 : data.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (data == null || data.size() == 0) ? VIEW_TYPE_EMPTY : VIEW_TYPE_ITEM;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

}