package com.example.patrikpatinak.betgraph;

/**
 * Created by patrik.patinak on 7/18/2016.
 */
public class Ticket {
    public int id;
    private String date;
    private int numberOfMatches;
    private float rate;
    private float deposit;
    private float win;

    public Ticket(int id, String date, int numberOfMatches, float rate, float deposit, float win) {
        this.id = id;
        this.date = date;
        this.numberOfMatches = numberOfMatches;
        this.rate = rate;
        this.deposit = deposit;
        this.win = win;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public float getRate() {
        return rate;
    }

    public float getDeposit() {
        return deposit;
    }

    public float getWin() {
        return win;}

    @Override
    public String toString() {
        return String.format("ID: %d, Date: %s, NumOfMatches: %d, Rate: %f, Deposit: %f, Win: %f", id, date, numberOfMatches, rate, deposit, win);
    }
}
