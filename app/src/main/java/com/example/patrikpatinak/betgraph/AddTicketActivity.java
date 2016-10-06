package com.example.patrikpatinak.betgraph;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import java.util.Calendar;

public class AddTicketActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DatePicker datePicker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add new ticket");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTicketActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Calendar calendar= Calendar.getInstance();
           MyOnDateChangeListener onDateChangeListener = new MyOnDateChangeListener();
                datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), onDateChangeListener);


    }

    public class MyOnDateChangeListener implements DatePicker.OnDateChangedListener {



        @Override
        public void onDateChanged(DatePicker view, int year, int month, int day) {
            int monthNumber= month+1;
            int yearNumber= year;
            int dayNumber = day;
            String date = String.valueOf(yearNumber)+"/"+String.valueOf(monthNumber)+"/"+String.valueOf(dayNumber);

            Fragment fragment = null;
            fragment = new AddTicketFragment();
            Bundle bundle = new Bundle();
            bundle.putString("date",date);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativeReplace, fragment).addToBackStack("AddTicketActivity").commit();

        }


    }


}
