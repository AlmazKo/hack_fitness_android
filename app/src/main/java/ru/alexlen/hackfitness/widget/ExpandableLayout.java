package ru.alexlen.hackfitness.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import ru.alexlen.hackfitness.R;
import ru.alexlen.hackfitness.Utils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @author Almazko
 */
public class ExpandableLayout extends LinearLayout {

    final static String TAG = "ExpandableLayout";

    @LayoutRes private final int mHeaderId;
    @LayoutRes private final int mContentId;
    @IdRes private final     int mOnClickHandlerId;

    private boolean mExpanded;

    private View         mHeader;
    private View         mContent;
    private View         mOnClickHandler;
    private LinearLayout mContentWrapper;
    private String       mHeaderText;

    private LayoutParams mWrapperLp;
    private ValueAnimator             mAnimator;
    private int                       mContentHeight;
    private int                       mAnimTime;

    private final FindScroll findScroll = new FindScroll();

    public ExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLayout, 0, 0);

        try {
            mHeaderId = a.getResourceId(R.styleable.ExpandableLayout_header, 0);
            mContentId = a.getResourceId(R.styleable.ExpandableLayout_content, 0);
            mOnClickHandlerId = a.getResourceId(R.styleable.ExpandableLayout_onclick_handler, 0);
            mExpanded = a.getBoolean(R.styleable.ExpandableLayout_expanded, false);
            mHeaderText = a.getString(R.styleable.ExpandableLayout_header_text);

            int defaultAnimTime = getResources().getInteger(android.R.integer.config_longAnimTime);
            mAnimTime = a.getInt(R.styleable.ExpandableLayout_anim_time, defaultAnimTime);
        } finally {
            a.recycle();
        }

        if (mHeaderId == 0) {
            throw new IllegalArgumentException(
                    "The handle attribute is required and must refer to a valid header layout.");
        }

        setOrientation(VERTICAL);
    }

    public void setAnimationTime(int time) {
        mAnimTime = time;
    }

    public View getHeader() {
        return mHeader;
    }

    public ViewGroup getContentContainer() {
        return mContentWrapper;
    }


    public void setContent(View content) {

        Log.d(ExpandableLayout.this.toString(), "setContent " + content);

        if (mContentWrapper != null) {
            mContentWrapper.removeAllViews();
            mContentWrapper.addView(content);
            mContent = content;
        }
    }


    public void removeContent() {
        if (mContentWrapper != null) {
            mContentWrapper.removeAllViews();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mContent != null) {// TODO disable animation time?

            mContent.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
            mContentHeight = mContent.getMeasuredHeight();

            ///  Log.d(ExpandableLayout.this.toString(), "onMeasure: " + mContentHeight);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LayoutInflater inf = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHeader = inf.inflate(mHeaderId, ExpandableLayout.this, true);

        if (mHeaderText != null) {
            if (mHeader instanceof TextView) {

                ((TextView) mHeader).setText(mHeaderText);

            } else if (mHeader instanceof ViewGroup) {

                TextView headerTextView = (TextView) Utils.findViewByClass((ViewGroup) mHeader, TextView.class);
                if (headerTextView != null) {
                    headerTextView.setText(mHeaderText);
                }

            } else {
                Log.w(TAG, "Not found TextView in Header view for ser header_text");
            }
        }

        mContentWrapper = new LinearLayout(getContext());
        mContentWrapper.setOrientation(VERTICAL);
        mContentWrapper.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mContentWrapper.setId(Utils.generateViewId());
        } else {
            mContentWrapper.setId(View.generateViewId());
        }

        addView(mContentWrapper);

        if (mContentId != 0) {
            mContent = inf.inflate(mContentId, mContentWrapper, false);
            mContentWrapper.addView(mContent);
        }

        mContentWrapper.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {

                if (parent == mContentWrapper && child != mContent) {
                    mContent = child;
                }
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
            }
        });

        mWrapperLp = (LayoutParams) mContentWrapper.getLayoutParams();

        if (mExpanded) {
            mWrapperLp.height = LayoutParams.WRAP_CONTENT;
            View icoExpand = mHeader.findViewById(R.id.ico_expand);
            icoExpand.setRotation(-180);
        } else {
            mWrapperLp.height = 0;
        }

        if (mOnClickHandlerId == 0) {
            mHeader.setOnClickListener(new ContentOnClickListener());
        } else {

            mOnClickHandler = findViewById(mOnClickHandlerId);
            if (mOnClickHandler == null) {
                throw new IllegalArgumentException("Not found clickable view");
            }

            mOnClickHandler.setOnClickListener(new ContentOnClickListener());
        }
    }


    private class FindScroll {
        private ScrollView mLastScroll = null;

        @Nullable
        private ViewParent _findParentScroll(ViewParent target) {

            target = target.getParent();

            if (target == null) return mLastScroll;

            if (target instanceof ScrollView) {

                if (mLastScroll != null) {
                    Log.w(TAG, "Nested similar scroll detected, pick top ScrollView");
                }

                mLastScroll = (ScrollView) target;


                //если жесткто установлены параметры разметки, берем найденный
                if (mLastScroll.getLayoutParams().height > 0) {

                    return mLastScroll;
                }
            }

            return _findParentScroll(target);
        }

        @Nullable
        ScrollView findParentScroll() {
            mLastScroll = null;
            ViewParent target = _findParentScroll(ExpandableLayout.this);

            if (target == null) return null;
            return (ScrollView) target;
        }

        int calcTopInScroll(View view) {

            int value = 0;
            View current = view;

            ScrollView parent = findParentScroll();
            if (parent == null) return 0;

            while (current != null && parent != current) {
                value += current.getTop();
                current = (View) current.getParent();
            }

            return value;
        }

    }

    private class ContentOnClickListener implements OnClickListener {
        public void onClick(View v) {

            int measuredContentHeight = 0;

            if (mContent != null) {
                measuredContentHeight = mContent.getMeasuredHeight();
            }

            Log.d(ExpandableLayout.this.toString(), "ContentOnClickListener: content size: " + mContentHeight + ", " + measuredContentHeight);

            final int contentHeight = Math.max(mContentHeight, measuredContentHeight);

            if (contentHeight == 0) {
                Log.d(ExpandableLayout.this.toString(), "ContentOnClickListener has empty content");
                // TODO add empty behavior
            }

            final View icoExpand = mHeader.findViewById(R.id.ico_expand);

            ValueAnimator.AnimatorUpdateListener expander;
            final ScrollView scroll = findScroll.findParentScroll();
            if (scroll == null) {
                Log.i(ExpandableLayout.this.toString(), "Not found ScrollView's parent!");

                expander = new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        final float alpha = (float) valueAnimator.getAnimatedValue();
                        mContentWrapper.setAlpha(alpha);
                        mWrapperLp.height = (int) (alpha * contentHeight);

                        if (icoExpand != null) icoExpand.setRotation((int) (-alpha * 180));

                        mContentWrapper.requestLayout();
                    }
                };
            } else {

                final int scrollY = scroll.getScrollY();
                LayoutParams lp = (LayoutParams) getLayoutParams();
                final int marginTop = (lp != null) ? lp.topMargin : 0;
                final int marginBottom = (lp != null) ? lp.bottomMargin : 0;

                final int top = findScroll.calcTopInScroll(ExpandableLayout.this) - scrollY - marginTop;
                final int bottom = top + mHeader.getHeight() + marginTop + marginBottom;
                final int scrollHeight = scroll.getHeight();

                Log.i(TAG, "ScrollView height=" + scrollHeight + ", scrollY=" + scrollY +
                        ". Top position in ScrollView=" + top);

                expander = new ValueAnimator.AnimatorUpdateListener() {
                    boolean isFit = false;
                    int exceeded = 0;

                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        final float alpha = (float) valueAnimator.getAnimatedValue();
                        mContentWrapper.setAlpha(alpha);
                        mWrapperLp.height = (int) (alpha * contentHeight);

                        exceeded = mWrapperLp.height + bottom - scrollHeight;
                        if (exceeded > 0) {
                            if (top > exceeded) {
                                scroll.scrollTo(0, scrollY + exceeded);
                            } else if (!isFit) {
                                isFit = true;
                                scroll.scrollTo(0, scrollY + top);
                            }
                        }

                        if (icoExpand != null) icoExpand.setRotation((int) (-alpha * 180));

                        mContentWrapper.requestLayout();
                    }
                };
            }

            ValueAnimator.AnimatorUpdateListener collapser = new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    final float alpha = (float) valueAnimator.getAnimatedValue();
                    mContentWrapper.setAlpha(alpha);
                    mWrapperLp.height = (int) (alpha * contentHeight);
                    mContentWrapper.requestLayout();

                    if (icoExpand != null) icoExpand.setRotation((int) (-alpha * 180));
                }
            };

            starAnimate(collapser, expander);
        }
    }

    private void starAnimate(ValueAnimator.AnimatorUpdateListener collapser, ValueAnimator.AnimatorUpdateListener expander) {


        if (mExpanded) {

            // start collapse
            mExpanded = !mExpanded;

            if (mAnimator != null && mAnimator.isRunning()) {

                mAnimator.cancel();
                float value = (float) mAnimator.getAnimatedValue();
                mAnimator = ValueAnimator.ofFloat(value, 0);
                mAnimator.addUpdateListener(collapser);
                mAnimator.setDuration((int) (mAnimTime * value));

            } else {
                mAnimator = ValueAnimator.ofFloat(1, 0);
                mAnimator.addUpdateListener(collapser);
                mAnimator.setDuration(mAnimTime);
            }


        } else {

            // start expand
            mExpanded = !mExpanded;
            if (mAnimator != null && mAnimator.isRunning()) {
                mAnimator.cancel();
                float value = (float) mAnimator.getAnimatedValue();
                mAnimator = ValueAnimator.ofFloat(value, 1);
                mAnimator.addUpdateListener(expander);
                mAnimator.setDuration((int) (mAnimTime * (1 - value)));

            } else {
                mAnimator = ValueAnimator.ofFloat(0, 1);
                mAnimator.addUpdateListener(expander);
                mAnimator.setDuration(mAnimTime);
            }
        }

        mAnimator.start();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ExpandableLayout:" + getId());


        if (mHeaderText != null) {
            sb.append(" \"");
            sb.append(mHeaderText);
            sb.append("\"");
        }

        if (mExpanded) {
            sb.append(" ^");
        } else {
            sb.append(" v");
        }

        return sb.toString();
    }

    public interface OnExpandListener {
        public void onExpand(View handle, View content);

        public void onCollapse(View handle, View content);
    }
}