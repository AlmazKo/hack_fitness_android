package ru.alexlen.hackfitness.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import java.util.ArrayList;

import ru.alexlen.hackfitness.BaseActivity;
import ru.alexlen.hackfitness.GymSection;
import ru.alexlen.hackfitness.R;
import ru.alexlen.hackfitness.SafeView;
import ru.alexlen.hackfitness.adapter.GymAddressListAdapter;
import ru.alexlen.hackfitness.adapter.GymSectionListAdapter;

import static ru.alexlen.hackfitness.adapter.GymSectionListAdapter.MenuSection;

/**
 * Created by almazko on 25/10/14.
 */
public class ClubScheduleFragment extends AbstractFragment {


    private GymAddressListAdapter mAdapter;
    private LayoutInflater mInflater;
    private GridLayout mGrid;
    private int mCellSize;

    public static ClubScheduleFragment newInstance(Bundle data) {

        ClubScheduleFragment fg = new ClubScheduleFragment();
        fg.setArguments(data);

        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

        mInflater = inflater;
        final View rootView = inflater.inflate(R.layout.fg_schedule, container, false);


        mGrid = (GridLayout) rootView.findViewById(R.id.grid_schedule);
        mCellSize = (int)dipToPixels(getBaseActivity(), 43);

        addCell(1, 1, "Yoga", color(R.color.event_yoga), false, 1);
        addCell(2, 2, "Yoga", color(R.color.event_yoga), false, 2);
        addCell(3, 2, "Yoga", color(R.color.event_yoga), false, 2);
        addCell(5, 1, "Yoga", color(R.color.event_yoga), false, 1);
        addCell(6, 3, "Yoga", color(R.color.event_yoga), false, 1);
        addCell(7, 2, "Yoga", color(R.color.event_yoga), false, 2);

        addCell(1, 4, "Body Stretch", color(R.color.event_body_stretch), false, 1);
        addCell(2, 4, "Body Stretch", color(R.color.event_body_stretch), false, 1);
        addCell(3, 4, "Body Stretch", color(R.color.event_body_stretch), false, 1);
        addCell(4, 4, "Body Stretch", color(R.color.event_body_stretch), false, 1);
        addCell(5, 4, "Body Stretch", color(R.color.event_body_stretch), false, 1);

        addCell(5, 7, "Power Class", color(R.color.event_power), true, 1);
        addCell(6, 9, "Power Class", color(R.color.event_power), true, 1);
        addCell(7, 9, "Power Class", color(R.color.event_power), true, 1);

        addCell(3, 14, "Tai-Bo", color(R.color.event_taibo), true, 2);
        addCell(4, 14, "Tai-Bo", color(R.color.event_taibo), true, 2);
        addCell(5, 14, "Tai-Bo", color(R.color.event_taibo), true, 2);
        addCell(6, 14, "Tai-Bo", color(R.color.event_taibo), true, 1);
        addCell(7, 14, "Tai-Bo", color(R.color.event_taibo), true, 2);

        addCell(1, 14, "Pilates", color(R.color.event_pilates), true, 2);
        addCell(2, 17, "Pilates", color(R.color.event_pilates), true, 2);
        addCell(3, 17, "Pilates", color(R.color.event_pilates), true, 2);
        addCell(6, 15, "Pilates", color(R.color.event_pilates), true, 2);
        addCell(7, 16, "Pilates", color(R.color.event_pilates), true, 2);

        return rootView;

    }


    private void addCell(int colId, int rowId, String name, int bgColor, boolean isGoing, int hours) {
        View cell = mInflater.inflate(R.layout.cell_schedule, mGrid, false);
        ViewGroup.LayoutParams lpCell = cell.getLayoutParams();
        SafeView sv = new SafeView(cell);

        sv.get(R.id.cell_content).setBackgroundColor(bgColor);
        sv.setText(R.id.cell_txt_event_type, name);

        GridLayout.Spec row = GridLayout.spec(rowId, hours);
        GridLayout.Spec colspan = GridLayout.spec(colId, 1);
        GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(row, colspan);
        gridLayoutParam.height = lpCell.height * hours;
        gridLayoutParam.width = lpCell.width;
        mGrid.addView(cell, gridLayoutParam);
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

}