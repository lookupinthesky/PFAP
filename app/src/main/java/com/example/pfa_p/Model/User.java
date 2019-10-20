package com.example.pfa_p.Model;

public class User {

    private String prisonerId; // the inmate_id as filled at the login screen
    private long idInDb; // the PRIMARY KEY in users table
    private String name; // name of prisoner
    private int numberOfVisits; // total times visited COMPLETELY
    private boolean ifHistoryTaken; // status of history
    private boolean ifAssessmentTaken; // status of assessment in the current visit number
    private boolean isSynced; // is synced to server

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(String value) {

        isSynced = !value.equals("dirty");
    }

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public long getIdInDb() {
        return idInDb;
    }

    public void setIdInDb(long idInDb) {
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
