package ru.alexlen.hackfitness.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.alexlen.hackfitness.BaseActivity;
import ru.alexlen.hackfitness.GymSection;
import ru.alexlen.hackfitness.R;
import ru.alexlen.hackfitness.adapter.GymAddressListAdapter;
import ru.alexlen.hackfitness.adapter.GymSectionListAdapter;
import ru.alexlen.hackfitness.widget.DividerItemDecoration;

import static ru.alexlen.hackfitness.adapter.GymSectionListAdapter.MenuSection;

/**
 * Created by almazko on 25/10/14.
 */
public class GymInfoListFragment extends AbstractFragment {


    private GymAddressListAdapter mAdapter;

    public static GymInfoListFragment newInstance(Bundle data) {

        GymInfoListFragment fg = new GymInfoListFragment();
        fg.setArguments(data);

        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

        final View rootView = inflater.inflate(R.layout.fg_list, container, false);

        final BaseActivity activity = (BaseActivity) getActivity();

        RecyclerView listSections = (RecyclerView) rootView.findViewById(R.id.list);

        ArrayList<MenuSection> sections = new ArrayList<>();

        sections.add(new MenuSection(GymSection.INFO,"Information"));
        sections.add(new MenuSection(GymSection.NEWS, "News"));
        sections.add(new MenuSection(GymSection.TRAINERS, "Trainers"));
        sections.add(new MenuSection(GymSection.SCHEDULE, "Schedule"));

        GymSectionListAdapter productsAdapter = new GymSectionListAdapter(activity, sections);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
        listSections.setLayoutManager(mLayoutManager);


        DividerItemDecoration itemDecoration =
                new DividerItemDecoration(getResources().getDrawable(R.drawable.divider_list));
        listSections.addItemDecoration(itemDecoration);


        listSections.addOnItemTouchListener(productsAdapter);
        listSections.setAdapter(productsAdapter);
        listSections.setHasFixedSize(true);

        return rootView;

    }

}