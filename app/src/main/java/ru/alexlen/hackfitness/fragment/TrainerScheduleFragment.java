package ru.alexlen.hackfitness.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.PopupWindow;

import ru.alexlen.hackfitness.R;
import ru.alexlen.hackfitness.SafeView;

/**
 * Created by almazko on 25/10/14.
 */
public class TrainerScheduleFragment extends AbstractFragment {

    public static class Event {

        public int bgColor;
        public boolean isGoing;
        public int hours;

        View cell;

        public Event(int bgColor, boolean isGoing, int hours) {

            this.bgColor = bgColor;
            this.isGoing = isGoing;
            this.hours = hours;
        }
    }


    class DetailOnClickListener implements View.OnClickListener {

        private LayoutInflater mInflater;
        private Event mEvent;

        public DetailOnClickListener(LayoutInflater inflater, Event event) {
            mInflater = inflater;
            mEvent = event;
        }

        @Override
        public void onClick(View view) {

            final View popupView = mInflater.inflate(R.layout.view_tooltip_schedule_event, null);
            SafeView sv = new SafeView(popupView);

            sv.get(R.id.tooltip_title).setBackgroundColor(mEvent.bgColor);

            final PopupWindow popupWindow = new PopupWindow(popupView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);


            popupWindow.setOutsideTouchable(true);

            popupWindow.setBackgroundDrawable(new BitmapDrawable(getActivity().getResources(),
                    Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)));

            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 40);


            popupView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
            popupView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();

                    if (mEvent.cell != null) {
                        mEvent.cell.findViewById(R.id.cell_content).setBackgroundColor(color(R.color.selected));
                    }

                }
            });

        }
    }


    private LayoutInflater mInflater;
    private GridLayout mGrid;

    public static TrainerScheduleFragment newInstance(Bundle data) {

        TrainerScheduleFragment fg = new TrainerScheduleFragment();
        fg.setArguments(data);

        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

        mInflater = inflater;
        final View rootView = inflater.inflate(R.layout.fg_trainer_schedule, container, false);


        mGrid = (GridLayout) rootView.findViewById(R.id.grid_schedule);


        for (int col = 1; col <= 5; col++) {
            for (int row = 4; row < 11; row++) {
                addCell(col, row, new Event(color(R.color.primary), true, 1));
            }

        }

        addCell(3, 4, new Event(Color.RED, true, 1));
        addCell(3, 8, new Event(Color.RED, true, 1));
        addCell(4, 4, new Event(Color.RED, true, 1));
        addCell(4, 5, new Event(Color.RED, true, 1));
        addCell(4, 6, new Event(Color.RED, true, 1));

        return rootView;

    }


    private void addCell(int colId, int rowId, Event event) {
        View cell = mInflater.inflate(R.layout.cell_schedule, mGrid, false);

        if (event.bgColor != Color.RED) {
            event.cell = cell;
            cell.setOnClickListener(new DetailOnClickListener(mInflater, event));
        }

        ViewGroup.LayoutParams lpCell = cell.getLayoutParams();
        SafeView sv = new SafeView(cell);

        sv.get(R.id.cell_content).setBackgroundColor(event.bgColor);


        GridLayout.Spec row = GridLayout.spec(rowId, event.hours);
        GridLayout.Spec colspan = GridLayout.spec(colId, 1);
        GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(row, colspan);
        gridLayoutParam.height = lpCell.height * event.hours;
        gridLayoutParam.width = lpCell.width;
        mGrid.addView(cell, gridLayoutParam);
    }
}