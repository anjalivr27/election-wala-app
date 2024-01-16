package com.example.database.models;
import java.util.Map;

public class PollingStation {
    private int serialNumber, voters;
    private String docId , assembly, locality, building, pollingAreas ;
    private Map<String, Object> sectorOfficers, BLO, talathi, gramSevak, policePatil, kotwal, principle , policeStation, rashoningShop, MandalAdhikari	;

    public PollingStation(String docId, int serialNumber,String assembly, String building, Map<String, Object> sectorOfficersy){
        this.docId = docId != null ? docId : "";
        this.serialNumber = serialNumber;
        this.assembly = assembly;
        this.building = building;
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
