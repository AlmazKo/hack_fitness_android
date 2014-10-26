package ru.alexlen.hackfitness;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import ru.alexlen.hackfitness.api.Gym;
import ru.alexlen.hackfitness.fragment.AbstractFragment;

/**
 * Created by almazko on 25/10/14.
 */
abstract public class BaseActivity extends FragmentActivity {

    public void showClubs(Gym gym) {

        final Intent intent = new Intent();
        intent.putExtra("gym", gym);
        intent.setClass(this, GymActivity.class);

        this.startActivity(intent);
    }


    void addFragmentToBackStack(@IdRes int resId, AbstractFragment fg) {
        FragmentManager fm = getSupportFragmentManager();


        fm.beginTransaction()
                .replace(resId, fg)
                .addToBackStack(fg.toString())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
