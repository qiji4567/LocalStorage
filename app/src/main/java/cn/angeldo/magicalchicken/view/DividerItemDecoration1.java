package cn.angeldo.magicalchicken.view;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * 此类用于给Recycleview的item设置间隔
 * DividerItemDecoration
 * ESchoolbag <com.ftet.recyclerdisplay>
 * Created by vilyever on 2016/3/4.
 * Feature:
 * item间隙
 * 注意：暂时只支持{@link LinearLayoutManager},{@link GridLayoutManager}
 * 注意：仅保证每个item间距为设定值，不保证{@link android.support.v7.widget.RecyclerView.LayoutManager#getLeftDecorationWidth(View)}之类方法的具体数值
 */
public class DividerItemDecoration1 extends RecyclerView.ItemDecoration {
    final DividerItemDecoration1 self = this;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    /* Constructors */
    public DividerItemDecoration1() {
        this(0, 0);
    }

    public DividerItemDecoration1(int horizontalSpace, int verticalSpace) {
        this(horizontalSpace, verticalSpace, false);
    }

    public DividerItemDecoration1(int horizontalSpace, int verticalSpace, boolean edgeSpaceEqualInnerSpace) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
        this.edgeSpaceEqualInnerSpace = edgeSpaceEqualInnerSpace;
    }

    public DividerItemDecoration1(int horizontalSpace, int verticalSpace, Rect edgeSpace) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
        this.edgeSpace = edgeSpace;
    }


    /* Public Methods */


    /* Properties */
    /**
     * 横向间隔空间
     */
    private int horizontalSpace;
    public int getHorizontalSpace() {
        return horizontalSpace;
    }
    public DividerItemDecoration1 setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
        return this;
    }

    /**
     * 纵向间隔空间
     */
    private int verticalSpace;
    public int getVerticalSpace() {
        return verticalSpace;
    }
    public DividerItemDecoration1 setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
        return this;
    }

    /**
     * 边缘是否有间隔空间
     */
    private boolean edgeSpaceEqualInnerSpace;
    public boolean isEdgeSpaceEqualInnerSpace() {
        return edgeSpaceEqualInnerSpace;
    }
    public DividerItemDecoration1 setEdgeSpaceEqualInnerSpace(boolean edgeSpaceEqualInnerSpace) {
        this.edgeSpaceEqualInnerSpace = edgeSpaceEqualInnerSpace;
        return this;
    }

    private Rect edgeSpace = new Rect();
    public DividerItemDecoration1 setEdgeSpace(Rect edgeSpace) {
        this.edgeSpace = edgeSpace;
        return this;
    }
    public DividerItemDecoration1 setEdgeSpace(int left, int top, int right, int bottom) {
        return setEdgeSpace(new Rect(left, top, right, bottom));
    }
    public Rect getEdgeSpace() {
        return this.edgeSpace;
    }


    /* Overrides */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        int parentHorizontalSpace = parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight();
        int parentVerticalSpace = parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom();

        Rect rect = new Rect(0, 0, 0, 0);

        Rect edgeSpace = new Rect(getEdgeSpace());
        if (isEdgeSpaceEqualInnerSpace()) {
            edgeSpace.set(getHorizontalSpace(), getVerticalSpace(), getHorizontalSpace(), getVerticalSpace());
        }

        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();

            if (!isEdgeSpaceEqualInnerSpace() && !getEdgeSpace().equals(new Rect(0, 0, 0, 0))) {
                Log.w("DividerItemDecoration", "Custom edge space for span of GridLayoutManager is not support.");
            }

            boolean isVertical = layoutManager.getOrientation() == LinearLayoutManager.VERTICAL;
            int spanCount = layoutManager.getSpanCount();
            int spanIndex = position % spanCount;

            int crossCount = itemCount / spanCount + 1;
            int crossIndex = position / spanCount;

            int spanSpace = isVertical ? getHorizontalSpace() : getVerticalSpace();
            int crossSpace = isVertical ? getVerticalSpace() : getHorizontalSpace();

            int spanEdgeStart = isVertical ? edgeSpace.left : edgeSpace.top;
            int spanEdgeEnd = isVertical ? edgeSpace.right : edgeSpace.bottom;
            int crossEdgeStart = isVertical ? edgeSpace.top : edgeSpace.left;
            int crossEdgeEnd = isVertical ? edgeSpace.bottom : edgeSpace.right;

            int spanStart = isEdgeSpaceEqualInnerSpace() ? (spanSpace * (spanCount - spanIndex) / spanCount) : (spanSpace * spanIndex / spanCount);
            int spanEnd = isEdgeSpaceEqualInnerSpace() ? (spanSpace * (spanIndex + 1) / spanCount) : (spanSpace * (spanCount - spanIndex - 1) / spanCount);

            int crossStart = (crossIndex == 0) ? crossEdgeStart : 0;
            int crossEnd = (crossIndex == crossCount - 1) ? crossEdgeEnd : crossSpace;

            if (isVertical) {
                rect.set(spanStart, crossStart, spanEnd, crossEnd);
            }
            else {
                rect.set(crossStart, spanStart, crossEnd, spanEnd);
            }

            if (layoutManager.getReverseLayout()) {
                rect.set(rect.right, rect.bottom, rect.left, rect.top);
            }
        }
        else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            boolean isVertical = layoutManager.getOrientation() == LinearLayoutManager.VERTICAL;

            int extendSpace = (isVertical) ? getHorizontalSpace() : getVerticalSpace();
            int crossSpace = (isVertical) ? getVerticalSpace() : getHorizontalSpace();

            int extendEdgeStart = isVertical ? edgeSpace.left : edgeSpace.top;
            int extendEdgeEnd = isVertical ? edgeSpace.right : edgeSpace.bottom;
            int crossEdgeStart = isVertical ? edgeSpace.top : edgeSpace.left;
            int crossEdgeEnd = isVertical ? edgeSpace.bottom : edgeSpace.right;

            int extendStart = Math.max(extendSpace, extendEdgeStart);
            int extentEnd = Math.max(extendSpace, extendEdgeEnd);

            int crossStart = (position == 0) ? crossEdgeStart : 0;
            int crossEnd = (position == itemCount - 1) ? crossEdgeEnd : crossSpace;

            if (isVertical) {
                rect.set(extendStart, crossStart, extentEnd, crossEnd);
            }
            else {
                rect.set(crossStart, extendStart, crossEnd, extentEnd);
            }

            if (layoutManager.getReverseLayout()) {
                rect.set(rect.right, rect.bottom, rect.left, rect.top);
            }
        }

        outRect.set(rect);
    }


    /* Delegates */


    /* Private Methods */

}