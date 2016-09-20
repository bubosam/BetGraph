package com.example.patrikpatinak.betgraph;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class HazzardFragment extends Fragment {
    private ImageButton success2;
    private ImageButton fail2;
    private EditText deposit_from_hazzard;
    private int counter;
    private float balance_from_hazzard;
    private static SharedPreferences prefs2;



    public HazzardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hazzard, container, false);
        success2 = (ImageButton) view.findViewById(R.id.success2);
        fail2 = (ImageButton) view.findViewById(R.id.fail2);
        deposit_from_hazzard = (EditText) view.findViewById(R.id.deposit_from_hazzard);


        success2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!deposit_from_hazzard.getText().toString().isEmpty()){
                    String temporary_deposit = deposit_from_hazzard.getText().toString();
                    balance_from_hazzard = Float.valueOf(temporary_deposit);
                    counter++;
                    prefs2=getActivity().getSharedPreferences("MYPREFS2",0);
                    SharedPreferences.Editor editor = prefs2.edit();
                    editor.putInt("counter_hazzard",counter);
                    editor.putFloat("ticket_hazzard"+counter,  balance_from_hazzard);
                    editor.commit();
                    deposit_from_hazzard.setText("");
                    Toast.makeText(v.getContext(),"Congratulations for the winning ,\n it has been marked to your graph !",Toast.LENGTH_LONG).show();
                    Log.d("prefs",String.valueOf(prefs2));

                }
                else{
                    Toast.makeText(v.getContext(),"You need to fill deposit",Toast.LENGTH_SHORT).show();
                }

            }
        });

        fail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!deposit_from_hazzard.getText().toString().isEmpty()){
                    String temporary_deposit = deposit_from_hazzard.getText().toString();
                    balance_from_hazzard = Float.valueOf(temporary_deposit);

                }
                else{
                    Toast.makeText(v.getContext(),"You need to fill deposit",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}
