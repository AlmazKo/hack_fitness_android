package ru.alexlen.hackfitness.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

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
public class GymListAdapter extends AbstractRecyclerView<GymListAdapter.ViewHolder> implements AbstractRecyclerView.OnItemClickListener {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        HashMap<TextView, Integer> savedColors = new HashMap<>();

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

        setOnItemClickListener(activity, this);
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


    }

    @Override
    public int getItemCount() {
        return mGyms.size();
    }

    @Override
    public void onItemClick(View view, int position) {

    }



}
