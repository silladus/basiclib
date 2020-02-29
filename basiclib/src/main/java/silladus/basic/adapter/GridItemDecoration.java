package silladus.basic.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    protected int size;
    private Paint dividerPaint;
    private float paddingLeft = 30;
    private float paddingRight = 30;

    private int columns = 2;

    public GridItemDecoration() {
        this(1);
    }

    public GridItemDecoration(int columns) {
        this(0, 2, 0, 0, columns);
    }

    public GridItemDecoration(@ColorInt int colorDivider, int size, int columns) {
        this(colorDivider, size, 0, 0, columns);
    }

    public GridItemDecoration(@ColorInt int colorDivider, int size, int paddingLeft, int paddingRight, int columns) {
        dividerPaint = new Paint();
        dividerPaint.setColor(colorDivider);
        this.size = size;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        this.columns = columns;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
////        boolean canScrollUp = parent.canScrollVertically(-1);
////        if (isDrawFirstTop && !parent.canScrollVertically(-1) && view == parent.getChildAt(0)) {
////            outRect.top = size;
////        }
//
//        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
//        if (parent.getAdapter().getItemCount() - itemPosition > columns) {
//            //竖直方向
//            outRect.bottom = size;
//        }
//
//        //水平方向，适用需要画竖直分割线的情况
//        if ((itemPosition + 1) % columns != 0) {
//            outRect.right = size;
//        }


        int spanCount;
        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) parent.getLayoutManager();
            spanCount = manager.getSpanCount();
        } else if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            spanCount = manager.getSpanCount();
        } else {
            spanCount = 1;
        }
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        outRect.left = column * size / spanCount;
        outRect.right = size - (column + 1) * size / spanCount;

        if (position >= spanCount) {
            outRect.top = size;
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, RecyclerView parent, @NonNull RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        float dividerLeft = left + paddingLeft;
        float dividerRight = right - paddingRight;

        int rows = childCount / columns;

        if (childCount % columns == 0) {
            drawColumns(rows, parent, c);
        } else {
            drawColumns(rows - 1, parent, c);
        }

        for (int i = 0; i < rows - 1; i++) {
            View view = parent.getChildAt(i * columns);
            float top = view.getBottom();
            float bottom = view.getBottom() + size;

            c.drawRect(dividerLeft, top, dividerRight, bottom, dividerPaint);
        }
    }

    private void drawColumns(int rows, RecyclerView parent, Canvas canvas) {
        for (int i = 0; i < rows; i++) {
            View view = parent.getChildAt(i * columns);
            float bottom = view.getBottom();
            float dTop = view.getTop();
            float dRight = view.getRight();
            for (int c = 1; c < columns; c++) {
                canvas.drawRect(dRight * c, dTop, dRight * c + size, bottom, dividerPaint);
            }
        }
    }

}
