package ru.alexlen.hackfitness;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alexander Suslov
 */
public class Utils {


    public static class SafeSetter {
        public SafeSetter(View view) {
        }


    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int getResId(String variableName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Find first View by class name
     * TODO do typed!
     */
    @Nullable
    public static View findViewByClass(ViewGroup parent, Class<? extends View> klass) {
        int count = parent.getChildCount();

        for (int i = 0; i <= count; i++) {
            View child = parent.getChildAt(i);


            if (klass.isInstance(child)) {
                return child;
            }

            if (child instanceof ViewGroup) {
                return findViewByClass((ViewGroup) child, klass);
            }
        }

        return null;
    }


    public static ArrayList<View> findViewsByClass(ViewGroup parent, Class<? extends View> klass, ArrayList<View> views) {
        int count = parent.getChildCount();

        views = new ArrayList<>();

        for (int i = 0; i <= count; i++) {
            View child = parent.getChildAt(i);


            if (klass.isInstance(child)) {
                views.add(child);
            }

            if (child instanceof ViewGroup) {
                views.addAll(findViewsByClass((ViewGroup) child, klass, views));
            }
        }

        return views;
    }

    public static ArrayList<View> getViewsByTag(ViewGroup root, String tag) {
        ArrayList<View> views = new ArrayList<>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }

        }
        return views;
    }

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        Adapter adapter = listView.getAdapter();
        if (adapter == null) return;

        int count = adapter.getCount();
        int totalHeight = 0;
        for (int i = 0; i < count; i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (count - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public static NumberFormat getNumberFormatter(int precision) {
        NumberFormat formatter = new DecimalFormat();
        formatter.setMinimumFractionDigits(precision);
        formatter.setMaximumFractionDigits(precision);

        return formatter;
    }
}
