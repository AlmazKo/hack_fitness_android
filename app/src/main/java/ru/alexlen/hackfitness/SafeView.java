package ru.alexlen.hackfitness;

import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class SafeView {
    public static final String TAG = "SafeView";

    private View parent;

    public SafeView(View view) {
        if (view == null) {
            throw new IllegalArgumentException(TAG + " set null parent");
        }

        parent = view;
    }


    /**
     * @throws RuntimeException
     */
    public View get(@IdRes int resId) {
        View v = parent.findViewById(resId);

        if (v == null) {
            throw new RuntimeException(TAG + " not found view with id=" + resId);
        }

        return v;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T get(Class<T> klass, @IdRes int resId) {
        View found = get(resId);

        if (!klass.isInstance(found)) {
            throw new RuntimeException(TAG + " wrong type. Required=" + klass.getName()
                                               + " found=" + found.getClass());
        }

        return (T) found;
    }

    public TextView getTextView(@IdRes int resId) {
        return get(TextView.class, resId);
    }


    @SuppressWarnings("unchecked")
    public <T extends View> T get(Class<T> targetClass, @IdRes int... resources) {

        View current = parent;

        for (int resId : resources) {
            current = current.findViewById(resId);
            if (current == null) {
                throw new RuntimeException(TAG + " not found view with id=" + resId);
            }
        }

        if (!targetClass.isInstance(current)) {
            throw new RuntimeException(TAG + " wrong type. Required=" + targetClass.getName()
                                               + " found=" + current.getClass());
        }

        return (T) current;
    }

    public SafeView setVisibility(@IdRes int resId, int visibility) {
        View view = parent.findViewById(resId);

        if (view == null) {
            Log.w(TAG, "Not found resource=" + resId + " in=" + parent);
            return this;
        }

        view.setVisibility(visibility);

        return this;
    }


    public SafeView setText(@IdRes int textResId, int text) {
        return setText(textResId, String.valueOf(text));
    }

    public SafeView setText(@IdRes int textResId, float text) {
        return setText(textResId, String.valueOf(text));
    }

    public SafeView setText(@IdRes int textResId, CharSequence text) {
        View view = parent.findViewById(textResId);

        if (view == null) {
            Log.w(TAG, "Not found resource=" + textResId + " in=" + parent);
            return this;
        }

        if (!(view instanceof TextView)) {
            Log.w(TAG, "Wrong type resource=" + textResId + " class=" + view.getClass());
            return this;
        }

        ((TextView) view).setText(text);

        return this;
    }
}