package ru.alexlen.hackfitness;

import android.animation.ValueAnimator;
import android.graphics.Outline;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        findViewById(R.id.fab).setVisibility(View.INVISIBLE);


        final ImageButton button = (ImageButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator animHide = ValueAnimator.ofFloat(1, 0).setDuration(500);
                final ViewGroup.LayoutParams lp = button.getLayoutParams();
                final int size = lp.width;
                animHide.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        final float alpha = (Float) valueAnimator.getAnimatedValue();
                        button.setAlpha(alpha);
                        lp.width = (int) (size * alpha);
                        lp.height = (int) (size * alpha);
                        button.requestLayout();
                    }
                });

                animHide.start();
            }
        });

        setTitle("Clubs");


        ImageLoader imageLoader = VolleySingleton.getInstance(this).getImageLoader();

        Bundle extras = getIntent().getExtras();
        Gym gym = (Gym) extras.getSerializable("gym");


        ((TextView) findViewById(R.id.txt_gym_name)).setText(gym.name);
        ImageView gymLogo = (ImageView) findViewById(R.id.img_gym_logo);

        String url = Config.SITE + gym.logo;
        imageLoader.get(url, ImageLoader.getImageListener(gymLogo, 0, 0));

        FragmentManager fm = getSupportFragmentManager();

        if (extras.getBoolean("notify")) {
            selectAddress(null);
            return;
        }

        GymClubListFragment fg;

        fg = (GymClubListFragment) fm.findFragmentById(R.id.list_container);
        if (fg == null) {
            fm.beginTransaction()
                    .replace(R.id.list_container, GymClubListFragment.newInstance(extras))
                    .commit();
        }

    }

    public void selectAddress(GymAddress gymAddress) {

        addFragmentToBackStack(R.id.list_container, GymInfoListFragment.newInstance(null));
        findViewById(R.id.fab).setVisibility(View.VISIBLE);
    }

    public void selectSection(GymSection section) {

        findViewById(R.id.fab).setVisibility(View.INVISIBLE);

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
