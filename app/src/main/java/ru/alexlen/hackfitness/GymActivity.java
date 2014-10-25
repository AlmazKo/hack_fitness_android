package ru.alexlen.hackfitness;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import ru.alexlen.hackfitness.api.GymAddress;
import ru.alexlen.hackfitness.fragment.AbstractFragment;
import ru.alexlen.hackfitness.fragment.ClubScheduleFragment;
import ru.alexlen.hackfitness.fragment.GymAddressListFragment;
import ru.alexlen.hackfitness.fragment.GymInfoListFragment;
import ru.alexlen.hackfitness.fragment.TrainerListFragment;


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

        addFragmentToBackStack(R.id.list_container, GymInfoListFragment.newInstance(null));
    }

    public void selectSection(GymSection section) {
        switch (section) {

            case NEWS:
                break;
            case SCHEDULE:
                addFragmentToBackStack(R.id.list_container, ClubScheduleFragment.newInstance(null));
                break;
            case INFO:
                break;
            case TRAINERS:
                addFragmentToBackStack(R.id.list_container, TrainerListFragment.newInstance(null));

                break;
        }

    }
}
