package com.example.patrikpatinak.betgraph;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;



public class HazzardFragment extends Fragment {

    private ImageButton success2;
    private ImageButton fail2;
    private EditText deposit_from_hazzard;
    private int counter_function,counter;
    private float balance_from_hazzard;
    private static SharedPreferences sp;



    public HazzardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_hazzard, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        success2 = (ImageButton) view.findViewById(R.id.success2);
        fail2 = (ImageButton) view.findViewById(R.id.fail2);
        deposit_from_hazzard = (EditText) view.findViewById(R.id.deposit_from_hazzard);

        sp = getActivity().getSharedPreferences("MYPREFS",0);

        counter = getCounter();

        success2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!deposit_from_hazzard.getText().toString().isEmpty())
                  {

                    String temporary_deposit = deposit_from_hazzard.getText().toString();
                    balance_from_hazzard = Float.valueOf(temporary_deposit);

                    Toast.makeText(v.getContext(),"Congratulations for the winning ,\n it has been marked to your graph !",Toast.LENGTH_LONG).show();

                    counter++;

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("counter",counter);
                    editor.putFloat("ticket"+counter, balance_from_hazzard);

                    editor.commit();

                    deposit_from_hazzard.setText("");

                  }
                else
                {
                    Toast.makeText(v.getContext(),"You need to fill deposit",Toast.LENGTH_SHORT).show();
                }

            }
        });

        fail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!deposit_from_hazzard.getText().toString().isEmpty()){

                    String temporary_deposit = deposit_from_hazzard.getText().toString();
                    balance_from_hazzard = Float.valueOf(temporary_deposit) * -1;

                    Toast.makeText(v.getContext(),"We are sorry for loosing this bet, better luck next time, it has been marked to your graph!",Toast.LENGTH_LONG).show();

                    counter++;

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("counter",counter);
                    editor.putFloat("ticket"+counter, balance_from_hazzard);
                    editor.commit();

                    deposit_from_hazzard.setText("");
                }
                else{
                    Toast.makeText(v.getContext(),"You need to fill deposit",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            counter = getCounter();
        }
    }

    private int getCounter()
    {
        counter_function = sp.getInt("counter", 0);
        Log.d("Counter_Hazz_Fragment", String.valueOf(counter_function));
        return counter_function;
    }


}
