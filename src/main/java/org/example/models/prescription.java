package org.example.models;

import java.sql.Date;

public class prescription {
    int id,idmed,userid;
String patient,doctor,medication,instruction,pharmacy;
Date createdat;

    public prescription() {
    }

    public prescription(int idmed, int userid, String patient, String doctor, String medication, String instruction, String pharmacy, Date createdat) {
        this.idmed = idmed;
        this.userid = userid;
        this.patient = patient;
        this.doctor = doctor;
        this.medication = medication;
        this.instruction = instruction;
        this.pharmacy = pharmacy;
        this.createdat = createdat;
    }

    public prescription(int id, int idmed, int userid, String patient, String doctor, String medication, String instruction, String pharmacy, Date createdat) {
        this.id = id;
        this.idmed = idmed;
        this.userid = userid;
        this.patient = patient;
        this.doctor = doctor;
        this.medication = medication;
        this.instruction = instruction;
        this.pharmacy = pharmacy;
        this.createdat = createdat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdmed() {
        return idmed;
    }

    public void setIdmed(int idmed) {
        this.idmed = idmed;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }
}
