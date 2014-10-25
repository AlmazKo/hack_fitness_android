package ru.alexlen.hackfitness;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import ru.alexlen.hackfitness.api.Gym;

/**
 * Created by almazko on 25/10/14.
 */
abstract public class BaseActivity extends FragmentActivity {

    public void gymAddresses(Gym gym) {

        final Intent intent = new Intent();


        intent.putExtra("gym", gym);

        intent.setClass(this, GymActivity.class);
        this.startActivity(intent);
    }
}
