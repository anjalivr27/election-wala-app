package com.example.database.models;
import java.util.List;
import java.util.Map;

public class PollingStation {
    private int serialNumber, voters;
    private String docId , assembly, locality, building, pollingAreas ;
    private List<Map<String, Object>> sectorOfficers, BLO, talathi, gramSevak, policePatil, kotwal, principle , policeStation, rashoningShop, MandalAdhikar;

    public PollingStation( int serialNumber, int voters,  String assembly, String building, String locality, String pollingAreas,
                          List<Map<String, Object>> sectorOfficers, List<Map<String, Object>> BLO, List<Map<String, Object>> talathi,
                          List<Map<String, Object>> gramSevak, List<Map<String, Object>> policePatil, List<Map<String, Object>> kotwal,
                          List<Map<String, Object>> principle, List<Map<String, Object>> policeStation, List<Map<String, Object>> rashoningShop,
                          List<Map<String, Object>> MandalAdhikar) {
        this.serialNumber = serialNumber;
        this.voters = voters;
        this.assembly = assembly;
        this.building = building;
        this.locality = locality;
        this.pollingAreas = pollingAreas;
        this.sectorOfficers = sectorOfficers;
        this.BLO = BLO;
        this.talathi = talathi;
        this.gramSevak = gramSevak;
        this.policePatil = policePatil;
        this.kotwal = kotwal;
        this.principle = principle;
        this.policeStation = policeStation;
        this.rashoningShop = rashoningShop;
        this.MandalAdhikar = MandalAdhikar;
    }

    public int getSerialNumber(){
        return serialNumber;
    }

    public String getAssembly(){
        return assembly;
    }

    public String getBuilding(){
        return building;
    }

    public String getDocId(){
        return docId;
    }

    public void setDocId(String docId){
        this.docId = docId;
    }

    public void setSerialNumber(int serialNumber){
        this.serialNumber = serialNumber;
    }

    public void setAssembly(String assembly){
        this.assembly = assembly;
    }

    public void setBuilding(String building){
        this.building = building;
    }

}
