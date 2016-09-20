package com.example.patrikpatinak.betgraph;

/**
 * Created by patrik.patinak on 7/19/2016.
 */
public class ItemData {
    private String date;
    private double deposit;
    private double rate;
    private double win;

    ItemData(String date, double deposit, double rate, double win) {
        this.date = date;
        this.deposit = deposit;
        this.rate = rate;
        this.win = win;
    }
}
