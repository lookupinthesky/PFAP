package com.example.pfa_p.Model;

public class User {

    private String prisonerId;
    private long idInDb;
    private String name;
    private int numberOfVisits;
    private boolean ifHistoryTaken;
    private boolean ifAssessmentTaken;
    private boolean isSynced;

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
