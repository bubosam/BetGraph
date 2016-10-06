package com.example.patrikpatinak.betgraph;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;



public class AddTicketFragment extends Fragment {
private NumberPicker numberPicker;
    private EditText deposit;
    private EditText rate;
    private EditText possibleWin;
    private FloatingActionButton submit;
    private Context context;

    public AddTicketFragment() {
        // Required empty public constructor
    }




    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_add_ticket,container,false);
        numberPicker =(NumberPicker) view.findViewById(R.id.numberOfMatches);
        deposit =(EditText) view.findViewById(R.id.deposit);
        rate= (EditText) view.findViewById(R.id.rate);
        possibleWin = (EditText) view.findViewById(R.id.win);
        submit =(FloatingActionButton) view.findViewById(R.id.FAB2);

        context=getActivity().getApplicationContext();
        final DatabaseOperations db = new DatabaseOperations(context);
        db.getReadableDatabase();


        deposit.setGravity(Gravity.CENTER);
        rate.setGravity(Gravity.CENTER);
        possibleWin.setGravity(Gravity.CENTER);
        numberPicker.setMaxValue(30);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(true);

        Bundle bundle = this.getArguments();
        final String dateNumber = bundle.getString("date",null);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!deposit.getText().toString().isEmpty()){
                    if(!rate.getText().toString().isEmpty()){

                            String numberOfMatches= String.valueOf(numberPicker.getValue());
                            String depositNumber= String.valueOf(deposit.getText());
                            String rateNumber=String.valueOf(rate.getText());
                            float multiply = Float.valueOf(depositNumber)*Float.valueOf(rateNumber);

                            possibleWin.setText(String.valueOf(multiply));
                            String winNumber=String.valueOf(possibleWin.getText());

                            db.insertMatches(db,dateNumber,numberOfMatches,rateNumber,depositNumber,winNumber);
                            Toast.makeText(getActivity().getApplicationContext(), "Ticket has been added", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);

                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(), "You need to fill rate !", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "You need to fill deposit ! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view ;
        }

        }
