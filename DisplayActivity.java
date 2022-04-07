package com.example.phonebook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        String [] projection = {
                MyContentProvider.COLUMN_FN,
                MyContentProvider.COLUMN_LN,
                MyContentProvider.COLUMN_PN
        };
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
                MyContentProvider.CONTENT_URI, projection,
                null,null);
        int count = cursor.getCount();
        cursor.moveToFirst();
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setVerticalScrollBarEnabled(true);
        TableRow tableRow;
        TextView t0,t1,t2;
        tableRow = new TableRow(this);
        t0 = new TextView(this);
        t1 = new TextView(this);
        t2 = new TextView(this);
        t0.setText("First Name");
        t0.setTextColor(Color.RED);
        t0.setTypeface(null, Typeface.BOLD);
        t0.setPadding(20,20,20,20);
        tableRow.addView(t0);
        t1.setText("Last Name");
        t1.setTextColor(Color.BLUE);
        t1.setTypeface(null, Typeface.BOLD);
        t1.setPadding(20,20,20,20);
        tableRow.addView(t1);
        t2.setText("Phone Number");
        t2.setTextColor(Color.GREEN);
        t2.setTypeface(null, Typeface.BOLD);
        t2.setPadding(20,20,20,20);
        tableRow.addView(t2);
        tableLayout.addView(tableRow);

        for ( int i = 0; i < count; i++ ) {
            tableRow = new TableRow(this);
            t0 = new TextView(this);
            t1 = new TextView(this);
            t2 = new TextView(this);
            t0.setText(cursor.getString(cursor.getColumnIndex("firstName")));
            t0.setTextColor(Color.BLACK);
            t0.setTypeface(null, Typeface.BOLD);
            t0.setPadding(20,20,20,20);
            tableRow.addView(t0);
            t1.setText(cursor.getString(cursor.getColumnIndex("lastName")));
            t1.setTextColor(Color.BLACK);
            t1.setTypeface(null, Typeface.BOLD);
            t1.setPadding(20,20,20,20);
            tableRow.addView(t1);
            t2.setText(cursor.getString(cursor.getColumnIndex("phoneNumber")));
            t2.setTextColor(Color.BLACK);
            t2.setTypeface(null, Typeface.BOLD);
            t2.setPadding(20,20,20,20);
            tableRow.addView(t2);
            tableLayout.addView(tableRow);
            cursor.moveToNext();
        }

        setContentView(tableLayout);
    }
}