package com.example.patrikpatinak.betgraph;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;



/**
 * A simple {@link Fragment} subclass.
 */
public class GraphsFragment extends Fragment {

    private GraphView graph;
    private TextView difference;
    private TextView numberOfBets;
    private FloatingActionButton resetData;
    NestedScrollView nestedScrollView;


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
        resetData = (FloatingActionButton) view.findViewById(R.id.FAB_reset);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.scrollViewGraph);

        resetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDialog();
            }
        });

        refreshUI();
        hideKeyboard();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            refreshUI();
            hideKeyboard();
        }

    }


    public void refreshUI() {
        Log.d("graph","Prepol sa graf");
        SharedPreferences prefs = getActivity().getSharedPreferences("MYPREFS", 0);
        int counter = prefs.getInt("counter", 0);

        if (counter != 0) {
            DataPoint[] dataPoints = new DataPoint[counter + 1];
            float value = 0;
            for (int i = 0; i < counter + 1 ; i++) {
                value += prefs.getFloat("ticket" + i, 0);

                dataPoints[i] = new DataPoint(i, value);
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
            Log.d("Series", String.valueOf(series));
            graph.addSeries(series);
            series.setColor(Color.RED);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(6);
            series.setThickness(12);
            series.setDrawBackground(true);

            graph.getViewport().setScalable(true);
            graph.getViewport().computeScroll();
            graph.getViewport().setScrollable(true);
            graph.getViewport().setBackgroundColor(Color.WHITE);

            graph.setTitle("Your Bets ");
            graph.setTitleTextSize(90);
            graph.setBackgroundColor(Color.WHITE);

            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        // show normal x values
                        return super.formatLabel(value, isValueX);
                    } else {
                        // show currency for y values
                        return super.formatLabel(value, isValueX) + " €";
                    }
                }
            });

            graph.getGridLabelRenderer().setHighlightZeroLines(true);
            graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);
            graph.getGridLabelRenderer().setNumHorizontalLabels(5);
            graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
            graph.getGridLabelRenderer().setLabelsSpace(2);


            Log.d("data length", graph.getSeries().size() + "");

            if (value > 0) {
                difference.setText("+" + value + " €");
                numberOfBets.setText(counter + "");
            } else {
                difference.setText(value + " €");
                numberOfBets.setText(counter + "");
            }

        } else {
            Toast.makeText(getActivity(), "You don´t have any tickets!!!", Toast.LENGTH_SHORT).show();
        }

    }


    private void hideKeyboard() {
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void showDialog(){
        new AlertDialog.Builder(getActivity())
                .setTitle("Reset Graph")
                .setMessage("Are you sure you want to reset the graph ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        resetData();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void resetData(){
        getActivity().getSharedPreferences("MYPREFS", 0).edit().clear().commit();
        graph.removeAllSeries();
        difference.setText("0");
        numberOfBets.setText("0");
        refreshUI();
    }
}
