package ru.alexlen.hackfitness.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import ru.alexlen.hackfitness.R;
import ru.alexlen.hackfitness.SafeView;

/**
 * Created by almazko on 25/10/14.
 */
public class ClubScheduleFragment extends AbstractFragment {

    public static class Event {
        public String name;
        public int bgColor;
        public boolean isGoing;
        public int hours;

        public Event(String name, int bgColor, boolean isGoing, int hours) {
            this.name = name;
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


            sv.setText(R.id.tooltip_title, mEvent.name);
            sv.get(R.id.tooltip_title).setBackgroundColor(mEvent.bgColor);

            final PopupWindow popupWindow = new PopupWindow(popupView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);


            popupWindow.setOutsideTouchable(true);

            popupWindow.setBackgroundDrawable(new BitmapDrawable(getActivity().getResources(),
                    Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)));

//            popupWindow.showAsDropDown(view, 0, 0);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 40);


            popupView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });

        }
    }


    private LayoutInflater mInflater;
    private GridLayout mGrid;

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

        addCell(1, 1, new Event("Yoga", color(R.color.event_yoga), false, 1));
        addCell(2, 2, new Event("Yoga", color(R.color.event_yoga), false, 1));
        addCell(3, 2, new Event("Yoga", color(R.color.event_yoga), false, 1));
        addCell(5, 1, new Event("Yoga", color(R.color.event_yoga), false, 1));
        addCell(6, 3, new Event("Yoga", color(R.color.event_yoga), false, 1));
        addCell(7, 2, new Event("Yoga", color(R.color.event_yoga), false, 1));

        addCell(1, 4, new Event("Body Stretch", color(R.color.event_body_stretch), false, 1));
        addCell(2, 4, new Event("Body Stretch", color(R.color.event_body_stretch), false, 1));
        addCell(3, 4, new Event("Body Stretch", color(R.color.event_body_stretch), false, 1));
        addCell(4, 4, new Event("Body Stretch", color(R.color.event_body_stretch), false, 1));
        addCell(5, 4, new Event("Body Stretch", color(R.color.event_body_stretch), false, 1));

        addCell(5, 7, new Event("Power Class", color(R.color.event_power), true, 1));
        addCell(6, 9, new Event("Power Class", color(R.color.event_power), true, 1));
        addCell(7, 9, new Event("Power Class", color(R.color.event_power), true, 1));

        addCell(3, 14, new Event("Tai-Bo", color(R.color.event_taibo), true, 1));
        addCell(4, 14, new Event("Tai-Bo", color(R.color.event_taibo), true, 1));
        addCell(5, 14, new Event("Tai-Bo", color(R.color.event_taibo), true, 1));
        addCell(6, 14, new Event("Tai-Bo", color(R.color.event_taibo), true, 1));
        addCell(7, 14, new Event("Tai-Bo", color(R.color.event_taibo), true, 1));

        addCell(1, 14, new Event("Pilates", color(R.color.event_pilates), true, 1));
        addCell(2, 17, new Event("Pilates", color(R.color.event_pilates), true, 1));
        addCell(3, 17, new Event("Pilates", color(R.color.event_pilates), true, 1));
        addCell(6, 15, new Event("Pilates", color(R.color.event_pilates), true, 1));
        addCell(7, 16, new Event("Pilates", color(R.color.event_pilates), true, 1));

        return rootView;

    }


    private void addCell(int colId, int rowId, Event event) {
        View cell = mInflater.inflate(R.layout.cell_schedule, mGrid, false);
        cell.setOnClickListener(new DetailOnClickListener(mInflater, event));

        ViewGroup.LayoutParams lpCell = cell.getLayoutParams();
        SafeView sv = new SafeView(cell);

        sv.get(R.id.cell_content).setBackgroundColor(event.bgColor);
        sv.setText(R.id.cell_txt_event_type, event.name);

        GridLayout.Spec row = GridLayout.spec(rowId, event.hours);
        GridLayout.Spec colspan = GridLayout.spec(colId, 1);
        GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(row, colspan);
        gridLayoutParam.height = lpCell.height * event.hours;
        gridLayoutParam.width = lpCell.width;
        mGrid.addView(cell, gridLayoutParam);
    }
}