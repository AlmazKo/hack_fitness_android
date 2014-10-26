package ru.alexlen.hackfitness;

import android.graphics.Outline;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.Toast;

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

        Toast.makeText(this, "Select own club", Toast.LENGTH_LONG).show();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("HackFitness");


    }


}
