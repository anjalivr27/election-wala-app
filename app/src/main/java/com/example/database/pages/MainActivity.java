package com.example.database.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.R;
import com.example.database.models.PollingStation;
import com.example.database.services.Firestore;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  Firestore.OnPollingStationLoadedListener {

    private Firestore firestore;
    TableLayout tableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestore = new Firestore();
//            firestore.loadInitialData();

        tableLayout = findViewById(R.id.tableLayout);

        // Add headers
        addHeaderRow(tableLayout, "Serial no", "Assembly", "Building");

        firestore.getAllPollingStations(this);

    }

    private void addHeaderRow(TableLayout tableLayout, String header1, String header2, String header3) {
        TableRow headerRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row, null);
        setRowData(headerRow, header1, header2, header3);
        tableLayout.addView(headerRow);
    }

    private void addDataRow(TableLayout tableLayout, String data1, String data2, String data3, PollingStation pollingStation) {
        TableRow dataRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row, null);
        setRowData(dataRow, data1, data2, data3);
        dataRow.setTag(pollingStation);


        dataRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the associated PollingStation object
                PollingStation selectedPollingStation = (PollingStation) view.getTag();

          Intent intent = new Intent(MainActivity.this, Officers.class);
          intent.putExtra("pollingStation", pollingStation.getDocId());
                startActivity(intent);

            }
        });

        tableLayout.addView(dataRow);

    }

    private void setRowData(TableRow row, String value1, String value2, String value3) {
        TextView textView1 = row.findViewById(R.id.textView1);
        TextView textView2 = row.findViewById(R.id.textView2);
        TextView textView3 = row.findViewById(R.id.textView3);

        textView1.setText(value1);
        textView2.setText(value2);
        textView3.setText(value3);
        // Set data for other TextViews as needed
    }

    @Override
    public void onPollingStationLoaded(List<PollingStation> pollingStations) {

        // Add data rows
        for (PollingStation data : pollingStations) {
            addDataRow(tableLayout, data.getSerialNumber() + "", data.getAssembly(), data.getBuilding(), data);
        }

    }
}