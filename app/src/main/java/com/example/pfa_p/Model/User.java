package com.example.pfa_p.Model;

public class User {

    String prisonerId;
    long _id;
    String name;
    int numberOfVisits;
    boolean ifHistoryTaken;
    boolean ifAssessmentTaken;
    boolean isSynced;

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

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
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
