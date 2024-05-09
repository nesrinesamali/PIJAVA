package org.example.models;

import java.sql.Date;

public class Medrecord {
    int id,userid;
    Date date;
    String patient,diagnosis,test;

    public Medrecord() {
    }

    public Medrecord(int userid, Date date, String patient, String diagnosis, String test) {
        this.userid = userid;
        this.date = date;
        this.patient = patient;
        this.diagnosis = diagnosis;
        this.test = test;
    }

    public Medrecord(int id, int userid, Date date, String patient, String diagnosis, String test) {
        this.id = id;
        this.userid = userid;
        this.date = date;
        this.patient = patient;
        this.diagnosis = diagnosis;
        this.test = test;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
