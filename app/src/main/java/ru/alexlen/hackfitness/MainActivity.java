package ru.alexlen.hackfitness;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import ru.alexlen.hackfitness.fragment.GymListFragment;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Список клубов");
        FragmentManager fm = getSupportFragmentManager();

        GymListFragment fg;

        fg = (GymListFragment) fm.findFragmentById(R.id.list_container);
        if (fg == null) {
            fm.beginTransaction()
                    .replace(R.id.list_container, GymListFragment.newInstance(null))
                    .commit();
        }
    }


}
