package ru.alexlen.hackfitness.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import ru.alexlen.hackfitness.BaseActivity;
import ru.alexlen.hackfitness.Config;
import ru.alexlen.hackfitness.R;
import ru.alexlen.hackfitness.SafeView;
import ru.alexlen.hackfitness.VolleySingleton;
import ru.alexlen.hackfitness.api.Gym;

/**
 * Created by almazko on 25/10/14.
 */
public class GymListAdapter extends AbstractRecyclerView<GymListAdapter.ViewHolder>
        implements AbstractRecyclerView.OnSelectClickListener {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView logo;

        public ViewHolder(ViewGroup view) {
            super(view);
            SafeView ss = new SafeView(view);

            name = ss.get(TextView.class, R.id.txt_gym_name);
            logo = ss.get(ImageView.class, R.id.img_gym_logo);

            view.setTag(this);
        }

    }


    private final ArrayList<Gym> mGyms;
    private final ImageLoader mImageLoader;

    public GymListAdapter(BaseActivity activity, ArrayList<Gym> gyms) {
        super(activity);

        mGyms = gyms;

        mImageLoader = VolleySingleton.getInstance(activity).getImageLoader();

        setOnSelectListener(activity, this);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewGroup item = (ViewGroup) mActivity.getLayoutInflater().inflate(R.layout.item_gym, viewGroup, false);


        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Gym gym = mGyms.get(position);

        holder.name.setText(gym.name);


        String url = Config.SITE + gym.logo;

        mImageLoader.get(url, ImageLoader.getImageListener(holder.logo,
                android.R.drawable.sym_def_app_icon, android.R.drawable.sym_def_app_icon));


        holder.itemView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return mGyms.size();
    }

    @Override
    public void onSelect(@NotNull View item, int position) {
        item.setBackgroundColor(getColor(R.color.primary));
        mActivity.showClubs(mGyms.get(position));
    }

    @Override
    public void onUnSelect(@Nullable View item, int position) {
        if (item != null) item.setBackgroundColor(Color.TRANSPARENT);
    }
}
