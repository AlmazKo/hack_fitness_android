package ru.alexlen.hackfitness;

import android.graphics.Outline;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import ru.alexlen.hackfitness.api.Gym;
import ru.alexlen.hackfitness.api.GymAddress;
import ru.alexlen.hackfitness.fragment.ClubScheduleFragment;
import ru.alexlen.hackfitness.fragment.GymClubListFragment;
import ru.alexlen.hackfitness.fragment.GymInfoListFragment;
import ru.alexlen.hackfitness.fragment.TrainerListFragment;


public class GymActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        setTitle("Clubs");


        ImageLoader imageLoader = VolleySingleton.getInstance(this).getImageLoader();

        Bundle extras = getIntent().getExtras();
        Gym gym = (Gym) extras.getSerializable("gym");


        ((TextView) findViewById(R.id.txt_gym_name)).setText(gym.name);
        ImageView gymLogo = (ImageView) findViewById(R.id.img_gym_logo);

        String url = Config.SITE + gym.logo;
        imageLoader.get(url, ImageLoader.getImageListener(gymLogo, 0, 0));

        FragmentManager fm = getSupportFragmentManager();

        GymClubListFragment fg;

        fg = (GymClubListFragment) fm.findFragmentById(R.id.list_container);
        if (fg == null) {
            fm.beginTransaction()
                    .replace(R.id.list_container, GymClubListFragment.newInstance(extras))
                    .commit();
        }

        //Outline
        int size = 100; //getResources().getDimensionPixelSize(R.dimen.fab_size);
        Outline outline = new Outline();
        outline.setOval(0, 0, size, size);
        //((ImageButton)findViewById(R.id.fab)).setOutlineProvider(outline);

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
