package ru.alexlen.hackfitness.adapter;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
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
import ru.alexlen.hackfitness.GymActivity;
import ru.alexlen.hackfitness.R;
import ru.alexlen.hackfitness.SafeView;
import ru.alexlen.hackfitness.VolleySingleton;
import ru.alexlen.hackfitness.api.Gym;
import ru.alexlen.hackfitness.api.GymAddress;

/**
 * Created by almazko on 25/10/14.
 */
public class GymAddressListAdapter extends AbstractRecyclerView<GymAddressListAdapter.ViewHolder>
        implements AbstractRecyclerView.OnSelectClickListener {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView subway;
        TextView address;

        public ViewHolder(ViewGroup view) {
            super(view);
            SafeView ss = new SafeView(view);

            address = ss.get(TextView.class, R.id.txt_gym_address);
            subway = ss.get(TextView.class, R.id.txt_gym_address_subway);

            view.setTag(this);
        }

    }


    private final ArrayList<GymAddress> mAddresses;

    public GymAddressListAdapter(BaseActivity activity, ArrayList<GymAddress> gyms) {
        super(activity);

        mAddresses = gyms;

        setOnSelectListener(activity, this);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewGroup item = (ViewGroup) mActivity.getLayoutInflater().inflate(R.layout.item_address, viewGroup, false);


        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GymAddress gym = mAddresses.get(position);

        holder.address.setText(gym.address);
        holder.subway.setText(gym.subway);
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }


    @Override
    public void onSelect(@NotNull View item, int position) {
        item.setBackgroundColor(getColor(R.color.primary));

        ((GymActivity) mActivity).selectAddress(mAddresses.get(position));
    }

    @Override
    public void onUnSelect(@Nullable View item, int position) {
        if (item != null) item.setBackgroundColor(Color.TRANSPARENT);
    }
}
