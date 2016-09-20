package com.example.patrikpatinak.betgraph;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;



/**
 * A simple {@link Fragment} subclass.
 */
public class GraphsFragment extends Fragment {

    private GraphView graph;
    private TextView difference;
    private  TextView numberOfBets;




    public GraphsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_graphs, container, false);
        graph = (GraphView) view.findViewById(R.id.graph);
        difference = (TextView) view.findViewById(R.id.differnece);
        numberOfBets = (TextView) view.findViewById(R.id.NOB);
        SharedPreferences prefs=getActivity().getSharedPreferences("MYPREFS",0);
        int counter = prefs.getInt("counter",0);

        if(counter!=0){
            DataPoint[] dataPoints=new DataPoint[counter];
            float value = 0;
            for(int i=1;i<=counter;i++){
                value+= prefs.getFloat("ticket"+i,0);
                dataPoints[i-1]=new DataPoint(i,value);
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);

            graph.addSeries(series);
            graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().calcCompleteRange();

        graph.setTitle("Your Bets ");
        graph.setTitleTextSize(90);
        graph.setBackgroundColor(Color.WHITE);
            if (value>0){
                difference.setText("+"+value+" €");
                numberOfBets.setText(counter+"");
            }
            else{
                difference.setText(value+" €");
                numberOfBets.setText(counter+"");
            }

        }
        else{
            Toast.makeText(getActivity(),"You don´t have any tickets!!!",Toast.LENGTH_SHORT).show();
        }




        return view ;
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            onResume();
        }
    }

    @Override
    public void onResume()
    {

        SharedPreferences prefs=getActivity().getSharedPreferences("MYPREFS",0);
        
        int counter = prefs.getInt("counter",0);
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }

        if(counter!=0){
            DataPoint[] dataPoints=new DataPoint[counter];
            float value = 0;
            for(int i=1;i<=counter;i++){
                value+= prefs.getFloat("ticket"+i,0);
                dataPoints[i-1]=new DataPoint(i,value);
            }
            Log.d("DataPoints",String.valueOf(dataPoints));
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
            Log.d("Series",String.valueOf(series));
            graph.addSeries(series);
            graph.getViewport().setScalable(true);
            graph.getViewport().setScrollable(true);
            graph.getViewport().calcCompleteRange();
            Log.d("data length", graph.getSeries().size() + "");
            graph.setTitle("Your Bets ");
            graph.setTitleTextSize(90);
            graph.setBackgroundColor(Color.WHITE);
            if (value>0){
                difference.setText("+"+value+" €");
                numberOfBets.setText(counter+"");
            }
            else{
                difference.setText(value+" €");
                numberOfBets.setText(counter+"");
            }

        }
        else{
            Toast.makeText(getActivity(),"You don´t have any tickets!!!",Toast.LENGTH_SHORT).show();
        }
    }


}
