package com.example.database.services;

import android.util.Log;

import com.example.database.models.PollingStation;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Firestore {
    private static final String TAG = "FirestoreService";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference pollingStationsCollection = db.collection("pollingStations");

    public void loadInitialData() {
        List<PollingStation> initialPollingStations = createInitialPollingStation();

        for (PollingStation pollingStation : initialPollingStations) {
            addPollingStationsToFirestore(pollingStation);
        }
    }


    private List<PollingStation> createInitialPollingStation() {
        List<PollingStation> pollingStations = new ArrayList<>();

        pollingStations.add(new PollingStation(null, 1, "105- Kannad Assembly", "Z.P.P.S. Room No-01"));
        pollingStations.add(new PollingStation(null, 2, "105- Kannad Assembly", "Z.P.P.S. Room No-01"));
        pollingStations.add(new PollingStation(null, 3, "105- Kannad Assembly", "Z.P.P.S. Room No-01"));
        pollingStations.add(new PollingStation(null, 4, "105- Kannad Assembly", "Z.P.P.S. Room No-01"));

        return pollingStations;
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
                            PollingStation pollingStation = new PollingStation(document.getId(), document.getLong("serialNumber").intValue(),document.getString("assembly"),document.getString("building"));
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


