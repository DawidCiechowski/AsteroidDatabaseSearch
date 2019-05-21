package com.example.anger.readfromdatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dh;
    private ListView listView;
    private EditText et;
    SQLiteDatabase db;
    public int i = 0;
    private int j = 1;
    private Button add, first, last;

    TextView tx, tx2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button) findViewById(R.id.button);
        first = findViewById(R.id.first);
        last = findViewById(R.id.last);


        DataAdapter mDbHelper = new DataAdapter(getApplicationContext());
        mDbHelper.createDatabse();
        mDbHelper.open();

        LocalDate startDate = LocalDate.parse("2003-05-14");
        LocalDate endDate = LocalDate.parse("2005-10-30");
        final ArrayList<Asteroid> asteroids = mDbHelper.getAsteroids(startDate, endDate);

        mDbHelper.close();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(asteroids);
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = asteroids.size() - 1;
                j = asteroids.size();
                tx.setText(String.valueOf(j) + ". " + asteroids.get(i).toString());
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                j = 1;
                tx.setText(String.valueOf(j) + ". " + asteroids.get(i).toString());
            }
        });
        tx = findViewById(R.id.textView);
        tx.setText(String.valueOf(j) + ". " + String.valueOf(asteroids.get(0).toString()));
        tx2 = findViewById(R.id.textView2);
        tx2.setText(String.valueOf(asteroids.size()));
    }

    public void update(ArrayList<Asteroid> asteroids) {
        if(i < asteroids.size() - 1) {
            i++;
            j++;
        } else {
            i = 0;
            j = 1;
        }

        tx.setText(String.valueOf(j) + ". " + String.valueOf(asteroids.get(i).toString()));
    }


}

//package com.example.c3471369.databasetest;
//
//        import android.database.Cursor;
//        import android.os.Bundle;
//        import android.support.v7.app.AppCompatActivity;
//        import android.widget.TextView;
//
//        import java.time.LocalDate;
//
//public class MainActivity extends AppCompatActivity {
//
//    private TextView tx;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        DataAdapter mDbHelper = new DataAdapter(getApplicationContext());
//        mDbHelper.createDatabse();
//        mDbHelper.open();
//
//        LocalDate startDate = LocalDate.parse("2014-10-29");
//        LocalDate endDate = LocalDate.parse("2015-10-29");
//        Cursor test = mDbHelper.getTestData(startDate, endDate);
//
//        mDbHelper.close();
//
//        tx = findViewById(R.id.LOL);
//        boolean notEmpty = (test == null);
//        String s = String.valueOf(notEmpty);
//        tx.setText(String.valueOf(test.getCount()));
//    }
//}

