package ru.alexlen.hackfitness.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;

import ru.alexlen.hackfitness.BaseActivity;
import ru.alexlen.hackfitness.Config;
import ru.alexlen.hackfitness.R;
import ru.alexlen.hackfitness.VolleySingleton;
import ru.alexlen.hackfitness.adapter.GymAddressListAdapter;
import ru.alexlen.hackfitness.adapter.TrainerListAdapter;
import ru.alexlen.hackfitness.api.Trainer;
import ru.alexlen.hackfitness.widget.DividerItemDecoration;

/**
 * Created by almazko on 25/10/14.
 */
public class TrainerListFragment extends AbstractFragment {


    private TrainerListAdapter mAdapter;

    public static TrainerListFragment newInstance(Bundle data) {

        TrainerListFragment fg = new TrainerListFragment();
        fg.setArguments(data);

        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

        final View rootView = inflater.inflate(R.layout.fg_list_loaded, container, false);

        rootView.findViewById(R.id.list_loader).setVisibility(View.VISIBLE);
        final BaseActivity activity = (BaseActivity) getActivity();

        final RecyclerView listFunds = (RecyclerView) rootView.findViewById(R.id.list);
        listFunds.setLayoutManager(new LinearLayoutManager(activity));
        listFunds.setItemAnimator(new DefaultItemAnimator());


        final String url = Config.SITE + "gym/1/1/trainers";


        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        rootView.findViewById(R.id.list_loader).setVisibility(View.GONE);
                        ObjectMapper mapper = new ObjectMapper();

                        try {
                             ArrayList<Trainer> trainers = mapper.readValue(response,
                                     new TypeReference<ArrayList<Trainer>>() {
                            });


                            mAdapter = new TrainerListAdapter(activity, trainers);

                            listFunds.addOnItemTouchListener(mAdapter);
                            listFunds.setAdapter(mAdapter);

                            mAdapter.notifyDataSetChanged();

                        } catch (IOException e) {
                            Log.e("LOAD_MAP", url);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error

                        Log.e("LOAD", url + " " + error);
                        rootView.findViewById(R.id.list_loader).setVisibility(View.GONE);
                    }
                });

        VolleySingleton.getInstance(getBaseActivity()).addToRequestQueue(request);

        return rootView;

    }

}