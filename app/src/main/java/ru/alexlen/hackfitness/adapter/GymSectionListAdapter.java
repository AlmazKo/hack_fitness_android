package ru.alexlen.hackfitness.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import ru.alexlen.hackfitness.BaseActivity;
import ru.alexlen.hackfitness.GymActivity;
import ru.alexlen.hackfitness.GymSection;
import ru.alexlen.hackfitness.R;
import ru.alexlen.hackfitness.SafeView;

/**
 * Created by almazko on 25/10/14.
 */
public class GymSectionListAdapter extends AbstractRecyclerView<GymSectionListAdapter.ViewHolder>
        implements AbstractRecyclerView.OnSelectClickListener {


    public static class MenuSection {

        final String description;
        final GymSection section;

        public MenuSection(GymSection section, String description) {
            this.description = description;
            this.section = section;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView description;

        public ViewHolder(ViewGroup view) {
            super(view);
            SafeView ss = new SafeView(view);

            description = ss.get(TextView.class, R.id.txt_section_description);

            view.setTag(this);
        }
    }


    private final ArrayList<MenuSection> mSections;

    public GymSectionListAdapter(BaseActivity activity, ArrayList<MenuSection> sections) {
        super(activity);

        mSections = sections;

        setOnSelectListener(activity, this);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewGroup item = (ViewGroup) mActivity.getLayoutInflater().inflate(R.layout.item_section, viewGroup, false);


        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MenuSection section = mSections.get(position);

        holder.description.setText(section.description);
    }

    @Override
    public int getItemCount() {
        return mSections.size();
    }


    @Override
    public void onSelect(@NotNull View item, int position) {
        item.setBackgroundColor(Color.GRAY);
        ((GymActivity) mActivity).selectSection(mSections.get(position).section);

    }

    @Override
    public void onUnSelect(@Nullable View item, int position) {
        if (item != null) item.setBackgroundColor(Color.TRANSPARENT);
    }
}
