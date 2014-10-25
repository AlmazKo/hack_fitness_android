package ru.alexlen.hackfitness;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import ru.alexlen.hackfitness.api.GymAddress;
import ru.alexlen.hackfitness.fragment.GymAddressListFragment;
import ru.alexlen.hackfitness.fragment.GymInfoListFragment;


public class GymActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        setTitle("Clubs");
        FragmentManager fm = getSupportFragmentManager();

        GymAddressListFragment fg;

        fg = (GymAddressListFragment) fm.findFragmentById(R.id.list_container);
        if (fg == null) {
            fm.beginTransaction()
                    .replace(R.id.list_container, GymAddressListFragment.newInstance(null))
                    .commit();
        }
    }


    public void selectAddress(GymAddress gymAddress) {
        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .replace(R.id.list_container, GymInfoListFragment.newInstance(null))
                .addToBackStack("notss")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }

    public void selectSection(GymSection section) {

    }
}
