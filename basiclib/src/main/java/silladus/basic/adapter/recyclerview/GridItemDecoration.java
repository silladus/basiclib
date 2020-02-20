package silladus.basic.adapter.recyclerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

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
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //竖直方向
        outRect.bottom = size;

        //水平方向，适用需要画竖直分割线的情况
        outRect.right = size;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
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
