package ru.alexlen.hackfitness.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import ru.alexlen.hackfitness.BaseActivity;
import ru.alexlen.hackfitness.Config;
import ru.alexlen.hackfitness.GymActivity;
import ru.alexlen.hackfitness.GymSection;
import ru.alexlen.hackfitness.R;
import ru.alexlen.hackfitness.SafeView;
import ru.alexlen.hackfitness.VolleySingleton;
import ru.alexlen.hackfitness.api.Gym;
import ru.alexlen.hackfitness.api.Trainer;

/**
 * Created by almazko on 25/10/14.
 */
public class TrainerListAdapter extends AbstractRecyclerView<TrainerListAdapter.ViewHolder>
        implements AbstractRecyclerView.OnSelectClickListener {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView photo;
        ImageButton goButton;

        public ViewHolder(ViewGroup view) {
            super(view);
            SafeView ss = new SafeView(view);

            name = ss.get(TextView.class, R.id.txt_trainer_name);
            photo = ss.get(ImageView.class, R.id.img_trainer);
//            goButton = ss.get(ImageButton.class, R.id.btn_go);

            view.setTag(this);
        }

    }


    private final ArrayList<Trainer> mTrainers;
    private final ImageLoader mImageLoader;

    public TrainerListAdapter(BaseActivity activity, ArrayList<Trainer> trainers) {
        super(activity);

        mTrainers = trainers;

        mImageLoader = VolleySingleton.getInstance(activity).getImageLoader();

        setOnSelectListener(activity, this);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewGroup item = (ViewGroup) mActivity.getLayoutInflater().inflate(R.layout.item_trainer, viewGroup, false);


        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Trainer trainer = mTrainers.get(position);

        holder.name.setText(trainer.name);
        String url = Config.SITE + trainer.photo;

        mImageLoader.get(url, ImageLoader.getImageListener(holder.photo,
                android.R.drawable.screen_background_light_transparent, android.R.drawable.screen_background_light_transparent));

        holder.itemView.setBackgroundColor(Color.TRANSPARENT);

        holder.itemView.findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GymActivity) mActivity).selectSection(GymSection.SCHEDULE_TRAINER);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrainers.size();
    }

    @Override
    public void onSelect(@NotNull View item, int position) {


    }

    @Override
    public void onUnSelect(@Nullable View item, int position) {
    }
}
