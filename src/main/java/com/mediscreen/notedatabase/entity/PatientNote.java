package com.mediscreen.notedatabase.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("PatientNote")
public class PatientNote {
    @Id
    private String id;
    private int patientId;
    private String notes;

    public PatientNote() {
    }

    public PatientNote(int patientId, String notes) {
        this.patientId = patientId;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return String.format("Patient note = [id: %s, Patient id: '%s', Notes: '%s']", id, patientId, notes);
    }

}
