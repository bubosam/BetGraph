package com.example.patrikpatinak.betgraph;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by patrik.patinak on 7/19/2016.
 */
public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder>  {
    private List<Ticket> ticketList;
    private Context context;
    private int counter;
    private static SharedPreferences prefs;
    private OnVisibleCallback callback;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView deposit, rate,win,date;
        public ImageView success,fault;
        public View v;



        public MyViewHolder(View v) {
            super(v);
            this.v=v;
            deposit = (TextView) v.findViewById(R.id.depositOfTicket);
            rate = (TextView) v.findViewById(R.id.rateOfTicket);
            win = (TextView) v.findViewById(R.id.winOfTicket);
            date = (TextView) v.findViewById(R.id.dateOfMAtch);
            success = (ImageView) v.findViewById(R.id.success);
            fault = (ImageView) v.findViewById(R.id.lostTicket);


            context = v.getContext();

        }
    }



    public TicketAdapter(List<Ticket> ticketList,int counter) {
        this.ticketList = ticketList;
        this.counter = counter;
    }

    public void setCallback(OnVisibleCallback callback) {
        this.callback = callback;
    }

    @Override
    public TicketAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(TicketAdapter.MyViewHolder holder, final int position) {
        final Ticket ticket = ticketList.get(position);
        holder.deposit.setText(ticket.getDeposit()+" €");
        holder.rate.setText(ticket.getRate()+"");
        holder.win.setText(ticket.getWin()+" €");
        holder.date.setText(ticket.getDate()+"");
        holder.success.setImageResource(R.drawable.ic_confirm);
        holder.fault.setImageResource(R.drawable.ic_refuse);



        final int pos = position;

        holder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                String ticketId= ticket.getId()+"";
                                final DatabaseOperations db = new DatabaseOperations(context);
                                db.deleteMatch(db,ticketId);
                                delete(position);
                                Toast.makeText(v.getContext(),"Ticket has been deleted",Toast.LENGTH_SHORT).show();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete this ticket?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                return true;
            }
        });

        holder.success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {



                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                double balance = ticket.getWin()-ticket.getDeposit();
                                String ticketId= ticket.getId()+"";

                                Toast.makeText(v.getContext(),"Congratulations for the winning ticket,\n it has been marked to your graph !",Toast.LENGTH_LONG).show();
                                counter++;
                                prefs=context.getSharedPreferences("MYPREFS",0);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt("counter",counter);
                                editor.putFloat("ticket"+counter, (float) balance);
                                editor.commit();
                                final DatabaseOperations db = new DatabaseOperations(context);
                                db.deleteMatch(db,ticketId);
                                delete(position);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you really want to mark this ticket as won ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();



            }
        });

        holder.fault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                float balance = ticket.getDeposit()*-1;
                                String ticketId= ticket.getId()+"";
                                Toast.makeText(v.getContext(),"We are sory for losing your ticket, better luck next time, it has been marked to your graph!",Toast.LENGTH_LONG).show();
                                Log.d("Negative balance",String.valueOf(balance));
                                counter++;
                                prefs=context.getSharedPreferences("MYPREFS",0);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt("counter",counter);
                                editor.putFloat("ticket"+counter, balance);
                                editor.commit();
                                final DatabaseOperations db = new DatabaseOperations(context);
                                db.deleteMatch(db,ticketId);
                                delete(position);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you really want to mark this ticket as lost ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });





    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public void delete(int position) { //removes the row
        ticketList.remove(position);
        notifyItemRemoved(position);
        if(callback !=null){
            callback.changeVisible();
        }
    }





}


