/**
 * Global Trading Technologies Ltd.
 *
 * The following source code is PROPRIETARY AND CONFIDENTIAL. Use of
 * this source code is governed by the Global Trading Technologies Ltd. Non-Disclosure
 * Agreement previously entered between you and Global Trading Technologies Ltd.
 *
 * By accessing, using, copying, modifying or distributing this
 * software, you acknowledge that you have been informed of your
 * obligations under the Agreement and agree to abide by those obligations.
 *
 * @author Alexander Suslov <alexander.suslov@alpari.org>
 * @date 16.10.2014
 */
package ru.alexlen.hackfitness.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ru.alexlen.hackfitness.BaseActivity;


/**
 * @link http://stackoverflow.com/questions/24471109/recyclerview-onclick/26196831#26196831
 */
abstract public class AbstractRecyclerView<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements RecyclerView.OnItemTouchListener {

    protected final BaseActivity mActivity;
    protected int mSelectedItem;

    private OnSelectClickListener mSelectListener;
    private GestureDetector mGestureDetector;

    protected AbstractRecyclerView(BaseActivity activity) {
        this.mActivity = activity;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public interface OnSelectClickListener {
        public void onSelect(@NotNull View item, int position);

        public void onUnSelect(@Nullable View item, int position);
    }


    public void setOnSelectListener(Context context, OnSelectClickListener listener) {
        mSelectListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        if (mSelectListener == null) return true;

        View item = view.findChildViewUnder(e.getX(), e.getY());
        if (item != null && mSelectListener != null && mGestureDetector.onTouchEvent(e)) {

            View prevView = findLastSelectedItem((RecyclerView) item.getParent());
            mSelectListener.onUnSelect(prevView, mSelectedItem);

            mSelectedItem = view.getChildPosition(item);
            mSelectListener.onSelect(item, mSelectedItem);

        }

        return false;
    }

    @Nullable
    private View findLastSelectedItem(RecyclerView rView) {

        LinearLayoutManager lm = (LinearLayoutManager) rView.getLayoutManager();

        View prevView = null;
        if (mSelectedItem > -1 &&
                mSelectedItem <= lm.findLastVisibleItemPosition() && mSelectedItem >= lm.findFirstVisibleItemPosition()) {

            int prevSelectedIndex = mSelectedItem - lm.findFirstVisibleItemPosition();
            prevView = rView.getChildAt(prevSelectedIndex);

            mSelectListener.onUnSelect(prevView, mSelectedItem);
        }

        return prevView;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    protected Resources getRes() {

        return mActivity.getResources();
    }

    protected int getColor(final int colorResId) {
        return getRes().getColor(colorResId);
    }
}