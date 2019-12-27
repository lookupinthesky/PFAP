package com.example.pfa_p.Model;

import android.util.Log;

import java.util.List;

public class User {

    private String prisonerId; // the inmate_id as filled at the login screen
    private long idInDb; // the PRIMARY KEY in users table
    private String name; // name of prisoner
    private int numberOfVisits; // total times visited COMPLETELY
    private boolean ifHistoryTaken; // status of history
    private boolean ifAssessmentTaken; // status of assessment in the current visit number
    private boolean isSynced; // is synced to server
    private String volunteerId;
    private List<Module> modules;

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private String timeStamp;
    private String status;
    private String action;




    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(String value) {

        isSynced = !value.equals("dirty");
    }

    public String getPrisonerId() {
        return prisonerId;
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public long getIdInDb() {
        return idInDb;
    }

    public void setIdInDb(long idInDb) {
        if(idInDb==-1){
            throw new IllegalArgumentException();
        }
       Log.d("POJO : USERS",  "setIdInDb called - idInDb = " + idInDb);
        this.idInDb = idInDb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(int numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    public boolean isIfHistoryTaken() {
        return ifHistoryTaken;
    }

    public void setIfHistoryTaken(boolean ifHistoryTaken) {
        this.ifHistoryTaken = ifHistoryTaken;
    }

    public boolean isIfAssessmentTaken() {
        return ifAssessmentTaken;
    }

    public void setIfAssessmentTaken(boolean ifAssessmentTaken) {
        this.ifAssessmentTaken = ifAssessmentTaken;
    }
}
