package com.example.patrikpatinak.betgraph;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;
import android.util.Log;

import java.util.Date;


public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS "+ TableData.TableInfo.TABLE_NAME+" ("+
            TableData.TableInfo.IdMatch+" INTEGER PRIMARY KEY,"+
            TableData.TableInfo.dateOfMatch+" TEXT,"+
            TableData.TableInfo.numberOfMatches+ " INTEGER,"+
            TableData.TableInfo.rateOfMatch+" DOUBLE,"+
            TableData.TableInfo.deposit+" DOUBLE,"+
            TableData.TableInfo.win+" DOUBLE );";

    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, database_version);
        Log.d("Database operations","Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database operations","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertMatches(DatabaseOperations dp, String date, String NOM, String rate, String deposit, String win){
        SQLiteDatabase SQ = dp.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.dateOfMatch,date);
        cv.put(TableData.TableInfo.numberOfMatches,NOM);
        cv.put(TableData.TableInfo.rateOfMatch,rate);
        cv.put(TableData.TableInfo.deposit,deposit);
        cv.put(TableData.TableInfo.win,win);

       long k= SQ.insert(TableData.TableInfo.TABLE_NAME,null,cv);
        Log.d("Database operations"," One row inserted");
    }

    public void deleteMatch(DatabaseOperations db,String id) {
        SQLiteDatabase SQ = db.getWritableDatabase();
        String selection = TableData.TableInfo.IdMatch+ " LIKE ?";
        String args[]={id};
        SQ.delete(TableData.TableInfo.TABLE_NAME,selection,args);

    }

    public Ticket[] getAllMatches(){
        SQLiteDatabase SQ = getReadableDatabase();
        String[] columns = {TableData.TableInfo.IdMatch, TableData.TableInfo.dateOfMatch, TableData.TableInfo.numberOfMatches, TableData.TableInfo.rateOfMatch, TableData.TableInfo.deposit, TableData.TableInfo.win};
        Cursor cursor = SQ.query(TableData.TableInfo.TABLE_NAME,columns,null,null,null,null,null);
        int cnt = cursor.getCount();
        Ticket[] tickets ;
        tickets = new Ticket[cnt];
        cnt=0;
        while(cursor.moveToNext()){

            int index0 = cursor.getColumnIndex(TableData.TableInfo.IdMatch);
            int index1 = cursor.getColumnIndex(TableData.TableInfo.dateOfMatch);
            int index2 = cursor.getColumnIndex(TableData.TableInfo.numberOfMatches);
            int index3 = cursor.getColumnIndex(TableData.TableInfo.rateOfMatch);
            int index4 = cursor.getColumnIndex(TableData.TableInfo.deposit);
            int index5 = cursor.getColumnIndex(TableData.TableInfo.win);

            int a = cursor.getInt(index0);
            String b = cursor.getString(index1);
            int c = cursor.getInt(index2);
            float d = cursor.getFloat(index3);
            float e = cursor.getFloat(index4);
            float f = cursor.getFloat(index5);
            tickets[cnt]= new Ticket(a,b,c,d,e,f);
            cnt++;
        }
        cursor.close();
        SQ.close();
        return tickets;
    }




}
