package silladus.basic.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by silladus on 2018/6/8/0008.
 * GitHub: https://github.com/silladus
 * Description:
 */
@Deprecated
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    @Deprecated
    private boolean isEmpty;

    @Deprecated
    public boolean isEmpty() {
        return isEmpty;
    }

    @Deprecated
    public RecyclerViewHolder(View itemView, boolean isEmpty) {
        this(itemView);

        this.isEmpty = isEmpty;
    }

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        this.mViews = new SparseArray<>();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

}
