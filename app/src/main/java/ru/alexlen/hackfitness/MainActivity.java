package ru.alexlen.hackfitness;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.Toast;

import ru.alexlen.hackfitness.api.Gym;
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

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.trainer_stub)
                        .setLargeIcon(Utils.drawableToBitmap(getDrawable(R.drawable.trainer_stub)))
                        .setContentTitle("HackFitness Team")
                        .setContentText("Success doesn't just come and find you. You have to go out and get it!")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Success doesn't just come and find you. You have to go out and get it!"));

        Intent intent = new Intent(this, GymActivity.class);

        Gym gym = new Gym();
        gym.name = "Alex Fitness";
        gym.logo = "i/gyms/AlexFitness.png";
        intent.putExtra("gym", gym);
        intent.putExtra("notify", true);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(GymActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }

}
