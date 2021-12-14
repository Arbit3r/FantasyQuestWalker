package com.example.fantasyquestwalker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class JourneySelection extends AppCompatActivity {

    public static final String EXTRA = "extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journeyselection);

        ListView lv = findViewById(R.id.lista);

        lv.setAdapter(new ArrayAdapter<Journey>(
                this,
                R.layout.centerlist,
                Singleton.getInstance().getMatkat())
        );

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //add stuff here
            }
        });
    }
}