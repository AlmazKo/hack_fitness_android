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
 * @date 05.09.2014
 */
package ru.alexlen.hackfitness.fragment;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;

import java.lang.reflect.Field;

import ru.alexlen.hackfitness.BaseActivity;

abstract public class AbstractFragment extends Fragment {

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public static int getResId(String variableName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    protected Resources getRes() {
        return getActivity().getResources();
    }

    protected Drawable drawable(@DrawableRes int resId) {
        return getRes().getDrawable(resId);
    }

    protected int color(@ColorRes int colorResId) {
        return getRes().getColor(colorResId);
    }

}
