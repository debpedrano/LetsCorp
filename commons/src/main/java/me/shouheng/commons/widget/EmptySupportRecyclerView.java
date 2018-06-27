package me.shouheng.commons.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import me.shouheng.commons.widget.fastscroll.FastScrollDelegate;
import me.shouheng.commons.widget.fastscroll.FastScrollDelegate.FastScrollable;

/**
 * Created by wangshouheng on 2017/3/31.*/
public class EmptySupportRecyclerView extends RecyclerView implements FastScrollable {

    private View emptyView;

    private FastScrollDelegate mFastScrollDelegate;

    public EmptySupportRecyclerView(Context context) {
        super(context);
        createFastScrollDelegate(context);
    }

    public EmptySupportRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFastScrollDelegate(context);
    }

    public EmptySupportRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createFastScrollDelegate(context);
    }

    // region empty support

    private AdapterDataObserver observer = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            showEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            showEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            showEmptyView();
        }
    };

    public void showEmptyView() {
        Adapter<?> adapter = getAdapter();
        if(adapter!=null && emptyView!=null){
            if(adapter.getItemCount()==0){
                emptyView.setVisibility(View.VISIBLE);
                EmptySupportRecyclerView.this.setVisibility(View.GONE);
            } else{
                emptyView.setVisibility(View.GONE);
                EmptySupportRecyclerView.this.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if(adapter != null){
            adapter.registerAdapterDataObserver(observer);
            observer.onChanged();
        }
    }

    public void setEmptyView(View v) {
        emptyView = v;
    }

    // endregion

    // region fast scroll

    private void createFastScrollDelegate(Context context) {
        mFastScrollDelegate = new FastScrollDelegate.Builder(this).build();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mFastScrollDelegate.onInterceptTouchEvent(ev) || super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mFastScrollDelegate.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mFastScrollDelegate.onAttachedToWindow();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (mFastScrollDelegate != null) {
            mFastScrollDelegate.onVisibilityChanged(changedView, visibility);
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mFastScrollDelegate.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected boolean awakenScrollBars() {
        return mFastScrollDelegate.awakenScrollBars();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mFastScrollDelegate.dispatchDrawOver(canvas);
    }

    @Override
    public void superOnTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
    }

    @Override
    public int superComputeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override
    public int superComputeVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }

    @Override
    public int superComputeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    public View getFastScrollableView() {
        return this;
    }

    @Override
    public FastScrollDelegate getFastScrollDelegate() {
        return mFastScrollDelegate;
    }

    @Override
    public void setNewFastScrollDelegate(FastScrollDelegate newDelegate) {
        if (newDelegate == null) {
            throw new IllegalArgumentException("setNewFastScrollDelegate must NOT be NULL.");
        }
        mFastScrollDelegate.onDetachedFromWindow();
        mFastScrollDelegate = newDelegate;
        newDelegate.onAttachedToWindow();
    }

    // endregion
}
