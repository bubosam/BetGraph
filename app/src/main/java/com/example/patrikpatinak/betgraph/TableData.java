package com.example.patrikpatinak.betgraph;

import android.provider.BaseColumns;



/**
 * Created by patrik.patinak on 7/18/2016.
 */
public class TableData {
    public TableData(){

    }


    public static abstract class  TableInfo implements BaseColumns{

        public static final String dateOfMatch = "date_of_match";
        public static final String IdMatch = "Id";
        public static final String numberOfMatches = "number_of_matches";
        public static final String rateOfMatch = "rate_of_match";
        public static final String deposit = "deposit";
        public static final String win = "win";

        public static final String DATABASE_NAME="BetGraph";
        public static final String TABLE_NAME = "Matches";

    }
}
