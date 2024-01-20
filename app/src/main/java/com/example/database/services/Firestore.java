package com.example.database.services;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import com.example.database.models.PollingStation;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

class JsonFileReader {
    public String readJsonFile(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("datasets/" + fileName);

            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
            return null;
        }
    }
}

public class Firestore {
    private static final String TAG = "FirestoreService";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference pollingStationsCollection = db.collection("pollingStations");

    public void loadInitialData(Context context){
        try {
            List<PollingStation> initialPollingStations = createInitialPollingStation(context);

            for (PollingStation pollingStation : initialPollingStations) {
                addPollingStationsToFirestore(pollingStation);
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private List<PollingStation> createInitialPollingStation(Context context) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFileReader jsonFileReader = new JsonFileReader();

        // Load JSON content from the assets folder
        String jsonContent = jsonFileReader.readJsonFile(context, "initialData.json");

        try {
            // Deserialize JSON content into a list of PollingStation objects
            List<PollingStation> pollingStations = objectMapper.readValue(
                    jsonContent,
                    new TypeReference<List<PollingStation>>() {});

            return pollingStations;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
            return new ArrayList<>(); // or null, or throw an exception
        }
    }

    private void addPollingStationsToFirestore(PollingStation pollingStation) {
        pollingStationsCollection.add(pollingStation)
                .addOnSuccessListener(documentReference ->
                        Log.d(TAG, "User added with ID: " + documentReference.getId()))
                .addOnFailureListener(e ->
                        Log.e(TAG, "Error adding user", e));
    }
    public void getAllPollingStations(OnPollingStationLoadedListener listener) {
        pollingStationsCollection.orderBy("serialNumber").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<PollingStation> pollingStations = new ArrayList<>();

                        for (DocumentSnapshot document : task.getResult()) {
                            PollingStation pollingStation = document.toObject(PollingStation.class);
                            pollingStation.setDocId(document.getId());
                            pollingStations.add(pollingStation);
                        }
                        listener.onPollingStationLoaded(pollingStations);
                    } else {
                        Log.e(TAG, "Error getting users", task.getException());
                    }
                });
    }

    public interface OnPollingStationLoadedListener {
        void onPollingStationLoaded(List<PollingStation> pollingStations);
    }
}


