package com.example.patrikpatinak.betgraph;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class TicketsFragment extends Fragment implements OnVisibleCallback {

    private FloatingActionButton fab;
    private Context context;
    private NestedScrollView nestedView;
    private RecyclerView recyclerView;
    private int counter;
    private SharedPreferences sp;
    private TextView noTicket;
    private TicketAdapter mAdapter;


    public TicketsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);
        context = getActivity();
        sp = getActivity().getSharedPreferences("MYPREFS",0);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.FAB);
        nestedView = (NestedScrollView) view.findViewById(R.id.nestedView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final DatabaseOperations db = new DatabaseOperations(context);
        noTicket = (TextView) view.findViewById(R.id.noTicket);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, AddTicketActivity.class);
                context.startActivity(intent);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });




        Ticket myList[] = db.getAllMatches();

        for (int i = 0; i < myList.length; i++) {

            Log.d("Databaza", String.valueOf(myList[i].toString()));
        }



        counter = sp.getInt("counter", 0);
        Log.d("Counter", String.valueOf(counter));

        mAdapter = new TicketAdapter(new ArrayList<Ticket>(Arrays.asList(myList)),counter);
        changeVisible();
        mAdapter.setCallback(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void changeVisible() {
        if(mAdapter.getItemCount()>0){
            noTicket.setVisibility(View.GONE);
        }
        else{
            noTicket.setVisibility(View.VISIBLE);
        }
    }
}

